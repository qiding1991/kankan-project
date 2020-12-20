package com.kankan.vo.comment;

import com.kankan.module.KankanComment;
import com.kankan.module.KankanUser;
import com.kankan.module.KankanWork;
import com.kankan.module.MediaResource;
import lombok.Data;

@Data
public class WorkCommentVo extends BaseCommentVo {
  private String title;
  private String subTitle;
  private Integer readCount;

  public WorkCommentVo(KankanWork kankanWork, KankanUser kankanUser, KankanComment item, MediaResource resource) {
    this.setTitle(kankanWork.getTitle());
    this.setSubTitle(kankanUser.getUserName() + "|专栏");
    this.setReadCount(resource.getReadCount());
    this.setPublishTime(kankanWork.getPublishTime());
    this.setCommentText(item.getCommentText());
    this.setCommentTime(item.getCreateTime());
    this.setUsername(kankanUser.getUserName());
    this.setId(item.getId());
    this.setItemType(resource.getMediaType());
    this.setResourceId(resource.getResourceId());
  }
}
