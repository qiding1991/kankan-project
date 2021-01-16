package com.kankan.param;

import lombok.Data;

@Data
public class ApplyUpdateParam {
  private Long userId;
  private Integer applyStatus;
  private String roleId;
}
