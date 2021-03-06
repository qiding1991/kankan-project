package com.kankan.param;

import com.kankan.module.Follow;
import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */
@Data
public class FollowParam {
  private Long userId;
  private Long followId;


  public Follow toFollow() {
     return Follow.builder().followId(followId).userId(userId).build();
  }
}
