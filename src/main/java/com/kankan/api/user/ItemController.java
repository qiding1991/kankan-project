package com.kankan.api.user;

import com.google.common.collect.ImmutableMap;
import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.constant.EnumItemType;
import com.kankan.constant.EnumTabType;
import com.kankan.constant.PageData;
import com.kankan.dao.entity.FavouriteEntity;
import com.kankan.dao.mapper.ThumpMapper;
import com.kankan.module.*;
import com.kankan.param.tab.TabPageInfo;
import com.kankan.service.*;
import com.kankan.vo.detail.ArticleDetailVo;
import com.kankan.vo.detail.NewsDetailVo;
import com.kankan.vo.detail.VideoDetailVo;
import com.kankan.vo.tab.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-04
 */
@Log4j2
@Validated
@Api(tags = "用户-主页-新闻")
@RestController
@RequestMapping("item")
public class ItemController extends BaseController {


  private TabService tabService;

  private HotPointService hotPointService;

  private KankanWorkService workService;

  private NewsService newsService;

  private KankanAdService adService;

  private HeaderLineService headerLineService;

  private KankanUserService kankanUserService;

  private ResourceService resourceService;

  private CommentService commentService;

  private FavouriteService favouriteService;

  private FollowService followService;

  @Autowired
  UserService userService;


  @Resource
  private ThumpMapper thumpMapper;


  public ItemController(TabService tabService, HotPointService hotPointService,
                        KankanWorkService workService, NewsService newsService, KankanAdService adService,
                        HeaderLineService headerLineService, KankanUserService kankanUserService,
                        ResourceService resourceService, CommentService commentService, FavouriteService favouriteService, FollowService followService) {
    this.tabService = tabService;
    this.hotPointService = hotPointService;
    this.workService = workService;
    this.newsService = newsService;
    this.adService = adService;
    this.headerLineService = headerLineService;
    this.kankanUserService = kankanUserService;
    this.resourceService = resourceService;
    this.commentService = commentService;
    this.favouriteService = favouriteService;
    this.followService = followService;
  }


  @ApiOperation("获取详情")
  @GetMapping("detail")
  public CommonResponse detail(@RequestParam(value = "userId", required = false) String userId, @RequestParam(value = "resourceId") String resourceId, @RequestParam(value = "itemType") Integer itemType) {
    MediaResource mediaResource = MediaResource.builder().resourceId(resourceId).build();
    mediaResource.incrementReadCount(resourceService);
    MediaResource resource = resourceService.findResource(resourceId);
    EnumItemType enumItem = EnumItemType.getItem(itemType);
    Boolean favouriteStatus = false;
    if (userId != null) {
      FavouriteEntity favouriteEntity = favouriteService.findFavourite(userId, resourceId);
      favouriteStatus = favouriteEntity == null ? false : true;
    }
    switch (enumItem) {
      case HOT_FIRST_NEWS:
      case NEWS:
        NewsDetailVo newsDetailVo = NewsDetailVo.builder().resourceId(resourceId).build();
        newsDetailVo.addBaseInfo(resource);
        newsDetailVo.addAdInfo(adService);
        newsDetailVo.addCommentInfo(commentService, userService);
        newsDetailVo.addRelatedNews(resourceId, resource, resourceService, tabService, newsService);
        newsDetailVo.setFavouriteStatus(favouriteStatus);
        //添加当前用户的评论状态
        newsDetailVo.addThumpStatus(userId, thumpMapper);

        return success(newsDetailVo);
      case ARTICLE:
        ArticleDetailVo articleDetailVo = ArticleDetailVo.builder().resourceId(resourceId).build();
        articleDetailVo.addBaseInfo(resource);
        articleDetailVo.addUserAndArticle(resourceId, kankanUserService, workService, mediaResource);
        articleDetailVo.addCommentInfo(commentService, userService);
        articleDetailVo.setFavouriteStatus(favouriteStatus);
        articleDetailVo.addThumpStatus(userId, thumpMapper);
        //当前用户是否关注
        articleDetailVo.addFollowStatus(followService, userId);
        //阅读加1
        kankanUserService.incrReadCount(articleDetailVo.getUserVo().getUserId());
        return success(articleDetailVo);
      case VIDEO:
        VideoDetailVo videoDetailVo = VideoDetailVo.builder().resourceId(resourceId).build();
        videoDetailVo.addBaseInfo(resource);
        videoDetailVo.addCommentInfo(commentService, userService);
        videoDetailVo.addRelatedVideos(resourceId, resource, resourceService, kankanUserService, workService);
        videoDetailVo.addUserVo(kankanUserService, workService);
        videoDetailVo.setFavouriteStatus(favouriteStatus);
        videoDetailVo.addThumpStatus(userId, thumpMapper);
        //当前用户是否关注
        videoDetailVo.addFollowStatus(followService, userId);
        //阅读加1
        kankanUserService.incrReadCount(videoDetailVo.getUserVo().getUserId());
        return success(videoDetailVo);
      default:
        break;
    }
    return success(ImmutableMap.of("content", resource.getContent()));
  }


  @ApiOperation("获取新闻列表")
  @GetMapping("list")
  public CommonResponse list(@Valid
                             @NotNull(message = "不能为空") @RequestParam(value = "tabId") String tabId,
                             @NotNull(message = "不能为空")
                             @RequestParam(value = "offset", required = false, defaultValue ="0") String offset,
                             @RequestParam(value = "userId", required = false) String userId,
                             @NotNull(message = "不能为空") @RequestParam(value = "size") Integer size) {

    if (offset.equals("2147483647")) {
      offset = "0";
    }

    TabPageInfo pageInfo = TabPageInfo.builder().offset(offset).size(size).tabId(tabId).build();
    Tab tab = tabService.findTab(tabId);
    if (tab == null) {
      log.info("tab not exists ,tabId={}", tabId);
    }
    EnumTabType tabType = EnumTabType.get(tab.getTabType());
    List<TabItemVo> infoList = new ArrayList<>();

    //获取tab详情
    switch (tabType) {
      case HOT:
        log.info("---开始查询热点--HOT");
        infoList.addAll(findHot(hotPointService, pageInfo));
        break;
      case ARTICLE:
        log.info("---开始查询文章--ARTICLE");
        infoList.add(findHotUserItemVo());
        if (offset.equals("0")) {
          infoList.add(findHeaderLine(resourceService, headerLineService, pageInfo));
        }
        infoList.addAll(findArticle(workService, pageInfo));
        break;
      case NEWS:
        log.info("---开始查询新闻--NEWS");
        if (offset.equals("0")) {
          infoList.add(findHeaderLine(resourceService, headerLineService, pageInfo));
        }
        infoList.addAll(findNews(newsService, pageInfo));
        break;
      case VIDEO:
        log.info("---开始查询视频--VIDEO");
        infoList.addAll(findVideo(workService, pageInfo));
        break;
      case KANKAN_USER:
        log.info("---开始查询用户--KANKAN_USER");
        List<UserItemVo> userList = findKankanUser(kankanUserService, pageInfo);
        if (userId != null) {
          itemAddFollowStatus(followService, userList, userId);
        }
        infoList.addAll(userList);
      default:
        break;
    }
    //过滤掉空的数据
    infoList = infoList.stream().filter(Objects::nonNull).collect(Collectors.toList());
    log.info("itemList response={}", infoList);
    //判断tab类型 （热点、新闻、专栏、视频）
    if (!CollectionUtils.isEmpty(infoList)) {
      TabItemVo itemVo = infoList.get(0);
      //第一个热点新闻，修改为大图
      if (itemVo.getItemType() == EnumItemType.NEWS.getCode()) {
        itemVo.setItemType(EnumItemType.HOT_FIRST_NEWS.getCode());
      }
    }
    PageData pageData = PageData.pageData(infoList, size);
    return success(pageData);
  }

  private TabItemVo findHotUserItemVo() {
    HotUserItemVo hotUserItemVo = new HotUserItemVo();
    hotUserItemVo.setItemType(EnumItemType.HOT_KAN_USER.getCode());
    return hotUserItemVo;
  }

  private List<UserItemVo> findKankanUser(KankanUserService kankanUserService, TabPageInfo pageInfo) {
    KankanUser kankanUser = KankanUser.builder().build();
    List<KankanUser> kankanUserList = kankanUser.findByPageInfo(kankanUserService, pageInfo);
    log.info("查询的用户列表返回，参数={},response={}", pageInfo, kankanUserList);
    return kankanUserList.stream().map(UserItemVo::new).collect(Collectors.toList());
  }

  /**
   * 头条
   */
  private TabItemVo findHeaderLine(ResourceService resourceService, HeaderLineService headerLineService, TabPageInfo pageInfo) {
    HeaderLine headerLine = headerLineService.findHeaderLineInfo(pageInfo.getTabId());
    if (headerLine == null) {
      return null;
    }
    List<HeaderLineItem> itemList = headerLineService.findHeaderLineItem(headerLine.getId());
    itemList.forEach(item -> {
      MediaResource resource = resourceService.findResource(item.getResourceId());
      item.setTitle(resource.getTitle());
      item.setItemType(resource.getMediaType());
    });
    return new HeaderLineVo(headerLine, itemList);
  }

  /**
   * 查看热点信息
   */
  private List<TabItemVo> findHot(HotPointService hotPointService, TabPageInfo pageInfo) {
    List<TabItemVo> infoList = new ArrayList<>();
    //1.找到所有的热点
    List<HotPoint> hotPointList = hotPointService.findHot(pageInfo);
    //2.关联到具体的服务
    infoList.addAll(hotPointList.stream().map(this::transform).collect(Collectors.toList()));
    return infoList;
  }


  /**
   * 查看新闻信息
   */
  private List<TabItemVo> findNews(NewsService newsService, TabPageInfo pageInfo) {
    List<News> newsList = newsService.findNews(pageInfo);
    return newsList.stream().map(this::transform).collect(Collectors.toList());
  }

  /**
   * 查看文章信息
   */
  private List<TabItemVo> findArticle(KankanWorkService workService, TabPageInfo pageInfo) {
    List<KankanWork> workList = workService.findArticle(pageInfo);
    return workList.stream().map(this::transform).collect(Collectors.toList());
  }

  /**
   * 查看视频信息
   */
  private List<TabItemVo> findVideo(KankanWorkService workService, TabPageInfo pageInfo) {
    List<KankanWork> workList = workService.findVideo(pageInfo);
    return workList.stream().map(this::transform).collect(Collectors.toList());
  }


  /**
   * 新闻信息转化为 展示
   */
  private NewsItemVo transform(News news) {
    NewsItemVo newsItemVo = news.toItemVo(tabService, resourceService);
    return newsItemVo;
  }

  /**
   * 看看信息展示
   */
  private TabItemVo transform(KankanWork kankanWork) {
    if (0 == kankanWork.getType()) {
      ArticleItemVo articleItemVo = kankanWork.toArticleItemVo(kankanUserService, resourceService);
      return articleItemVo;
    } else {
      VideoItemVo itemVo = kankanWork.toVideoItemVo(kankanUserService, resourceService);
      return itemVo;
    }
  }

  /**
   * 热点信息展示
   */
  private TabItemVo transform(HotPoint hotPoint) {
    Integer itemType = hotPoint.getItemType();
    String itemId = hotPoint.getItemId();
    EnumItemType itemEnum = EnumItemType.getItem(itemType);
    TabItemVo item = TabItemVo.builder().itemType(itemType).itemId(itemId).build();
    switch (itemEnum) {
      case NEWS:
        NewsItemVo newsItem = item.toNews(tabService, newsService, resourceService);
        return newsItem;
      case VIDEO:
        VideoItemVo videoItem = item.toVideo(kankanUserService, workService, resourceService);
        return videoItem;
      case ARTICLE:
        ArticleItemVo articleItem = item.toArticle(kankanUserService, workService, resourceService);
        return articleItem;
      case AD:
        AdItemVo adItem = item.toAd(adService);
        return adItem;
      case HEADER_LINE_GROUP:
        HeaderLineVo headerLine = item.toHeaderLine(headerLineService);
        return headerLine;
      default:
        return new TabItemVo();
    }
  }

}
