package com.kankan.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.kankan.dao.entity.AdEntity;
import com.kankan.module.KankanAd;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-03
 */
@Mapper
public interface KankanAdMapper {
    void insert(AdEntity adEntity);
    AdEntity findById(Long id);
    @Select("select * from ad_info")
    List<AdEntity> findAll();
}
