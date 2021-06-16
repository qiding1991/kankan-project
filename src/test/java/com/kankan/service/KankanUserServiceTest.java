package com.kankan.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KankanUserServiceTest {

  @Autowired
  private KankanUserService userService;

  @Test
  void incrFollowCount() {
       userService.incrFollowCount("2");
       userService.incrFollowCount("2");
       userService.incrFollowCount("2");
  }

  @Test
  void decrFollowCount() {
      userService.decrFollowCount("1");
  }

  @Test
  void incrReadCount(){
     userService.incrReadCount("1");
  }

}
