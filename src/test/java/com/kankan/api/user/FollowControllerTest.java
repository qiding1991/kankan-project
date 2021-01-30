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
    followParam.setUserId(1L);
    followParam.setFollowId(2L);
    followController.add(followParam);
  }

  @Test
  void cancel() {
    FollowParam followParam=new FollowParam();
    followParam.setUserId(1L);
    followParam.setFollowId(2L);
    followController.cancel(followParam);
  }
}
