package com.kankan.api.user;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.constant.EnumItemType;
import com.kankan.constant.EnumTabType;
import com.kankan.constant.PageData;
import com.kankan.module.*;
import com.kankan.param.tab.TabPageInfo;
import com.kankan.service.*;
import com.kankan.vo.detail.ArticleDetailVo;
import com.kankan.vo.detail.NewsDetailVo;
import com.kankan.vo.detail.VideoDetailVo;
import com.kankan.vo.tab.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-04
 */
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


  public ItemController(TabService tabService, HotPointService hotPointService,
                        KankanWorkService workService, NewsService newsService, KankanAdService adService,
                        HeaderLineService headerLineService, KankanUserService kankanUserService,
                        ResourceService resourceService, CommentService commentService) {
    this.tabService = tabService;
    this.hotPointService = hotPointService;
    this.workService = workService;
    this.newsService = newsService;
    this.adService = adService;
    this.headerLineService = headerLineService;
    this.kankanUserService = kankanUserService;
    this.resourceService = resourceService;
    this.commentService = commentService;
  }


  @ApiOperation("获取详情")
  @GetMapping("detail")
  public CommonResponse detail(@RequestParam(value = "resourceId") String resourceId, @RequestParam(value = "itemType") Integer itemType) {
    MediaResource mediaResource = MediaResource.builder().resourceId(resourceId).build();
    mediaResource.incrementReadCount(resourceService);
    MediaResource resource = resourceService.findResource(resourceId);
    EnumItemType enumItem = EnumItemType.getItem(itemType);

    switch (enumItem) {
      case NEWS:
        NewsDetailVo newsDetailVo = NewsDetailVo.builder().resourceId(resourceId).build();
        newsDetailVo.addBaseInfo(resource);
        newsDetailVo.addAdInfo(adService);
        newsDetailVo.addCommentInfo(commentService, kankanUserService);
        newsDetailVo.addRelatedNews(resource, resourceService, tabService, newsService);
        return success(newsDetailVo);
      case ARTICLE:
        ArticleDetailVo articleDetailVo = ArticleDetailVo.builder().resourceId(resourceId).build();
        articleDetailVo.addBaseInfo();
        articleDetailVo.addUserAndArticle(kankanUserService, workService, mediaResource);
        articleDetailVo.addCommentInfo(commentService, kankanUserService);
        return success(articleDetailVo);
      case VIDEO:
        VideoDetailVo videoDetailVo = VideoDetailVo.builder().resourceId(resourceId).build();
        videoDetailVo.addBaseInfo();
        videoDetailVo.addCommentInfo(commentService, kankanUserService);
        videoDetailVo.addRelatedVideos(resource, resourceService, kankanUserService, workService);
        videoDetailVo.addUserVo(kankanUserService, workService);
        return success(videoDetailVo);
      default:
        break;
    }
    return success(resource.getContent());
  }


  @ApiOperation("获取新闻列表")
  @GetMapping("list")
  public CommonResponse list(@Valid
                             @NotNull(message = "不能为空") @RequestParam(value = "tabId") Long tabId,
                             @NotNull(message = "不能为空")
                             @RequestParam(value = "offset", required = false, defaultValue = Integer.MAX_VALUE + "") Long offset,
                             @NotNull(message = "不能为空") @RequestParam(value = "size") Integer size) {

    TabPageInfo pageInfo = TabPageInfo.builder().offset(offset).size(size).tabId(tabId).build();
    Tab tab = tabService.findTab(tabId);
    EnumTabType tabType = EnumTabType.get(tab.getTabType());
    List<TabItemVo> infoList = new ArrayList<>();

    //获取tab详情
    switch (tabType) {
      case HOT:
        infoList.addAll(findHot(hotPointService, pageInfo));
        break;
      case ARTICLE:
        if (offset == Integer.MAX_VALUE) {
          infoList.add(findHeaderLine(headerLineService, pageInfo));
        }
        //热门看看号
        infoList.add(findHotUserItemVo());
        infoList.addAll(findArticle(workService, pageInfo));
        break;
      case NEWS:
        if (offset == Integer.MAX_VALUE) {
          infoList.add(findHeaderLine(headerLineService, pageInfo));
        }
        infoList.addAll(findNews(newsService, pageInfo));
        break;
      case VIDEO:
        infoList.addAll(findVideo(workService, pageInfo));
        break;
      case KANKAN_USER:
        infoList.addAll(findKankanUser(kankanUserService, pageInfo));
      default:
        break;
    }
    //判断tab类型 （热点、新闻、专栏、视频）
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
    return kankanUserList.stream().map(UserItemVo::new).collect(Collectors.toList());
  }

  /**
   * 头条
   */
  private TabItemVo findHeaderLine(HeaderLineService headerLineService, TabPageInfo pageInfo) {
    HeaderLine headerLine = headerLineService.findHeaderLineInfo(pageInfo.getTabId());
    List<HeaderLineItem> itemList = headerLineService.findHeaderLineItem(headerLine.getId());
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
    Long itemId = hotPoint.getItemId();
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
