package com.kankan.dao.mapper;

import java.util.List;


import com.kankan.dao.entity.HeaderLineItemEntity;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Select;
//import org.apache.ibatis.annotations.Update;

//@Mapper
public interface HeaderLineItemMapper {
  void insert(HeaderLineItemEntity itemEntity);

  List<HeaderLineItemEntity> findHeaderLineItem(String headerLineId);

//  @Select("select * from header_line_item where resource_id=#{resourceId}")
  HeaderLineItemEntity findByResourceId(String resourceId);

//  @Update("delete from header_line_item where header_line_id =#{headerLineId} and resource_id=#{resourceId}")
  void delByHeadLineIdAndResourceId(String headerLineId, String resourceId);


  void delByResourceId(String resourceId);
}
