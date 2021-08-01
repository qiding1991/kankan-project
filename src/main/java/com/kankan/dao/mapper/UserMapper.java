package com.kankan.dao.mapper;

import com.kankan.dao.entity.UserEntity;
//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Select;

import com.kankan.module.User.ThreePartLogin;
import java.util.List;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-02
 */
//@Mapper
public interface UserMapper {

  /**
   * 通过邮箱获取用户信息
   *
   * @param userEmail
   * @return
   */
  UserEntity findUserByEmail(String userEmail);

  /**
   * 创建用户
   *
   * @param entity
   */
  void createUser(UserEntity entity);

  /**
   * 注册用户名，密码
   *
   * @param entity
   */
  void registerUser(UserEntity entity);

  /**
   * 更新用户信息
   *
   * @param entity
   */
  void updateUser(UserEntity entity);

  List<UserEntity> findUser(UserEntity userEntity);

//  @Select("select * from  user_info where id=#{userId}")
  UserEntity findUserById(String userId);

  UserEntity findByThreePart(ThreePartLogin threePartLogin);

  void addThreeAccount(String userId, ThreePartLogin threeAccount);

  void updatePassword(String userId, String password);

  void removeUser(String userId);

  Long userCount();
}
