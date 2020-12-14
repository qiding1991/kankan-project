package com.kankan.dao.mapper;

import com.kankan.module.HeaderLine;
import org.apache.ibatis.annotations.Mapper;

import com.kankan.dao.entity.HeaderLineInfoEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface HeaderLineInfoMapper {
    void insert(HeaderLineInfoEntity headerLineInfoEntity);

    HeaderLineInfoEntity findHeaderLineInfo(Long tabId);

    @Select("select * from header_line_info  where id=#{itemId}")
    HeaderLine findHeaderLineById(Long itemId);

    @Select("select * from header_line_info")
    List<HeaderLineInfoEntity> findHeaderLine();
}
