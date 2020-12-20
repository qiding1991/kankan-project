package com.kankan.vo.comment;

import com.kankan.module.*;
import lombok.Data;

@Data
public class NewsCommentVo extends BaseCommentVo{
  private String title;
  private String subTitle;
  private Integer readCount;

  public NewsCommentVo(Tab newTab, News news, KankanUser kankanUser, KankanComment item, MediaResource resource) {
     this.setTitle(news.getTitle());
     this.setSubTitle(newTab.getTabName());

     this.setReadCount(resource.getReadCount());
     this.setPublishTime(news.getCreateTime());

     this.setCommentText(item.getCommentText());
     this.setCommentTime(item.getCreateTime());
     this.setUsername(kankanUser.getUserName());
     this.setId(item.getId());
     this.setItemType(resource.getMediaType());
     this.setResourceId(resource.getResourceId());
  }
}
