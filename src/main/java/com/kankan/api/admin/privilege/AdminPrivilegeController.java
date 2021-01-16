package com.kankan.api.admin.privilege;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.module.privilege.Privilege;
import com.kankan.service.PrivilegeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@Api(tags = "管理后台-权限管理-新闻")
@RestController
@RequestMapping("admin/privilege")
public class AdminPrivilegeController extends BaseController {
  @Autowired
  private PrivilegeService privilegeService;

  //添加权限
  @ApiOperation("添加权限")
  @PostMapping("add")
  public CommonResponse addPrivilege(@RequestBody Privilege privilege) {
    privilege.saveToDb(privilegeService);
    return success();
  }
  //权限详情
  @ApiOperation("获取权限详情")
  @GetMapping("detail")
  public  CommonResponse getPrivilege(@RequestParam(value = "privilegeId") String privilegeId){
    Privilege privilege=privilegeService.getPrivilege(privilegeId);
    return success(privilege);
  }
  @ApiOperation("权限列表")
  @GetMapping("list")
  public CommonResponse listPrivilege(){
    List<Privilege> infoList=privilegeService.findAll();
    return success(infoList);
  }

  @ApiOperation("删除权限")
  @PostMapping("del/{privilegeId}")
  public CommonResponse delPrivilege(@PathVariable(value = "privilegeId") String privilegeId){
    privilegeService.delPrivilege(privilegeId);
    return success();
  }


}
