package com.kankan.dao.mapper;

import java.util.List;

import com.kankan.module.resouce.Favourite;
import net.bytebuddy.implementation.auxiliary.AuxiliaryType;
import org.apache.ibatis.annotations.Mapper;

import com.kankan.dao.entity.FavouriteEntity;
import org.apache.ibatis.annotations.Select;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
@Mapper
public interface FavouriteMapper {
  void insert(FavouriteEntity entity);

  List<FavouriteEntity> findUserFavourite(Long userId, Long offset, Integer size);

  void remove(Long userId, String resourceId);

  @Select("select * from favourite where user_id=#{userId} and resource_id=#{resourceId}")
  FavouriteEntity findByUserIdAndResourceId(Long userId, String resourceId);
}
