package com.kankan.vo;

import com.kankan.dao.entity.KankanApply;
import com.kankan.dao.entity.UserEntity;
import com.kankan.module.User;
import com.kankan.module.privilege.UserPrivilege;
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
  private String password;

  private String username;
  private String userPhoto;
  private Integer applyStatus;
  private Boolean isKankan;

  private List<String> privilege;


  public UserDetailVo(UserEntity user) {
    this.userId = user.getId();
    this.userEmail = user.getUserEmail();
    this.password = user.getPassword();
    this.username = user.getUsername();
    this.userPhoto = user.getUserPhoto();
  }
  public UserDetailVo(User user) {
    this.userId = user.getUserId();
    this.userEmail = user.getUserEmail();
    this.password = user.getPassword();
    this.username = user.getUsername();
    this.userPhoto = user.getUserPhoto();
  }




  public UserDetailVo(UserEntity user, UserPrivilege userPrivilege) {
    this.userId = user.getId();
    this.userEmail = user.getUserEmail();
    this.password = user.getPassword();
    this.username = user.getUsername();
    this.userPhoto = user.getUserPhoto();
    this.privilege = userPrivilege.getPrivilege();
    this.isKankan=false;
  }

  public UserDetailVo(User user, UserPrivilege userPrivilege) {
    this.userId = user.getUserId();
    this.userEmail = user.getUserEmail();
    this.password = user.getPassword();
    this.username = user.getUsername();
    this.userPhoto = user.getUserPhoto();
    this.privilege = userPrivilege.getPrivilege();
    this.isKankan=false;
  }


  public UserDetailVo(UserEntity user, KankanCompanyApply companyApply) {
    this.userId = user.getId();
    this.userEmail = user.getUserEmail();
    this.password = user.getPassword();
    this.username = user.getUsername();
    this.userPhoto = user.getUserPhoto();
    this.privilege = companyApply.getPrivilege();
    this.applyStatus=companyApply.getApplyStatus();
    this.isKankan=this.applyStatus==2;
  }


  public UserDetailVo(UserEntity user, KankanApply kankanApply) {
    this.userId = user.getId();
    this.userEmail = user.getUserEmail();
    this.password = user.getPassword();
    this.username = user.getUsername();
    this.userPhoto = user.getUserPhoto();
    this.privilege = kankanApply.getPrivilege();
    this.applyStatus =kankanApply.getApplyStatus();
    this.isKankan=kankanApply.getApplyStatus()==2;
  }

  public UserDetailVo(User user, KankanCompanyApply companyApply) {
    this.userId = user.getUserId();
    this.userEmail = user.getUserEmail();
    this.password = user.getPassword();
    this.username = user.getUsername();
    this.userPhoto = user.getUserPhoto();
    this.privilege = companyApply.getPrivilege();
    this.applyStatus=companyApply.getApplyStatus();
    this.isKankan=companyApply.getApplyStatus()==2;
  }


  public UserDetailVo(User user, KankanApply kankanApply) {
    this.userId = user.getUserId();
    this.userEmail = user.getUserEmail();
    this.password = user.getPassword();
    this.username = user.getUsername();
    this.userPhoto = user.getUserPhoto();
    this.privilege = kankanApply.getPrivilege();
    this.applyStatus =kankanApply.getApplyStatus();
    this.isKankan=kankanApply.getApplyStatus()==2;
  }



  public void addApplyStatus(KankanCompanyApply kankanCompanyApply) {
    this.applyStatus = kankanCompanyApply.getApplyStatus();
    this.isKankan=this.applyStatus==2;
  }

  public void addApplyStatus(KankanApply kankanApply) {
    this.applyStatus = kankanApply.getApplyStatus();
    this.isKankan=this.applyStatus==2;
  }

  public void addApplyStatus(Object applyInfo) {
    if (applyInfo == null) {
      return;
    }

    if (applyInfo instanceof KankanApply) {
      addApplyStatus((KankanApply) applyInfo);
    }
    if (applyInfo instanceof KankanCompanyApply) {
      addApplyStatus((KankanCompanyApply) applyInfo);
    }

  }

}
