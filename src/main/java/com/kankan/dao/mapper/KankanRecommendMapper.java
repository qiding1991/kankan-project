package com.kankan.dao.mapper;

import java.util.List;

import com.kankan.module.KankanRecommend;
import org.apache.ibatis.annotations.Mapper;

import com.kankan.dao.entity.KankanRecommendEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */
@Mapper
public interface KankanRecommendMapper {

     List<KankanRecommendEntity> findAll();

     void insert(KankanRecommendEntity entity);

     void deleteByUserId(KankanRecommendEntity entity);

     void updateByUserId(KankanRecommendEntity entity);

     @Select("select * from kankan_recommend where user_id=#{userId}")
     KankanRecommendEntity findKankanRecommend(Long userId);
}
