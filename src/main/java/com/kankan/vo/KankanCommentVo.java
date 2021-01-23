package com.kankan.vo;

import java.util.ArrayList;
import java.util.List;

import com.kankan.module.KankanComment;
import com.kankan.module.KankanUser;
import com.kankan.service.KankanUserService;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;

/**
 * @author <qiding@kuaishou.com>
 * Created on 2020-12-12
 */
@Data
public class KankanCommentVo {
  private Long id;
  private Long parentId;
  private String resourceId;
  private Integer thumpCount;
  private String commentText;
  private Long userId;
  private String userName;
  private Long createTime;
  private Boolean thumpStatus;//当前用户是否 false 未点赞 true

  private List<KankanCommentVo> children = new ArrayList<>();


  public KankanCommentVo(KankanComment kankanComment, KankanUserService userService) {
    BeanUtils.copyProperties(kankanComment, this);
    KankanUser user = userService.findUser(userId);
    this.userName = user.getUserName();
  }

  public static void addThumpStatus(List<KankanCommentVo> infoList, Long currentUserId) {
    for (KankanCommentVo item : infoList) {
      item.thumpStatus = currentUserId.equals(item.userId);
    }
  }

  public static void addThumpStatus(KankanCommentVo commentVo, Long currentUserId) {
    currentUserId=ObjectUtils.defaultIfNull(currentUserId,0L);
    commentVo.thumpStatus = currentUserId.equals(commentVo.userId);
  }

}
