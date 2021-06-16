package com.kankan.api.user;

import com.kankan.param.FollowParam;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FollowControllerTest {

  @Autowired
  private FollowController followController;

  @Test
  void add() {
    FollowParam followParam=new FollowParam();
    followParam.setUserId("1");
    followParam.setFollowId("2");
    followController.add(followParam);
  }

  @Test
  void cancel() {
    FollowParam followParam=new FollowParam();
    followParam.setUserId("1");
    followParam.setFollowId("2");
    followController.cancel(followParam);
  }
}
