package com.kankan.api.admin.feedback;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.module.Feedback;
import com.kankan.service.FeedbackService;
import com.kankan.service.UserService;
import com.kankan.vo.FeedBackVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Validated
@Api(tags = "管理后台-反馈管理")
@RestController
@RequestMapping("admin/feedback")
public class AdminFeedBackController  extends BaseController {

  @Autowired
  private FeedbackService feedbackService;
  @Autowired
  private UserService userService;

  @ApiOperation("获取反馈列表")
  @GetMapping("list")
  public CommonResponse list(){
    List<Feedback> feedbackList= feedbackService.feedbackList();
    List<FeedBackVo> resultList= feedbackList.stream().map(feedback -> new FeedBackVo(feedback,userService)).collect(Collectors.toList());
    return success(resultList);
  }

}
