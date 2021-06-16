package com.kankan.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.kankan.util.GsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.kankan.dao.entity.NewsEntity;
import com.kankan.dao.mapper.NewsMapper;
import com.kankan.module.News;
import com.kankan.param.tab.TabPageInfo;
import org.springframework.util.CollectionUtils;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-03
 */
@Service
public class NewsService {

  @Resource
  NewsMapper newsMapper;

  public void createNews(News news) {
    NewsEntity entity = new NewsEntity();
    BeanUtils.copyProperties(news, entity);
    newsMapper.insert(entity);
    news.setId(entity.getId());
  }

  public List<News> findNews(TabPageInfo pageInfo) {
    List<NewsEntity> infoList = newsMapper.findNews(pageInfo.getTabId(), pageInfo.getOffset(), pageInfo.getSize());
    return infoList.stream().map(News::parseEntity).collect(Collectors.toList());
  }

  public News findById(String  newsId) {
    NewsEntity newsEntity = newsMapper.findNewsById(newsId);
    return News.parseEntity(newsEntity);
  }

  public List<News> findAll() {
    List<NewsEntity> infoList = newsMapper.findAllNews();
    return infoList.stream().map(News::parseEntity).collect(Collectors.toList());
  }

  public News findNews(String resourceId) {
    NewsEntity newsEntity = newsMapper.findNewsByResourceId(resourceId);
    return News.parseEntity(newsEntity);
  }

  public void updateHotStatus(String newsId, Integer hotStatus) {
    newsMapper.setHot(newsId, hotStatus);
  }

  public void updateHeaderStatus(String userId, Integer headerStatus) {
    newsMapper.setHeader(userId, headerStatus);
  }


  public List<NewsEntity> findByUserId(String userId) {
    return newsMapper.findByUserId(userId);
  }

  public List<NewsEntity> findNewsTitle(List<String> newsIdList) {
    if (CollectionUtils.isEmpty(newsIdList)) {
      return new ArrayList<>();
    }
    String newsIds= GsonUtil.toGson(newsIdList);
    newsIds=newsIds.substring(1,newsIds.length()-1);
    List<NewsEntity> infoList=newsMapper.findTitles(newsIds);
    return infoList;
  }

  public List<News> findNews(String offset, Integer size, String keyword) {
    List<NewsEntity> infoList=newsMapper.findByKeyword(keyword,offset,size);
    return infoList.stream().map(News::parseEntity).collect(Collectors.toList());
  }
}
