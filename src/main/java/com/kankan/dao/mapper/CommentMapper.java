package com.kankan.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kankan.dao.entity.CommentEntity;
import com.kankan.module.KankanComment;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
@Mapper
public interface CommentMapper {
  void insert(CommentEntity entity);

  List<CommentEntity> findByUserId(Long userId);

  List<KankanComment> myComment(List<CommentEntity> condition);

  List<CommentEntity> reply(List<CommentEntity> condition);

  List<CommentEntity> findByCondition(List<KankanComment> workCondition);

  List<CommentEntity> findResourceComment(String resourceId);

  @Update("update comment set thump_count=thump_count+1 where id=#{id}")
  void incrementThumpCount(Long id);

  @Select("select * from comment where id=#{id}")
  CommentEntity findById(Long id);
}
