package com.kankan.vo.tab;

import com.kankan.module.HeaderLine;
import com.kankan.module.HeaderLineItem;
import com.kankan.module.KankanAd;
import com.kankan.module.KankanUser;
import com.kankan.module.KankanWork;
import com.kankan.module.MediaResource;
import com.kankan.module.News;
import com.kankan.module.Tab;
import com.kankan.service.HeaderLineService;
import com.kankan.service.KankanAdService;
import com.kankan.service.KankanUserService;
import com.kankan.service.KankanWorkService;
import com.kankan.service.NewsService;
import com.kankan.service.ResourceService;
import com.kankan.service.TabService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author <qiding@qiding.com> Created on 2020-12-04
 */
@Slf4j
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TabItemVo {

  private String itemId;
  private Integer itemType;
  private String resourceId;
  private Long publishTime;


  /**
   * 新闻展示方式
   */
  public NewsItemVo toNews(TabService tabService, NewsService newsService, ResourceService resourceService) {
    //获取新闻详情
    News news = newsService.findNewsById(itemId);
    log.info("toNews itemId={},response={}", itemId, news);
    //获取tab名称
    Tab tab = tabService.findTab(news.getTabId());
    //资源详情
    MediaResource resource = resourceService.findResource(news.getResourceId());
    NewsItemVo newsItemVo = new NewsItemVo(tab, news, resource);
    log.info("newsItemVo itemId={},response={}", itemId, newsItemVo);
    return newsItemVo;
  }


  /**
   * 视频展示方式
   */
  public VideoItemVo toVideo(KankanUserService userService, KankanWorkService workService,
      ResourceService resourceService) {
    //获取视频的详情
    KankanWork video = workService.findVideo(itemId);
    //获取看看用户信息
    KankanUser writer = userService.findUser(video.getUserId());
    //获取 点赞数、评论数
    MediaResource resource = resourceService.findResource(video.getResourceId());
    // 合并结果
    return new VideoItemVo(video, writer, resource);
  }

  /**
   * 文章展示方式
   */
  public ArticleItemVo toArticle(KankanUserService userService, KankanWorkService workService,
      ResourceService resourceService) {
    //获取视频的详情
    KankanWork article = workService.findArticle(itemId);
    //获取看看用户信息
    KankanUser writer = userService.findUser(article.getUserId());
    //获取 点赞数、评论数
    MediaResource resource = resourceService.findResource(article.getResourceId());
    // 合并结果
    return new ArticleItemVo(article, writer, resource);
  }

  /**
   * 广告展示方式
   */
  public AdItemVo toAd(KankanAdService adService) {
    KankanAd ad = adService.findAd(itemId);
    return new AdItemVo(ad);
  }

  /**
   * 头条展示方式
   */
  public HeaderLineVo toHeaderLine(HeaderLineService headerLineService) {
    HeaderLine headerLine = headerLineService.findHeaderLineById(itemId);
    List<HeaderLineItem> itemList = headerLineService.findHeaderLineItem(itemId);
    return new HeaderLineVo(headerLine, itemList);
  }
}
