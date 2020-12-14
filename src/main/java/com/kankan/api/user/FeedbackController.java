package com.kankan.api.user;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.module.Feedback;
import com.kankan.param.FeedBackParam;
import com.kankan.service.FeedbackService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
@Validated
@Api(tags = "反馈")
@RestController
@RequestMapping("feedback")
public class FeedbackController extends BaseController {

    private FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @ApiOperation("添加用户反馈")
    @PostMapping("add")
    public CommonResponse addFeedBack(@RequestBody FeedBackParam param){
          Feedback feedback=param.toFeedBack();
          feedback.addFeedback(feedbackService);
          return success();
    }

}
