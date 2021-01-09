package com.kankan.param;

import lombok.Data;

@Data
public class UserApplyParam {
   private String userId;
   private String idUrl;
   private String photo;
   private String username;
   private String remark;
   private String email;
}
