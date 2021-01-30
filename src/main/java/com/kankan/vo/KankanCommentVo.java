package com.kankan.vo;

import java.util.ArrayList;
import java.util.List;

import com.kankan.dao.mapper.ThumpMapper;
import com.kankan.module.KankanComment;
import com.kankan.module.KankanUser;
import com.kankan.module.User;
import com.kankan.service.KankanUserService;
import com.kankan.service.UserService;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

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


  public KankanCommentVo(KankanComment kankanComment, UserService userService) {
    BeanUtils.copyProperties(kankanComment, this);
    User user = userService.getUser(userId);
    this.userName = user.getUsername();
  }

  public static void addThumpStatus(List<KankanCommentVo> infoList, Long currentUserId, ThumpMapper thumpMapper) {
    for (KankanCommentVo item : infoList) {
      item.thumpStatus = !CollectionUtils.isEmpty(thumpMapper.findByCommentId(item.getId(), currentUserId));
    }
  }

  public static void addThumpStatus(KankanCommentVo commentVo, Long currentUserId, ThumpMapper thumpMapper) {
    currentUserId = ObjectUtils.defaultIfNull(currentUserId, 0L);
    commentVo.thumpStatus = !CollectionUtils.isEmpty(thumpMapper.findByCommentId(commentVo.getId(), currentUserId));
  }

}
