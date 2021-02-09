package com.kankan.param;

import com.kankan.module.Feedback;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */
@Data
public class FeedBackParam {
  private Long userId;
  private String feedback;
  private List<String> pictures;

  public Feedback toFeedBack(String username) {
    return Feedback.builder()
      .userId(userId)
      .feedback(feedback)
      .pictures(this.pictures)
      .createTime(Instant.now().toEpochMilli())
      .build();
  }
}
