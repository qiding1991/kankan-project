package com.kankan.service;

import com.kankan.dao.entity.KankanUserEntity;
import com.kankan.dao.mapper.KankanUserMapper;
import com.kankan.module.User;
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

  @Resource
  private UserService userService;


  public KankanUser findUser(Long userId) {
    log.info("获取用户信息，userId={}", userId);
    KankanUserEntity userEntity = kankanUserMapper.findByUserId(userId);
    KankanUser user = new KankanUser(userEntity);
    user.addUserPhoto(userService);
    return user;
  }

  public void createUser(KankanUser kankanUser) {
    KankanUserEntity entity = new KankanUserEntity(kankanUser);
    kankanUserMapper.insert(entity);
  }

  public List<KankanUser> findAll() {
    List<KankanUserEntity> userEntityList = kankanUserMapper.findAll();
    List<KankanUser> infoList = userEntityList.stream().map(KankanUserEntity::parse).collect(Collectors.toList());
    infoList.forEach(userItem -> userItem.addUserPhoto(userService));
    return infoList;
  }

  public List<KankanUser> findUserByPageInfo(Long offset, Integer size) {
    log.info("参数，offset={},size={}", offset, size);
    List<KankanUserEntity> userEntityList = kankanUserMapper.findByPage(offset, size);
    List<KankanUser> infoList = userEntityList.stream().map(KankanUserEntity::parse).collect(Collectors.toList());
    infoList.forEach(userItem -> userItem.addUserPhoto(userService));
    return infoList;
  }

  public List<KankanUser> findUserByType(Long userType) {
    List<KankanUserEntity> userEntityList = kankanUserMapper.findByType(userType);
    List<KankanUser> infoList =  userEntityList.stream().map(KankanUserEntity::parse).collect(Collectors.toList());
    infoList.forEach(userItem -> userItem.addUserPhoto(userService));
    return infoList;
  }

  public void updateUserType(Long userId, Long userType) {
    kankanUserMapper.updateUserType(userId, userType);
  }

  public void updateUserRecommendStatus(Long userId, Integer recommendStatus) {
    kankanUserMapper.updateUserRecommendStatus(userId, recommendStatus);
  }


  public void incrFollowCount(Long userId) {
    kankanUserMapper.updateFollowCount(userId, 1);
  }

  public void decrFollowCount(Long userId) {
    kankanUserMapper.updateFollowCount(userId, -1);
  }

  public void incrFansCount(Long userId) {
    kankanUserMapper.updateFansCount(userId, 1);
  }

  public void decrFansCount(Long userId) {
    kankanUserMapper.updateFansCount(userId, -1);
  }


  public void incrReadCount(Long userId) {
    kankanUserMapper.updateReadCount(userId, 1);
  }


}
