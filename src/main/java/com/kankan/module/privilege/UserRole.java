package com.kankan.module.privilege;

import com.kankan.service.UserRoleService;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "kankan-role")
@Data
public class UserRole {
   @Id
   @Indexed(unique = true)
   private String roleId;
   private String roleName;
   private List<String> privilegeList;

  public void saveToDb(UserRoleService userRoleService) {
      userRoleService.saveToDB(this);
  }
}
