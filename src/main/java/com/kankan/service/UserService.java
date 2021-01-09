package com.kankan.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kankan.dao.entity.UserEntity;
import com.kankan.dao.mapper.UserMapper;
import com.kankan.module.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

  @Resource
  private UserMapper userMapper;

  public UserEntity findUserByEmail(String userEmail) {
    return userMapper.findUserByEmail(userEmail);
  }

  public UserEntity createUser(String userEmail) {
    UserEntity entity = UserEntity.registerUserEntity(userEmail);
    userMapper.createUser(entity);
    return entity;
  }


  public void register(User user) {
    UserEntity entity = UserEntity.registerUserEntity(user);
    userMapper.registerUser(entity);
  }

  public void updateBaseInfo(User user) {
    UserEntity entity = UserEntity.updateUserEntity(user);
    userMapper.updateUser(entity);
  }

  public List<User> findUser( User user) {
    UserEntity userEntity=new UserEntity();
    userEntity.setUsername(user.getUsername());
    userEntity.setUserEmail(user.getUserEmail());
    List<UserEntity> infoList = userMapper.findUser(userEntity);
    return infoList.stream().map(UserEntity::toUser).collect(Collectors.toList());
  }
}
