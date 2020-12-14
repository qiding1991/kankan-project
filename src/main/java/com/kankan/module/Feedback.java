package com.kankan.module;

import com.kankan.service.FeedbackService;

import lombok.Builder;
import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */
@Data
@Builder
public class Feedback {
    private Long userId;
    private String feedback;

    public void addFeedback(FeedbackService feedbackService) {
           feedbackService.addFeedback(this);
    }
}
