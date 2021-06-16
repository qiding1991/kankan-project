package com.kankan;

import com.kankan.api.admin.user.AdminApplyController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KankanProjectApplicationTests {

  @Autowired
  private AdminApplyController adminApplyController;

  @Test
  void contextLoads() {
    adminApplyController.sendInnerMessage("38","xxxxx","helloworld");
  }

}
