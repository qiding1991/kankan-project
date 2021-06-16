package com.kankan.module;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.google.common.collect.ImmutableMap;
import com.kankan.dao.entity.UserEntity;
import com.kankan.service.CacheService;
import com.kankan.service.MailSender;
import com.kankan.service.TokenService;
import com.kankan.service.UserService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
public class User {
  private String userEmail;
  private String userId;
  private Boolean empty = false;
  private String username;
  private String password;
  private String userPhoto;

  public static User toUser(TokenService tokenService, String userToken) {
    return tokenService.findUserByToken(userToken);
  }


  public Map<String, String> tokenMap() {

    Map<String, String> tokenMap = new HashMap<>(ImmutableMap.of("userId", userId.toString(), "userEmail", userEmail));
    if (StringUtils.hasText(this.username)) {
      tokenMap.put("username", username);
    }

    if (StringUtils.hasText(this.password)) {
      tokenMap.put("password", password);
    }
    if (StringUtils.hasText(this.userPhoto)) {
      tokenMap.put("userPhoto", userPhoto);
    }
    return tokenMap;
  }


  /**
   * 控对象
   */
  public static User emptyUser() {
    return User.builder().empty(true).build();
  }

  public Boolean isEmpty() {
    return empty;
  }

  /**
   * 发送激活码
   */
  public void sendActiveCode(MailSender mailSender, CacheService cacheService) {
    String activeCode = mailSender.sendActiveSmsCode(this.userEmail);
    cacheService.cacheActiveCode("sendActiveCode:" + userEmail, activeCode);
  }

  /**
   * 判断邮箱是否存在
   */
  public boolean emailNotExists(UserService userService) {
    UserEntity userEntity = userService.findUserByEmail(userEmail);
    return ObjectUtils.isEmpty(userEntity);
  }

  /**
   * 存入数据库
   */
  public User create(UserService userService) {
    UserEntity userEntity = userService.createUser(this);
    this.userId = userEntity.getId();
    return this;
  }

  /**
   * 生成一个的code
   */
  public String generateVerifyToken(TokenService tokenService, User user) {
    return tokenService.createUserToken(user);
  }

  /**
   * 校验code
   */
  public boolean checkActiveCode(CacheService cacheService, String smsCode) {
    return smsCode.equalsIgnoreCase(cacheService.getActiveCode("sendActiveCode:" + userEmail));
  }

  public boolean isNotEmpty() {
    return empty == false;
  }

  public User username(String username) {
    this.username = username;
    return this;
  }

  public User password(String password) {
    this.password = password;
    return this;

  }

  public User register(UserService userService) {
    userService.register(this);
    return this;
  }

  public String generateUserToken(TokenService tokenService) {
    String token = tokenService.createUserToken(this);
    return token;
  }

  public void setUserPhoto(String userPhoto) {
    this.userPhoto = userPhoto;
  }

  public void refreshToken(String userToken, TokenService tokenService) {
    tokenService.refreshToken(userToken, this);
  }

  public void saveBaseInfo(UserService userService) {
    userService.updateBaseInfo(this);
  }

  public void updateUser(UserService userService) {
    userService.updateBaseInfo(this);
  }
}
