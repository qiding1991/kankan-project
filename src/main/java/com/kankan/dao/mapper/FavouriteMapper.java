package com.kankan.dao.mapper;

import java.util.List;

//import com.kankan.module.resouce.Favourite;
//import net.bytebuddy.implementation.auxiliary.AuxiliaryType;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Select;
import com.kankan.dao.entity.FavouriteEntity;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
//@Mapper
public interface FavouriteMapper {
  void insert(FavouriteEntity entity);

  List<FavouriteEntity> findUserFavourite(String userId, String offset, Integer size);

  void remove(String userId, String resourceId);

//  @Select("select * from favourite where user_id=#{userId} and resource_id=#{resourceId}")
  FavouriteEntity findByUserIdAndResourceId(String userId, String resourceId);
}
