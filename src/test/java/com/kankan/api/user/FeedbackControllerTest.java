package com.kankan.api.user;

import com.kankan.constant.CommonResponse;
import com.kankan.param.FeedBackParam;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@Log4j2
@SpringBootTest
class FeedbackControllerTest {

  @Autowired
  private FeedbackController feedbackController;

  @Test
  void addFeedBack() {
    FeedBackParam feedBackParam = new FeedBackParam();
    feedBackParam.setFeedback("46464646");
    feedBackParam.setUserId(4L);
    feedBackParam.setPictures(Arrays.asList("11111111111111"));
    feedbackController.addFeedBack(feedBackParam);
  }

  @Test
  void listFeedBack() {
    CommonResponse response = feedbackController.listFeedBack();
    log.info("response={}", response);
  }
}
