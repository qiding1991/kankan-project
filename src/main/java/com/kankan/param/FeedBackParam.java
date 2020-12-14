package com.kankan.param;

import com.kankan.module.Feedback;

import lombok.Builder;
import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */
@Data
public class FeedBackParam {
    private Long userId;
    private String feedback;
    public Feedback toFeedBack() {
       return Feedback.builder().userId(userId).feedback(feedback).build();
    }
}
