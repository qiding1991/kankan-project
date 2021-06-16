package com.kankan.dao.mapper;

import com.kankan.dao.entity.WorkWhiteEntity;
//import org.apache.ibatis.annotations.*;

//@Mapper
public interface WorkWhiteListMapper {
//  @Insert("insert into work_white_list(id,user_id,status,update_time,create_time) values(#{id},#{userId},#{status},#{updateTime},#{createTime})")
//  @Options(useGeneratedKeys = true,keyProperty = "id")
  void insert(WorkWhiteEntity workWhiteEntity);

//  @Delete("delete from work_white_list where user_id=#{userId}")
  void delete(String userId);

//  @Select("select * from work_white_list where user_id=#{userId}")
  WorkWhiteEntity findOne(String userId);

}
