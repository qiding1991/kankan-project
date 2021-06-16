package com.kankan.dao.mapper;

import java.util.List;
//
//import org.apache.ibatis.annotations.Delete;
//import org.apache.ibatis.annotations.Insert;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Select;

import com.kankan.dao.entity.FollowEntity;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */
//@Mapper
public interface FollowMapper {

//  @Select("select * from kankan_follow where user_id=#{userId} and id > #{offset} limit #{size}")
  List<FollowEntity> findUserFollow(String userId, String offset, Integer size);

//  @Select("select count(1) from kankan_follow where user_id=#{userId} and follow_id=#{followId}")
  Integer findCount(String userId, String followId);

//  @Insert("insert into kankan_follow(id,user_id,follow_id,status,create_time,update_time) values (#{id},#{userId},#{followId},#{status},#{createTime},#{updateTime})")
  void insert(FollowEntity followEntity);

//  @Delete("delete from kankan_follow where user_id=#{userId} and follow_id=#{followId}")
  void delete(String userId, String followId);
}
