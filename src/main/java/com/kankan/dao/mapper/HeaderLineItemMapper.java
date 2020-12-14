package com.kankan.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kankan.dao.entity.HeaderLineItemEntity;
@Mapper
public interface HeaderLineItemMapper {
    void insert(HeaderLineItemEntity itemEntity);
    List<HeaderLineItemEntity> findHeaderLineItem(Long headerLineId);
}
