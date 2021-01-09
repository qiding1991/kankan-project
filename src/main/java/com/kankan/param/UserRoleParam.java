package com.kankan.param;

import lombok.Data;

@Data
public class UserRoleParam {
   private String email;
   private String roleId;
   private String photo;
   private String username;
   private String password;
}
