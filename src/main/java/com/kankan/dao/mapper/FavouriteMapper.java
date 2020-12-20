package com.kankan.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kankan.dao.entity.FavouriteEntity;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
@Mapper
public interface FavouriteMapper {
    void insert(FavouriteEntity entity);
    List<FavouriteEntity> findUserFavourite(Long userId, Long offset, Integer size);
    void remove(String resourceId);
}
