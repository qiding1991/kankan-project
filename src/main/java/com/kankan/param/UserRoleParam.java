package com.kankan.param;

import lombok.Data;

import java.util.List;

@Data
public class UserRoleParam {
   private String userEmail;
   private String roleId;
   private String userPhoto;
   private String username;
   private String password;
   private List<String> privilege;
}
