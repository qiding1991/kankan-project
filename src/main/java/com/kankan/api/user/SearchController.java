package com.kankan.api.user;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.constant.EnumItemType;
import com.kankan.constant.EnumTabType;
import com.kankan.dao.entity.NewsEntity;
import com.kankan.dao.entity.WorkEntity;
import com.kankan.module.*;
import com.kankan.service.*;
import com.kankan.vo.tab.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Api(tags = "用户搜索服务")
@RestController
@RequestMapping("user/search")
public class SearchController extends BaseController {

  private final HotPointService hotPointService;

  private final NewsService newsService;

  private final KankanWorkService workService;

  private final KankanUserService kankanUserService;

  private final TabService tabService;

  private final ResourceService resourceService;

  private final FollowService followService;

  public SearchController(HotPointService hotPointService, NewsService newsService,
                          KankanWorkService workService, KankanUserService kankanUserService, TabService tabService, ResourceService resourceService, FollowService followService) {
    this.hotPointService = hotPointService;
    this.newsService = newsService;
    this.workService = workService;
    this.kankanUserService = kankanUserService;
    this.tabService = tabService;
    this.resourceService = resourceService;
    this.followService = followService;
  }


  @ApiOperation(value = "热门新闻")
  @GetMapping("hot/newsAndArticle")
  public CommonResponse newsAndArticle() {
    //查询10个，随机返回3个 只查询 新闻+文章
    List<Integer> itemTypes = Stream.of(EnumItemType.NEWS.getCode(), EnumItemType.ARTICLE.getCode()).collect(Collectors.toList());
    List<HotPoint> hotPointList = hotPointService.findByItemType(itemTypes, 10);
    //批量获取标题
    List<Long> newsIdList = new ArrayList<>();
    List<Long> articleIdList = new ArrayList<>();

    hotPointList.forEach(hotPoint -> {
      if (EnumItemType.NEWS.getCode() == hotPoint.getItemType()) {
        newsIdList.add(hotPoint.getItemId());
      } else {
        articleIdList.add(hotPoint.getItemId());
      }
    });
    List<TabItemVo> itemVoList=new ArrayList<>();

    List<NewsEntity> newsInfoList = newsService.findNewsTitle(newsIdList);
    newsInfoList.stream().forEach(newsEntity -> {
      itemVoList.add(new NewsItemVo(newsEntity));
    });

    List<WorkEntity> articleInfoList = workService.findArticleTitle(articleIdList);

    articleInfoList.stream().forEach(workEntity -> {
      itemVoList.add(new ArticleItemVo(workEntity));
    });

    //长度是3个直接返回
    if (itemVoList.size() <= 3) {
      return success(itemVoList);
    }
    Set<Integer> indexSet = new HashSet<>();
    while (indexSet.size() < 3) {
      Integer randomIndex = RandomUtils.nextInt(itemVoList.size());
      indexSet.add(randomIndex);
    }
    List<TabItemVo> resultTitle = itemVoList.stream().filter(indexSet::contains)
      .collect(Collectors.toList());
    return success(resultTitle);
  }

  @ApiOperation(value = "热门看看号")
  @GetMapping("hot/kankan")
  public CommonResponse kankan() {
    //按照阅读数排名，返回前10个
    List<KankanUser> hotUserList = kankanUserService.findHotUserByReadCount(10);
    return success(hotUserList);
  }


  @ApiOperation(value = "根据关键字搜索 ")
  @GetMapping("byKeyWords")
  public CommonResponse searchHot(@RequestParam(value = "tableType") Integer tableType,
                                  @RequestParam(value = "keyword") String keyword,
                                  @NotNull(message = "不能为空") @RequestParam(value = "offset", required = false, defaultValue = "0") Long offset,
                                  @RequestParam(value = "userId", required = false) Long userId,
                                  @NotNull(message = "不能为空") @RequestParam(value = "size") Integer size) {
    //查看信息
    EnumTabType enumTabType = EnumTabType.get(tableType);
    List<TabItemVo> infoList = new ArrayList<>();
    if (enumTabType == EnumTabType.NEWS) {
      //获取满足条件的新闻
      List<News> newsList = newsService.findNews(offset, size, keyword);
      infoList.addAll(newsList.stream().map(this::transform).collect(Collectors.toList()));
    }
    if (enumTabType == EnumTabType.ARTICLE) {
      List<KankanWork> workList = workService.findArticle(offset, size, keyword);
      infoList.addAll(workList.stream().map(this::transform).collect(Collectors.toList()));
    }
    if (enumTabType == EnumTabType.KANKAN_USER) {
      List<KankanUser> kankanUserList = kankanUserService.findUser(offset, size, keyword);
      List<UserItemVo> userItemVoList = kankanUserList.stream().map(UserItemVo::new).collect(Collectors.toList());
      if (userId != null) {
        itemAddFollowStatus(followService, userItemVoList, userId);
      }
      infoList.addAll(userItemVoList);
    }
    return success(infoList);
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


}
