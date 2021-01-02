package com.kankan.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.kankan.dao.entity.ThumpEntity;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
@Mapper
public interface ThumpMapper {
     void insert(ThumpEntity entity);

  void remove(ThumpEntity entity);
}
