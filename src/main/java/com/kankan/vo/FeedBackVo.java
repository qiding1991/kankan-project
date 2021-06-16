package com.kankan.vo;

import com.kankan.module.Feedback;
import com.kankan.service.UserService;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;
@Data
public class FeedBackVo {
  private String id;
  private String userId;
  private String username;
  private String feedback;
  private List<String> pictures;
  private Long createTime;

  public FeedBackVo(Feedback feedback, UserService userService) {
    BeanUtils.copyProperties(feedback,this);
    this.username=userService.getUser(userId).getUsername();
  }
}
