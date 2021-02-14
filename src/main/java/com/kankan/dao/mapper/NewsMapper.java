package com.kankan.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kankan.dao.entity.NewsEntity;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author <qiding@qiding.com>
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

  @Update("update news_info set hot_status=#{hotStatus} where id=#{newsId}")
  void setHot(Long newsId, Integer hotStatus);

  @Update("update news_info set head_status=#{headStatus} where id=#{newsId}")
  void setHeader(Long newsId, Integer headStatus);

  @Select("select * from news_info where user_id=#{userId}")
  List<NewsEntity> findByUserId(Long userId);

  @Select("select * from news_info where id in (${newsIds})")
  List<NewsEntity> findTitles(String newsIds);

//  @Select("select * from news_info where title like '%#{keyword}%' limit #{offset},#{size}")
  @Select("select * from news_info where title like  concat('%',#{keyword},'%')  limit #{offset},#{size} ")
  List<NewsEntity> findByKeyword(String keyword, Long offset, Integer size);
}
