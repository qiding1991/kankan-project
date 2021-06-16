package com.kankan.vo.comment;

import lombok.Data;

@Data
public class BaseCommentVo {
  private String id;
  private String username;
  private Long publishTime;
  private String resourceId;
  private Integer itemType;
  private Long commentTime;
  private String commentText;
}
