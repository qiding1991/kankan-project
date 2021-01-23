package com.kankan.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.kankan.dao.entity.ThumpEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
@Mapper
public interface ThumpMapper {
  void insert(ThumpEntity entity);
  void remove(ThumpEntity entity);

  @Select("select * from thumbs where resource_id=#{resourceId} and comment_id=0 and user_id=#{userId}")
  List<ThumpEntity> findByResourceId(String resourceId,Long userId);

  @Select("select * from thumbs where comment_id=#{commentId} and user_id=#{userId}")
  List<ThumpEntity> findByCommentId(Long commentId,Long userId);
}
