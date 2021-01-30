package com.kankan.api.user;

import com.kankan.module.User;
import com.kankan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.module.Feedback;
import com.kankan.param.FeedBackParam;
import com.kankan.service.FeedbackService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
@Validated
@Api(tags = "反馈")
@RestController
@RequestMapping("feedback")
public class FeedbackController extends BaseController {

  @Autowired
  private FeedbackService feedbackService;

  @Autowired
  private UserService userService;

  public FeedbackController(FeedbackService feedbackService) {
    this.feedbackService = feedbackService;
  }

  @ApiOperation("添加用户反馈")
  @PostMapping("add")
  public CommonResponse addFeedBack(@RequestBody FeedBackParam param) {
    //获取用户信息
    User user = userService.getUser(param.getUserId());
    Feedback feedback = param.toFeedBack(user.getUsername());
    feedbackService.addFeedBack(feedback);
    return success();
  }

  @ApiOperation("用户反馈列表")
  @GetMapping("list")
  public CommonResponse listFeedBack() {
    List<Feedback> feedbackList = feedbackService.feedbackList();
    return success(feedbackList);
  }
}
