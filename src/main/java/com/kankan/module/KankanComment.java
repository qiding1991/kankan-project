package com.kankan.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.kankan.service.CommentService;

import com.kankan.service.KankanUserService;
import com.kankan.service.UserService;
import com.kankan.vo.KankanCommentVo;
import lombok.Builder;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
@Log4j2
@Builder
@Data
public class KankanComment {
  private String id;
  private String resourceId;
  private String parentId = "0";
  private String commentText;
  private String userId;
  private Integer thumpCount = 0;
  private Long createTime;

  public void save(CommentService commentService) {
    commentService.saveComment(this);
  }

  public List<KankanComment> fromMe(CommentService commentService) {
    return commentService.fromMe(this);

  }

  public List<KankanComment> myComment(CommentService commentService) {
    return commentService.myComment(this);
  }

  public List<KankanCommentVo> resourceCommentInfo(CommentService commentService, UserService userService) {
    //获取当前resource的所有评价
    List<KankanComment> commentList = commentService.findResourceComment(resourceId);
    log.info("当前resourceId={},response={}", resourceId, commentList);
    List<KankanCommentVo> voList = commentList.stream().map(kankanComment -> new KankanCommentVo(kankanComment, userService)).collect(Collectors.toList());
    log.info("当前resourceId={},response={},voList={}", resourceId, commentList, voList);

    List<KankanCommentVo> rootComment = new ArrayList<>();

    Map<String, KankanCommentVo> commentVoMap = new HashMap<>();
    voList.forEach(vo -> commentVoMap.put(vo.getId(), vo));

    voList.forEach(vo -> {
      if (vo.getParentId() .equals("0")) {
        rootComment.add(vo);
      } else {
        if(commentVoMap.get(vo.getParentId())!=null){
          commentVoMap.get(vo.getParentId()).getChildren().add(vo);
        }
      }
    });
    return rootComment;
  }


  public KankanCommentVo resourceCommentInfo(CommentService commentService, UserService userService, String commentId) {
    //获取当前resource的所有评价
    List<KankanComment> commentList = commentService.findResourceComment(resourceId);
    log.info("当前resourceId={},response={}", resourceId, commentList);
    List<KankanCommentVo> voList = commentList.stream().map(kankanComment -> new KankanCommentVo(kankanComment, userService)).collect(Collectors.toList());
    log.info("当前resourceId={},response={},voList={}", resourceId, commentList, voList);
    List<KankanCommentVo> rootComment = new ArrayList<>();

    Map<String, KankanCommentVo> commentVoMap = new HashMap<>();
    voList.forEach(vo -> commentVoMap.put(vo.getId(), vo));

    voList.forEach(vo -> {
      if (vo.getParentId().equals("0")) {
        rootComment.add(vo);
      } else {
        if(commentVoMap.get(vo.getParentId())!=null){
          commentVoMap.get(vo.getParentId()).getChildren().add(vo);
        }
      }
    });
    return commentVoMap.get(commentId);
  }


  public void incrementThumpCount(CommentService commentService) {
    commentService.incrementThumpCount(id);
  }

  public void decreaseThumpCount(CommentService resourceService) {
    resourceService.decreaseThumpCount(id);
  }

  public String remove(CommentService commentService) {
    return commentService.removeById(this.getId());
  }
}
