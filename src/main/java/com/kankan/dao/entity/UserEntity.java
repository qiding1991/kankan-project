package com.kankan.dao.entity;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import com.kankan.module.User;

import lombok.Data;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {
    private Long id;
    private String userEmail="";
    private String password="";;
    private String username="";;
    private String userPhoto="";;
    private Integer status=1;
    private Long createTime= Instant.now().toEpochMilli();
    private Long updateTime= Instant.now().toEpochMilli();

  public UserEntity(User user) {
       this.userEmail=user.getUserEmail();
       this.password=user.getPassword();
       this.username=user.getUsername();
       this.userPhoto=user.getUserPhoto();
  }

  public static UserEntity createUser(User user) {
      UserEntity userEntity=new UserEntity(user);
      return userEntity;
    }


    public static UserEntity registerUserEntity(User user) {
        UserEntity userEntity=new UserEntity();
        userEntity.username=user.getUsername();
        userEntity.password=user.getPassword();
        userEntity.id=user.getUserId();
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
        .userId(id)
        .password(password)
        .userPhoto(userPhoto)
        .username(username)
        .userEmail(userEmail)
        .build();
      return user;
    }

}
