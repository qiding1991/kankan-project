package com.kankan.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kankan.dao.entity.HotPointEntity;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-04
 */
@Mapper
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
    List<HotPointEntity> findHotInfo(Long offset, Integer size);

    @Select("select * from hot_info")
    List<HotPointEntity> findAll();

    @Select("select * from hot_info where item_id=#{itemId} and item_type=#{itemType}")
    HotPointEntity findByItemTypeAndItemId(int itemType, Long itemId);
}
