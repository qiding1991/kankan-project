package com.kankan.api.admin.privilege;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.dao.entity.KankanUserRole;
import com.kankan.dao.mapper.KankanUserRoleMapper;
import com.kankan.module.privilege.UserRole;
import com.kankan.service.UserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Validated
@Api(tags = "管理后台-角色管理-权限")
@RestController
@RequestMapping("admin/role")
public class AdminUserRoleController extends BaseController {

  @Autowired
  private UserRoleService userRoleService;

  @Resource
  private KankanUserRoleMapper kankanUserRoleMapper;


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

  @ApiOperation("给用户授权")
  @PostMapping("grantUserRole/{userId}/{roleId}")
  public CommonResponse grantUserRole(
    @PathVariable(value = "userId") Long userId,
    @PathVariable(value = "roleId") String roleId){

    KankanUserRole kankanUserRole= kankanUserRoleMapper.findByUserId(userId);
    if(kankanUserRole!=null){
      kankanUserRoleMapper.updateRole(userId,roleId);
    }else{

      kankanUserRole =new KankanUserRole();
      kankanUserRole.setUserId(userId);
      kankanUserRole.setRoleId(roleId);
      kankanUserRole.setCreateTime(System.currentTimeMillis());
      kankanUserRole.setUpdateTime(System.currentTimeMillis());
      kankanUserRole.setStatus(1);
      kankanUserRoleMapper.insert(kankanUserRole);
    }
    return  success();
  }




}
