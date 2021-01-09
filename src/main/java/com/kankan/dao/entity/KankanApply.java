package com.kankan.dao.entity;

import lombok.Data;

@Data
public class KankanApply extends BaseEntity{
  private Long id;
  private Long userId;
  private String idUrl;
  private String photo;
  private String username;
  private String remark;
  private String email;
}
