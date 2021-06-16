package com.kankan.module;

import com.kankan.service.FeedbackService;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */
@Document(value = "feed-back")
@Data
@Builder
public class Feedback {
  @Id
  private String id;
  private String userId;
  private String username;
  private String feedback;
  private List<String> pictures;
  private Long createTime;
//
//  public void addFeedback(FeedbackService feedbackService) {
//    feedbackService.addFeedback(this);
//  }
}
