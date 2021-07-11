package com.kankan.service;

import com.kankan.module.User.ThreePartLogin;
import com.kankan.param.user.ThreePartLoginParam;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kankan.dao.entity.UserEntity;
import com.kankan.dao.mapper.UserMapper;
import com.kankan.module.User;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

  @Resource
  private UserMapper userMapper;

  public UserEntity findUserByEmail(String userEmail) {
    return userMapper.findUserByEmail(userEmail);
  }

  public UserEntity createUser(User user) {
    UserEntity entity = UserEntity.createUser(user);
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

  public List<User> findUser(User user) {
    UserEntity userEntity = new UserEntity();
    userEntity.setUsername(user.getUsername());
    userEntity.setUserEmail(user.getUserEmail());
    List<UserEntity> infoList = userMapper.findUser(userEntity);
    return infoList.stream().map(UserEntity::toUser).collect(Collectors.toList());
  }

  public User getUser(String userId) {
    UserEntity userEntity = userMapper.findUserById(userId);
    userEntity = Optional.ofNullable(userEntity).orElse(new UserEntity());
    return userEntity.toUser();
  }


  public UserEntity byThreePartLoginParam(ThreePartLoginParam loginParam) {
    ThreePartLogin threePartLogin = ThreePartLogin.builder()
        .threePartType(loginParam.getThreePartType())
        .threePartId(loginParam.getThreePartId())
        .build();
    UserEntity userEntity = userMapper.findByThreePart(threePartLogin);
    return userEntity;
  }

  public void addThreeAccount(String userId ,ThreePartLogin threeAccount) {
    userMapper.addThreeAccount(userId, threeAccount);
  }
}
