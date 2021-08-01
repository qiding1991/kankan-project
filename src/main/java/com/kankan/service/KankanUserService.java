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
import java.util.function.Supplier;
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


  public KankanUser findUser(String userId) {
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

  public List<KankanUser> findAllOrderByTime(Boolean desc) {
    List<KankanUserEntity> userEntityList = kankanUserMapper.findAllOrderByTime(desc);
    List<KankanUser> infoList = userEntityList.stream().map(KankanUserEntity::parse).collect(Collectors.toList());
    infoList.forEach(userItem -> userItem.addUserPhoto(userService));
    return infoList;
  }



  public List<KankanUser> findUserByPageInfo(String offset, Integer size) {
    log.info("参数，offset={},size={}", offset, size);
    List<KankanUserEntity> userEntityList = kankanUserMapper.findByPage(offset, size);
    List<KankanUser> infoList = userEntityList.stream().map(KankanUserEntity::parse).collect(Collectors.toList());
    infoList.forEach(userItem -> userItem.addUserPhoto(userService));
    return infoList;
  }

  public List<KankanUser> findUserByType(String userType) {
    List<KankanUserEntity> userEntityList = kankanUserMapper.findByType(userType);
    List<KankanUser> infoList = userEntityList.stream().map(KankanUserEntity::parse).collect(Collectors.toList());
    infoList.forEach(userItem -> userItem.addUserPhoto(userService));
    return infoList;
  }

  public void updateUserType(String userId, String userType) {
    kankanUserMapper.updateUserType(userId, userType);
  }

  public void updateUserRecommendStatus(String userId, Integer recommendStatus) {
    kankanUserMapper.updateUserRecommendStatus(userId, recommendStatus);
  }


  public void incrFollowCount(String userId) {
    kankanUserMapper.updateFollowCount(userId, 1);
  }

  public void decrFollowCount(String userId) {
    kankanUserMapper.updateFollowCount(userId, -1);
  }

  public void incrFansCount(String userId) {
    kankanUserMapper.updateFansCount(userId, 1);
  }

  public void decrFansCount(String userId) {
    kankanUserMapper.updateFansCount(userId, -1);
  }


  public void incrReadCount(String userId) {
    kankanUserMapper.updateReadCount(userId, 1);
  }


  public void updateWhiteStatus(String userId, Integer whiteStatus) {
    kankanUserMapper.updateWhiteStatus(userId, whiteStatus);
  }

  public Integer whiteStatus(String userId) {
    KankanUserEntity userEntity = kankanUserMapper.findByUserId(userId);
    return userEntity.getWhiteStatus();
  }

  public List<KankanUser> findHotUserByReadCount(Integer limit) {
    List<KankanUserEntity> userEntityList = findBotUser(() -> "read_count", limit);
    List<KankanUser> infoList = userEntityList.stream().map(KankanUserEntity::parse).collect(Collectors.toList());
    infoList.forEach(userItem -> userItem.addUserPhoto(userService));
    return infoList;
  }

  private List<KankanUserEntity> findBotUser(Supplier<String> orderField, Integer limit) {
    return kankanUserMapper.findHotUser(orderField.get(), limit);
  }

  public List<KankanUser> findUser(String offset, Integer size, String keyword) {
    List<KankanUserEntity> userEntityList = kankanUserMapper.findByKeyword(offset, size, keyword);
    List<KankanUser> infoList = userEntityList.stream().map(KankanUserEntity::parse).collect(Collectors.toList());
    infoList.forEach(userItem -> userItem.addUserPhoto(userService));
    return infoList;
  }
}
