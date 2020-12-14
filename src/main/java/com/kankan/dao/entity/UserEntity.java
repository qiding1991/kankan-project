package com.kankan.dao.entity;

import java.time.Instant;

import org.springframework.beans.BeanUtils;

import com.kankan.module.User;

import lombok.Data;

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
    public static UserEntity registerUserEntity(String userEmail) {
          UserEntity user=new UserEntity();
          user.userEmail=userEmail;
          return user;
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
}
