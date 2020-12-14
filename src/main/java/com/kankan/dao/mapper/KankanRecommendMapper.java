package com.kankan.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kankan.dao.entity.KankanRecommendEntity;

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
}
