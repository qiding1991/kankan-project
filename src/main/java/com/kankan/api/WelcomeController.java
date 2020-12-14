package com.kankan.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "根目录，忽略")
@RestController
public class WelcomeController {
    @ApiOperation("项目欢迎根目录")
    @GetMapping("/")
    public String  welcome(){
        return "success";
    }
}
