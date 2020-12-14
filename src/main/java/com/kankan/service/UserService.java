package com.kankan.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kankan.dao.entity.UserEntity;
import com.kankan.dao.mapper.UserMapper;
import com.kankan.module.User;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public UserEntity findUserByEmail(String userEmail) {
        return userMapper.findUserByEmail(userEmail);
    }

    public UserEntity createUser(String userEmail) {
        UserEntity entity= UserEntity.registerUserEntity(userEmail);
        userMapper.createUser(entity);
        return  entity;
    }


    public void register(User user) {
        UserEntity entity= UserEntity.registerUserEntity(user);
        userMapper.registerUser(entity);
    }

    public void updateBaseInfo(User user) {
        UserEntity entity= UserEntity.updateUserEntity(user);
        userMapper.updateUser(entity);
    }
}
