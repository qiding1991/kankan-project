package com.kankan.service;

import com.kankan.dao.entity.NewsEntity;
import com.kankan.dao.mapper.NewsMapper;
import com.kankan.module.News;
import com.kankan.param.AuditParam;
import com.kankan.param.tab.TabPageInfo;
import com.kankan.util.GsonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * @author <qiding@qiding.com> Created on 2020-12-03
 */
@Slf4j
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

    public News findById(String newsId) {
        NewsEntity newsEntity = newsMapper.findNewsById(newsId);
        return News.parseEntity(newsEntity);
    }

    public List<News> findAll() {
        List<NewsEntity> infoList = newsMapper.findAllNews();
        return infoList.stream().map(News::parseEntity).collect(Collectors.toList());
    }

    public News findNews(String resourceId) {
        NewsEntity newsEntity = newsMapper.findNewsByResourceId(resourceId);
        if (ObjectUtils.allNotNull(newsEntity)) {
            return News.parseEntity(newsEntity);
        }
        return null;
    }

    public News findNewsById(String newsId) {
        NewsEntity newsEntity = newsMapper.findNewsById(newsId);
        log.info("findNewsById newsId={},response={}", newsId, newsEntity);
        if (ObjectUtils.allNotNull(newsEntity)) {
            return News.parseEntity(newsEntity);
        }
        return null;
    }


    public void updateHotStatus(String newsId, Integer hotStatus) {
        newsMapper.setHot(newsId, hotStatus);
    }

    public void updateHeaderStatus(String userId, Integer headerStatus) {
        newsMapper.setHeader(userId, headerStatus);
    }


    public List<News> findByUserId(String userId) {
        List<NewsEntity> newsEntities = newsMapper.findByUserId(userId);
        return newsEntities.stream().map(News::parseEntity).collect(Collectors.toList());
    }

    public List<NewsEntity> findNewsTitle(List<String> newsIdList) {
        if (CollectionUtils.isEmpty(newsIdList)) {
            return new ArrayList<>();
        }
        String newsIds = GsonUtil.toGson(newsIdList);
        newsIds = newsIds.substring(1, newsIds.length() - 1);
        List<NewsEntity> infoList = newsMapper.findTitles(newsIds);
        return infoList;
    }

    public List<News> findNews(String offset, Integer size, String keyword) {
        List<NewsEntity> infoList = newsMapper.findByKeyword(keyword, offset, size);
        return infoList.stream().map(News::parseEntity).collect(Collectors.toList());
    }

    public void delete(String id) {
        newsMapper.delete(id);
    }

    public List<NewsEntity> findRelatedNews(String tabId, String id) {
        return newsMapper.findRelated(tabId, id);
    }


    public void updateNews(NewsEntity newsEntity) {
        newsMapper.updateNews(newsEntity);
    }

    public void auditNews(AuditParam auditParam) {
        newsMapper.auditNews(auditParam.getId(), auditParam.getAuditStatus());
    }
}
