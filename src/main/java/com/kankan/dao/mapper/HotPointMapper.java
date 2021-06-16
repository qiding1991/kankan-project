package com.kankan.dao.mapper;

import java.util.List;

import com.kankan.module.HotPoint;

import com.kankan.dao.entity.HotPointEntity;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Options;
//import org.apache.ibatis.annotations.Select;
//import org.apache.ibatis.annotations.Update;
//import org.springframework.stereotype.Service;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-04
 */
//@Mapper
public interface HotPointMapper {
    /**
     * 插入热点
     * @param hotPointEntity
     */
    void insert(HotPointEntity hotPointEntity);

    /**
     * 分页获取热点
     * @param offset
     * @param size
     * @return
     */
    List<HotPointEntity> findHotInfo(String offset, Integer size);

//    @Select("select * from hot_info")
    List<HotPointEntity> findAll();

//    @Select("select * from hot_info where item_id=#{itemId} and item_type=#{itemType}")
    HotPointEntity findByItemTypeAndItemId(int itemType, String itemId);


//    @Update("delete from hot_info where item_id=#{itemId} and item_type=#{itemType}")
    void delByItemTypeAndItemId(int itemType, String itemId);

//    @Select("select * from hot_info where  item_type in (${itemType})  limit #{limit}")
    List<HotPoint> findByItemType(String itemType, Integer limit);
}
