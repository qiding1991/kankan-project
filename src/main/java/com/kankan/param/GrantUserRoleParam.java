package com.kankan.param;

import lombok.Data;

@Data
public class GrantUserRoleParam {
  private Long userId;
  private String roleId;
}
