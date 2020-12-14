package com.kankan.api.user;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.module.Tab;
import com.kankan.service.TabService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-02
 */
@Validated
@Api(tags = "主页-tab-列表")
@RestController
@RequestMapping("tab")
public class TabController extends BaseController {

    private TabService tabService;

    public TabController(TabService tabService) {
        this.tabService = tabService;
    }

    @ApiOperation("主页tab列表")
    @PostMapping("list")
    public CommonResponse list() {
        Tab tab = Tab.defaultTab();
        List<Tab> tabList = tab.tabList(tabService);
        return success(tabList);
    }
}
