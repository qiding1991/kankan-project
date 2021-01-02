package com.kankan.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.kankan.module.KankanUser;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.kankan.dao.entity.CommentEntity;
import com.kankan.dao.entity.KankanRecommendEntity;
import com.kankan.dao.mapper.CommentMapper;
import com.kankan.module.KankanComment;
import com.kankan.module.KankanRecommend;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
@Service
public class CommentService {

  @Resource
  private CommentMapper commentMapper;

  public void saveComment(KankanComment comment) {
    CommentEntity entity = new CommentEntity();
    BeanUtils.copyProperties(comment, entity);
    commentMapper.insert(entity);
  }

  public List<KankanComment> fromMe(KankanComment kankanComment) {
    List<CommentEntity> entityList = entityList(kankanComment);
    List<KankanComment> kankanComments = entityList.stream().map(CommentEntity::parse).collect(Collectors.toList());
    kankanComments.stream().filter(comment -> comment.getParentId() > 0)
      .forEach(comment -> {
        Long id = comment.getParentId();
        CommentEntity commentEntity = commentMapper.findById(id);
        comment.setUserId(commentEntity.getUserId());
      });
    return kankanComments;
  }



  public List<KankanComment> myComment(KankanComment kankanComment) {
    List<CommentEntity> entityList= commentMapper.findByUserId(kankanComment.getUserId());
    List<KankanComment> kankanComments = entityList.stream().map(CommentEntity::parse).collect(Collectors.toList());
    return kankanComments;
  }

  private List<CommentEntity> entityList(KankanComment kankanComment) {
    List<CommentEntity> infoList = commentMapper.findByUserId(kankanComment.getUserId());
    return infoList;
  }

  public List<KankanComment> findComment(List<KankanComment> workCondition) {
    List<CommentEntity> resultList = commentMapper.findByCondition(workCondition);
    List<KankanComment> infoList = resultList.stream().map(CommentEntity::parse).collect(Collectors.toList());
    return infoList;
  }

  public List<KankanComment> findResourceComment(String resourceId) {
    List<CommentEntity> resultList = commentMapper.findResourceComment(resourceId);
    List<KankanComment> infoList = resultList.stream().map(CommentEntity::parse).collect(Collectors.toList());
    return infoList;
  }

  public void incrementThumpCount(Long id) {
    commentMapper.incrementThumpCount(id);
  }

  public void decreaseThumpCount(Long id) {
    commentMapper.decreaseThumpCount(id);
  }
}
