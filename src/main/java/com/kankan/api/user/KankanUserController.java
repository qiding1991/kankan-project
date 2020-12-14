package com.kankan.api.user;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.module.KankanUser;
import com.kankan.service.KankanUserService;
import com.kankan.vo.KankanUserVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
@Validated
@Api(tags = "专栏用户接口")
@RestController
@RequestMapping("kankan/user")
public class KankanUserController extends BaseController {

    private KankanUserService kankanUserService;

    public KankanUserController(KankanUserService kankanUserService) {
        this.kankanUserService = kankanUserService;
    }

    @ApiOperation("作者基本信息-粉丝数、阅读量、关注基本信息")
    @GetMapping("baseInfo")
    public CommonResponse baseInfo(@RequestParam(value = "userId") Long userId) {
        KankanUser user = KankanUser.builder().userId(userId).build();
        user.completeInfo(kankanUserService);
        KankanUserVo kankanUserVo = user.toVo();
        return success(kankanUserVo);
    }


}
