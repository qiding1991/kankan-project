package com.kankan.api.admin.user;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.module.KankanUser;
import com.kankan.param.KankanUserParam;
import com.kankan.service.KankanUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@Api(tags = "管理后台-看看作者-管理")
@RestController
@RequestMapping("admin/kankan/user")
public class AdminKankanUserController  extends BaseController {

    private KankanUserService kankanUserService;

    public AdminKankanUserController(KankanUserService kankanUserService) {
        this.kankanUserService = kankanUserService;
    }

    @ApiOperation("创建一个看看作者")
    @PostMapping("create")
    public CommonResponse create(@RequestBody KankanUserParam param){

        //TODO 判断用户是否存在

        KankanUser kankanUser=param.toUser();
        kankanUser.save(kankanUserService);
        return success();
    }

    @ApiOperation("获取作者列表")
    @PostMapping("list")
    public CommonResponse list(){
        KankanUser kankanUser= KankanUser.builder().build();
        List<KankanUser> userList= kankanUser.list(kankanUserService);
        return success(userList);
    }
}
