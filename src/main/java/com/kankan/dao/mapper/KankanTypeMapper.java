package com.kankan.dao.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

import com.kankan.dao.entity.KankanTypeEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */
@Mapper
public interface KankanTypeMapper {
    void insert(KankanTypeEntity entity);


    void update(KankanTypeEntity kankanTypeEntity);

    @Delete("delete from kankan_type where id=#{id}")
    void delById(Long id);

    @Select("select * from kankan_type order by type_order desc")
    List<KankanTypeEntity> findAll();
}
