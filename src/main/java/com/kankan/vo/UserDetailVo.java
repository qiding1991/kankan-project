package com.kankan.vo;

import com.kankan.dao.entity.UserEntity;
import com.kankan.module.User;
import com.kankan.module.privilege.Privilege;
import com.kankan.module.privilege.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDetailVo {
  private Long userId;
  private String userEmail;
  private String password;;
  private String username;;
  private String userPhoto;
  private String roleId;
  private String roleName;
  private List<String> privilege;
  public UserDetailVo(UserEntity user, UserRole userRole) {
    this.userId=user.getId();
    this.userEmail=user.getUserEmail();
    this.password=user.getPassword();
    this.username=user.getUsername();
    this.userPhoto=user.getUserPhoto();
    if(userRole!=null){
      this.roleId=userRole.getRoleId();
      this.roleName=userRole.getRoleName();
      this.privilege=userRole.getPrivilegeList();
    }

  }

  public UserDetailVo(User user, UserRole userRole) {
    this.userId=user.getUserId();
    this.userEmail=user.getUserEmail();
    this.password=user.getPassword();
    this.username=user.getUsername();
    this.userPhoto=user.getUserPhoto();
    if(userRole!=null){
      this.roleId=userRole.getRoleId();
      this.roleName=userRole.getRoleName();
      this.privilege=userRole.getPrivilegeList();
    }
  }

}
