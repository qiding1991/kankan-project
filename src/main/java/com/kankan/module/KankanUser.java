package com.kankan.module;

import java.util.List;

import com.kankan.param.tab.TabPageInfo;
import com.kankan.service.UserService;
import com.kankan.vo.tab.UserItemVo;
import org.checkerframework.checker.units.qual.K;
import org.springframework.beans.BeanUtils;

import com.kankan.dao.entity.KankanUserEntity;
import com.kankan.service.KankanUserService;
import com.kankan.vo.KankanUserVo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class KankanUser {
  private String userName;
  private Long userId;
  private Long userType;
  private String picture;
  private String remark;
  private Integer recommendStatus;
  private Integer whiteStatus;
  private Long fansCount;
  private Long followCount;
  private Long readCount;

  public KankanUser(KankanUserEntity kankanUserEntity) {
    BeanUtils.copyProperties(kankanUserEntity, this);
  }

  public static KankanUser fromUserId(Long userId, KankanUserService userService) {
    return userService.findUser(userId);
  }

  public KankanUser findUser(KankanUserService userService) {
    return userService.findUser(userId);
  }

  public KankanUserVo toVo() {
    KankanUserVo kankanUserVo = new KankanUserVo();
    BeanUtils.copyProperties(this, kankanUserVo);
    return kankanUserVo;
  }

  public List<KankanUser> commonTypeUser(KankanUserService userService) {
    List<KankanUser> userEntityList = userService.findUserByType(this.userType);

    return userEntityList;
  }

  public List<KankanUser> commonType(KankanUserService userService) {
    return null;
  }

  public void save(KankanUserService kankanUserService) {
    kankanUserService.createUser(this);
  }

  public void completeInfo(KankanUserService userService) {
    KankanUser user = userService.findUser(userId);
    BeanUtils.copyProperties(user, this);
  }

  public List<KankanUser> list(KankanUserService kankanUserService) {
    List<KankanUser> infoList = kankanUserService.findAll();
    return infoList;
  }

  public List<KankanUser> findByPageInfo(KankanUserService kankanUserService, TabPageInfo pageInfo) {
    List<KankanUser> infoList = kankanUserService.findUserByPageInfo(pageInfo.getOffset(), pageInfo.getSize());
    return infoList;
  }

  public void addUserPhoto(UserService userService) {
    this.picture= userService.getUser(userId).getUserPhoto();
  }

}
