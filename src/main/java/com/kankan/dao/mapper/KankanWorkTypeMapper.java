package com.kankan.dao.mapper;

import com.kankan.module.KankanWorkTypeEntity;
//import org.apache.ibatis.annotations.*;

import java.util.List;

//@Mapper
public interface KankanWorkTypeMapper {


//  @Insert("insert into kankan_work_type(id,type_name,status,create_time,update_time) values(#{id},#{typeName},#{status},#{createTime},#{updateTime})")
//  @Options(useGeneratedKeys = true,keyProperty = "id")
  void insert(KankanWorkTypeEntity kankanWorkTypeEntity);

//  @Delete("delete from kankan_work_type where id=#{id}")
  void removeKankanWorkType(String id);

//  @Update("update kankan_work_type set type_name=#{typeName} where id=#{id}")
  void updateWorkType(String id, String typeName);

//  @Select("select * from kankan_work_type")
  List<KankanWorkTypeEntity> findAll();
}
