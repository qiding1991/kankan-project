package com.kankan.dao.entity;

import lombok.Data;


@Data
public class KankanUserRole  extends BaseEntity{
  private String userId;
  private String roleId;
}
