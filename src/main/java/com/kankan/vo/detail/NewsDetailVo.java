package com.kankan.vo.detail;

import com.kankan.dao.mapper.ThumpMapper;
import com.kankan.module.KankanAd;
import com.kankan.module.KankanComment;
import com.kankan.module.MediaResource;
import com.kankan.module.News;
import com.kankan.service.*;
import com.kankan.vo.KankanCommentVo;
import com.kankan.vo.tab.AdItemVo;
import com.kankan.vo.tab.NewsItemVo;
import java.util.Objects;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class NewsDetailVo {
  private String resourceId;
  private String content;
  private AdItemVo adItemVo;
  private String newsTitle = "相关信息";
  private List<NewsItemVo> relatedNews;
  private String commentTitle = "相关评论";
  private List<KankanCommentVo> commentVoList;
  private Boolean favouriteStatus;
  private Integer readCount;

  /**
   * 基本信息
   *
   * @param mediaResource
   */
  public void addBaseInfo(MediaResource mediaResource) {
    this.content = mediaResource.getContent();
    this.readCount = mediaResource.getReadCount();
    this.commentTitle = "相关评论";
    this.newsTitle = "相关信息";
  }

  /**
   * 广告
   *
   * @param adService
   */
  public void addAdInfo(KankanAdService adService) {
    this.adItemVo = findAdItemVo(adService);
  }

  /**
   * 相关信息
   *
   * @param resourceId
   * @param mediaResource
   * @param resourceService
   * @param tabService
   * @param newsService
   */
  public void addRelatedNews(String resourceId, MediaResource mediaResource, ResourceService resourceService, TabService tabService, NewsService newsService) {
    List<MediaResource> mediaResourceList = resourceService.findRelatedResource(mediaResource);
    mediaResourceList = mediaResourceList.stream().filter(resource -> !resource.getResourceId().equalsIgnoreCase(resourceId)).collect(Collectors.toList());
    if (CollectionUtils.isEmpty(mediaResourceList)) {
      return;
    }
    this.relatedNews = relatedNews(mediaResourceList, newsService, tabService, resourceService);
  }


  public void addCommentInfo(CommentService commentService, UserService userService) {
    KankanComment comment = KankanComment.builder().resourceId(resourceId).build();
    this.commentVoList = comment.resourceCommentInfo(commentService, userService);
  }


  /**
   * 相关信息
   *
   * @param mediaResourceList
   * @param newsService
   * @param tabService
   * @param resourceService
   * @return
   */
  private List<NewsItemVo> relatedNews(List<MediaResource> mediaResourceList, NewsService newsService, TabService tabService, ResourceService resourceService) {
    List<News> newsList = mediaResourceList.stream().map(mediaResource -> newsService.findNews(mediaResource.getResourceId()))
        .filter(Objects::nonNull)
        .collect(Collectors.toList());
    return newsList.stream().map(news -> news.toItemVo(tabService, resourceService)).collect(Collectors.toList());

  }

  /**
   * 随机广告
   *
   * @param adService
   * @return
   */
  AdItemVo findAdItemVo(KankanAdService adService) {
    List<KankanAd> adList = adService.findAll();
    if (!CollectionUtils.isEmpty(adList)) {
      int randomIndex=0;
      if(adList.size()>1){
        randomIndex = RandomUtils.nextInt(adList.size() - 1);
      }
      KankanAd kankanAd = adList.get(randomIndex);
      return new AdItemVo(kankanAd);
    } else {
      return null;
    }
  }


  public void addThumpStatus(String userId, ThumpMapper thumpMapper) {
    KankanCommentVo.addThumpStatus(this.commentVoList, userId, thumpMapper);
  }

}
