package com.kankan.vo.comment;

import com.kankan.module.*;
import lombok.Data;

@Data
public class WorkCommentVo extends BaseCommentVo {
  private String title;
  private String subTitle;
  private Integer readCount;

  public WorkCommentVo(KankanWork kankanWork, User user, KankanUser kankanUser, KankanComment item, MediaResource resource) {
    this.setTitle(kankanWork.getTitle());
    this.setSubTitle(kankanUser.getUserName() + "|专栏");
    this.setReadCount(resource.getReadCount());
    this.setPublishTime(kankanWork.getPublishTime());
    this.setCommentText(item.getCommentText());
    this.setCommentTime(item.getCreateTime());
    this.setUsername(user.getUsername());
    this.setId(item.getId());
    this.setItemType(resource.getMediaType());
    this.setResourceId(resource.getResourceId());
  }

}
