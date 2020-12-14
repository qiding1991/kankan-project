package com.kankan.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kankan.dao.entity.NewsEntity;
import org.apache.ibatis.annotations.Select;

/**
 * @author  <qiding@qiding.com>
 * Created on 2020-12-03
 */
@Mapper
public interface NewsMapper {
    void insert(NewsEntity entity);

    List<NewsEntity> findNews(Long tabId, Long offset, Integer size);

    @Select("select * from news_info where id=#{newsId}")
    NewsEntity findNewsById(Long newsId);

    @Select("select * from news_info")
    List<NewsEntity> findAllNews();

    @Select("select * from news_info where resource_id=#{resourceId}")
    NewsEntity findNewsByResourceId(String resourceId);
}
