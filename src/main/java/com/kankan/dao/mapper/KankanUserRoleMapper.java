package com.kankan.dao.mapper;

import com.kankan.dao.entity.KankanUserRole;
//import org.apache.ibatis.annotations.Insert;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Select;
//import org.apache.ibatis.annotations.Update;

import java.util.List;

//@Mapper
public interface KankanUserRoleMapper {

//  @Insert("insert into kankan_user_role(user_id,role_id,status,create_time,update_time)" +
//    "values " +
//    "(#{userId},#{roleId},#{status},#{createTime},#{updateTime})")
  void insert(KankanUserRole kankanUserRole);

//  @Select("select * from kankan_user_role where user_id=#{userId}")
  KankanUserRole findByUserId(Long userId);

//  @Select("select * from kankan_user_role where user_id in (${userIdList})")
  List<KankanUserRole> batchFindUser(String userList);

//  @Update("update kankan_user_role set role_id=#{roleId} where user_id=#{userId}")
  void updateRole(Long userId, String roleId);
}
