package com.kankan.vo.tab;

import com.kankan.constant.EnumItemType;
import com.kankan.module.KankanUser;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class UserItemVo extends TabItemVo {
  private String userName;
  private Long userId;
  private String remark;
  private String picture;
  private Long fansCount;
  private Long followCount;
  private Long readCount;
  private boolean followStatus;

  public UserItemVo(KankanUser user) {
    BeanUtils.copyProperties(user, this);
    this.setItemType(EnumItemType.KAN_KAN_USER.getCode());
    this.setItemId(userId);
  }
}
