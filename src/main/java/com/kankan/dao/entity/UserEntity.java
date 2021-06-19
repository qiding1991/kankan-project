package com.kankan.dao.entity;

import com.kankan.module.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity extends BaseEntity {

    private String userEmail="";
    private String password="";;
    private String username="";;
    private String userPhoto="";;


  public UserEntity(User user) {
       this.userEmail= ObjectUtils.defaultIfNull(user.getUserEmail(),"");
       this.password= ObjectUtils.defaultIfNull(user.getPassword(),"");
       this.username= ObjectUtils.defaultIfNull(user.getUsername(),"");;
       this.userPhoto= ObjectUtils.defaultIfNull(user.getUserPhoto(),"");
  }

  public static UserEntity createUser(User user) {
      UserEntity userEntity=new UserEntity(user);
      return userEntity;
    }


    public static UserEntity registerUserEntity(User user) {
        UserEntity userEntity=new UserEntity();
        userEntity.username=user.getUsername();
        userEntity.password=user.getPassword();
        userEntity.setId(user.getUserId());
        return userEntity;
    }

    public static UserEntity updateUserEntity(User user) {
        UserEntity userEntity=new UserEntity();
        BeanUtils.copyProperties(user,userEntity);
        userEntity.setId(user.getUserId());
        return userEntity;
    }

    public User toUser(){
      User user= User.builder()
        .userId(getId())
        .password(password)
        .userPhoto(userPhoto)
        .username(username)
        .userEmail(userEmail)
        .build();
      return user;
    }

}
