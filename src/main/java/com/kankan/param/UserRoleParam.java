package com.kankan.param;

import lombok.Data;

@Data
public class UserRoleParam {
   private String userEmail;
   private String roleId;
   private String photo;
   private String username;
   private String password;
}
