package com.kankan.dao.entity;

import lombok.Data;


@Data
public class KankanUserRole  extends BaseEntity{
  private Long id;
  private Long userId;
  private String roleId;
}
