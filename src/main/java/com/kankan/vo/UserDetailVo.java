package com.kankan.vo;

import com.kankan.dao.entity.KankanApply;
import com.kankan.dao.entity.UserEntity;
import com.kankan.module.User;
import com.kankan.param.KankanCompanyApply;
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
  private Integer applyStatus;


  private List<String> privilege;
  public UserDetailVo(UserEntity user,Object applyInfo) {
    this.userId=user.getId();
    this.userEmail=user.getUserEmail();
    this.password=user.getPassword();
    this.username=user.getUsername();
    this.userPhoto=user.getUserPhoto();
    if(applyInfo==null){
      return;

    }
    if(applyInfo instanceof KankanApply){
      this.privilege=((KankanApply) applyInfo).getPrivilegeList();
      this.applyStatus=((KankanApply) applyInfo).getApplyStatus();
    }

    if(applyInfo instanceof KankanCompanyApply){
      this.privilege=((KankanCompanyApply) applyInfo).getPrivilegeList();
      this.applyStatus=((KankanCompanyApply) applyInfo).getApplyStatus();
    }
  }

  public UserDetailVo(User user,Object applyInfo) {
    this.userId=user.getUserId();
    this.userEmail=user.getUserEmail();
    this.password=user.getPassword();
    this.username=user.getUsername();
    this.userPhoto=user.getUserPhoto();
    if(applyInfo==null){
      return;

    }
    if(applyInfo instanceof KankanApply){
      this.privilege=((KankanApply) applyInfo).getPrivilegeList();
      this.applyStatus=((KankanApply) applyInfo).getApplyStatus();
    }

    if(applyInfo instanceof KankanCompanyApply){
      this.privilege=((KankanCompanyApply) applyInfo).getPrivilegeList();
      this.applyStatus=((KankanCompanyApply) applyInfo).getApplyStatus();
    }
  }

}
