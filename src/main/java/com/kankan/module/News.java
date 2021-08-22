package com.kankan.module;

import com.kankan.vo.NewsVo;
import org.springframework.beans.BeanUtils;

import com.kankan.dao.entity.NewsEntity;
import com.kankan.service.NewsService;
import com.kankan.service.ResourceService;
import com.kankan.service.TabService;
import com.kankan.vo.tab.NewsItemVo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class News {
  private String id;
  private String tabId;
  private String picture;
  private String title;
  private String resourceId;
  private String userId;
  private Long createTime;
  private Integer hotStatus;//是否设置称热点
  private Integer headStatus;
  private String desc;
  private Integer auditStatus;
  private Long offset;


  public static News fromResourceId(String resourceId, NewsService newsService) {
    return newsService.findNews(resourceId);
  }

  public void create(NewsService newsService) {
    newsService.createNews(this);
  }

  public static News parseEntity(NewsEntity newsEntity) {
    News news = new News();
    BeanUtils.copyProperties(newsEntity, news);
    news.setOffset(newsEntity.getUpdateTime());
    return news;
  }

  public NewsItemVo toItemVo(TabService tabService, ResourceService resourceService) {
    Tab tab = tabService.findTab(tabId);
    MediaResource resource = resourceService.findResource(resourceId);
    return new NewsItemVo(tab, this, resource);
  }

  public List<News> findAll(NewsService newsService) {
    return newsService.findAll();
  }

  public NewsVo toItemVo(ResourceService resourceService) {
    NewsVo newsVo = new NewsVo(this);
    MediaResource resource = resourceService.findResource(resourceId);
    newsVo.setContent(resource.getContent());
    newsVo.setKeyword(resource.getKeyWords());
    return newsVo;
  }
}
