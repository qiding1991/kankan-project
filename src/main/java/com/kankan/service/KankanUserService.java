package com.kankan.service;

import com.kankan.dao.entity.KankanUserEntity;
import com.kankan.dao.mapper.KankanUserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import com.kankan.module.KankanUser;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-05
 */
@Log4j2
@Service
public class KankanUserService {

  @Resource
  private KankanUserMapper kankanUserMapper;


  public KankanUser findUser(Long userId) {
    log.info("获取用户信息，userId={}", userId);
    KankanUserEntity userEntity = kankanUserMapper.findByUserId(userId);
    final KankanUser user = new KankanUser(userEntity);
    return user;
  }

  public void createUser(KankanUser kankanUser) {
    KankanUserEntity entity = new KankanUserEntity(kankanUser);
    kankanUserMapper.insert(entity);
  }

  public List<KankanUser> findAll() {
    List<KankanUserEntity> userEntityList = kankanUserMapper.findAll();
    return userEntityList.stream().map(KankanUserEntity::parse).collect(Collectors.toList());
  }

  public List<KankanUser> findUserByPageInfo(Long offset, Integer size) {
    log.info("参数，offset={},size={}", offset, size);
    List<KankanUserEntity> userEntityList = kankanUserMapper.findByPage(offset, size);
    return userEntityList.stream().map(KankanUserEntity::parse).collect(Collectors.toList());
  }

  public List<KankanUser> findUserByType(Long userType) {
    List<KankanUserEntity> userEntityList = kankanUserMapper.findByType(userType);
    return userEntityList.stream().map(KankanUserEntity::parse).collect(Collectors.toList());
  }

  public void updateUserType(Long userId, Long userType) {
       kankanUserMapper.updateUserType(userId,userType);
  }

  public void updateUserRecommendStatus(Long userId, Integer recommendStatus) {
         kankanUserMapper.updateUserRecommendStatus(userId,recommendStatus);
  }


  public void incrFollowCount(Long followId) {
    kankanUserMapper.updateFollowCount(followId,1);
  }

  public void decrFollowCount(Long followId) {
    kankanUserMapper.updateFollowCount(followId,-1);
  }
}
