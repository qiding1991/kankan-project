package com.kankan.module;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.kankan.service.CommentService;

import com.kankan.service.KankanUserService;
import com.kankan.vo.KankanCommentVo;
import lombok.Builder;
import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
@Builder
@Data
public class KankanComment {
    private Long id;
    private String resourceId;
    private Long parentId = 0L;
    private String commentText;
    private Long userId;
    private Integer thumpCount=0;
    private Long createTime;

    public void save(CommentService commentService) {
        commentService.saveComment(this);
    }

    public List<KankanComment> fromMe(CommentService commentService) {
        return commentService.fromMe(this);

    }

    public List<KankanComment> toMe(CommentService commentService) {
        return commentService.toMe(this);
    }

    public List<KankanCommentVo> resourceCommentInfo(CommentService commentService, KankanUserService userService) {
        //获取当前resource的所有评价
        List<KankanComment> commentList = commentService.findResourceComment(resourceId);
        List<KankanCommentVo> voList = commentList.stream().map(kankanComment -> new KankanCommentVo(kankanComment, userService)).collect(Collectors.toList());

        List<KankanCommentVo> rootComment = new ArrayList<>();

        Map<Long, KankanCommentVo> commentVoMap = new HashMap<>();
        voList.forEach(vo -> commentVoMap.put(vo.getId(),vo));

        voList.forEach(vo->{
            if (vo.getParentId() == 0) {
                rootComment.add(vo);
            } else {
                commentVoMap.get(vo.getParentId()).getChildren().add(vo);
            }
        });
        return rootComment;
    }

    public void incrementThumpCount(CommentService commentService) {
        commentService.incrementThumpCount(id);
    }
}
