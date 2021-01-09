package com.kankan.dao.mapper;

import com.kankan.dao.entity.KankanApply;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface KankanApplyMapper {
  @Insert("insert into kankan_apply(user_id,id_url,photo,username,remark,email,status,create_time,update_time)" +
    "values " +
    "(#{userId},#{idUrl},#{photo},#{username},#{remark},#{email},#{status},#{createTime},#{updateTime})")
  void insert(KankanApply kankanApply);

  @Select("select * from kankan_apply where user_id=#{userId} limit 1")
  KankanApply findByUserId(Long userId);

  @Select("select * from kankan_apply")
  List<KankanApply> findAll();

  @Update("update kankan_apply set status=#{status} where user_id=#{userId}")
  void updateStatus(Long userId, Integer status);
}

