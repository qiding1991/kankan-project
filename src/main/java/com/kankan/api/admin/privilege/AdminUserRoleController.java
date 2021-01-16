package com.kankan.api.admin.privilege;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.module.privilege.UserRole;
import com.kankan.service.UserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@Api(tags = "管理后台-角色管理-新闻")
@RestController
@RequestMapping("admin/role")
public class AdminUserRoleController extends BaseController {

  @Autowired
  private UserRoleService userRoleService;

  @ApiOperation("创建角色")
  @PostMapping("add")
  public CommonResponse add(@RequestBody UserRole userRole) {
    userRole.saveToDb(userRoleService);
    return success();
  }

  @ApiOperation("角色列表")
  @PostMapping("list")
  public CommonResponse list(){
      List<UserRole> infoList=  userRoleService.findAll();
      return success(infoList);
  }




}
