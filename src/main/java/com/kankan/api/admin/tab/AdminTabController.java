package com.kankan.api.admin.tab;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.module.Tab;
import com.kankan.param.tab.TabAdd;
import com.kankan.param.tab.TabUpdate;
import com.kankan.service.TabService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-02
 */
@Api(tags = "管理后台-主页tab-管理")
@RestController
@RequestMapping("admin/tab")
public class AdminTabController extends BaseController {

    private TabService tabService;

    public AdminTabController(TabService tabService) {
        this.tabService = tabService;
    }

    @ApiOperation("主页tab列表")
    @GetMapping("list")
    public CommonResponse list() {
        Tab tab = Tab.defaultTab();
        List<Tab> tabList = tab.tabList(tabService);
        return success(tabList);
    }

    @ApiOperation("主页tab更新")
    @PostMapping("update")
    public CommonResponse update(@Valid  @RequestBody TabUpdate tabUpdate) {
        Tab tab = tabUpdate.toTab();
        tab.save2Db(tabService);
        return success();
    }

    @ApiOperation("主页tab添加")
    @PostMapping("add")
    public CommonResponse add(@Valid @RequestBody TabAdd request) {
        Tab tab = request.toTab();
        tab.insert2Db(tabService);
        return success();
    }
}
