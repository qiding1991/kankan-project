package com.kankan.dao.entity;

import com.kankan.module.Follow;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FollowEntity extends BaseEntity{
  private Long userId;
  private Long followId;
  public FollowEntity(Follow follow) {
    BeanUtils.copyProperties(follow,this);
  }

}

