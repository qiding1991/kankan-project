package com.kankan.api.admin.user;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.dao.entity.KankanUserRole;
import com.kankan.dao.entity.UserEntity;
import com.kankan.dao.mapper.KankanUserMapper;
import com.kankan.dao.mapper.KankanUserRoleMapper;
import com.kankan.module.KankanUser;
import com.kankan.module.User;
import com.kankan.module.privilege.UserRole;
import com.kankan.param.KankanUserParam;
import com.kankan.param.UserRoleParam;
import com.kankan.service.KankanUserService;
import com.kankan.service.UserRoleService;
import com.kankan.service.UserService;
import com.kankan.util.GsonUtil;
import com.kankan.util.Md5Util;
import com.kankan.vo.UserDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.K;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.kankan.constant.ErrorCode.EMAIL_NOT_AVAILABLE_ERROR;
import static com.kankan.constant.ErrorCode.PARAM_CHECK_ERROR;

@Validated
@Api(tags = "管理后台-用户管理-管理")
@RestController
@RequestMapping("admin/kankan/user")
public class AdminKankanUserController extends BaseController {

  private KankanUserService kankanUserService;

  private UserService userService;

  @Resource
  private KankanUserRoleMapper userRoleMapper;

  @Autowired
  private UserRoleService userRoleService;

  public AdminKankanUserController(KankanUserService kankanUserService, UserService userService) {
    this.kankanUserService = kankanUserService;
    this.userService = userService;
  }


  @ApiOperation("获取作者列表")
  @PostMapping("list")
  public CommonResponse list() {
    KankanUser kankanUser = KankanUser.builder().build();
    List<KankanUser> userList = kankanUser.list(kankanUserService);
    return success(userList);
  }

  @ApiOperation("创建一个用户")
  @PostMapping("create")
  public CommonResponse create(@RequestBody UserRoleParam userRole) {
    User user = User.builder()
      .userEmail(userRole.getUserEmail())
      .username(userRole.getUsername())
      .userPhoto(userRole.getPhoto())
      .password(Md5Util.md5Hex(userRole.getPassword()))
      .build();

    Boolean emailExists = user.emailNotExists(userService);
    if (!emailExists) {
      return CommonResponse.error(EMAIL_NOT_AVAILABLE_ERROR, "邮箱重复");
    }
    //创建用户
    user = user.create(userService);
    // 创建关联表
    KankanUserRole kankanUserRole = new KankanUserRole();
    kankanUserRole.setRoleId(userRole.getRoleId());
    kankanUserRole.setUserId(user.getUserId());
    userRoleMapper.insert(kankanUserRole);
    UserRole userRoleInfo = userRoleService.findUserRole(kankanUserRole.getRoleId());
    UserDetailVo userDetail = new UserDetailVo(user, userRoleInfo);
    return success(userDetail);
  }

  @ApiOperation("用户列表")
  @GetMapping("find")
  public CommonResponse findUser(
    @RequestParam(value = "username", required = false) String username,
    @RequestParam(value = "roleId", required = false) String roleId,
    @RequestParam(value = "userEmail", required = false) String userEmail) {


    User user = User.builder().userEmail(userEmail).username(username).build();
    List<User> userList = userService.findUser(user);
    Map<Long, User> userMap = new HashMap<>(userList.size());
    userList.stream().forEach(userInfo -> userMap.put(userInfo.getUserId(), userInfo));
    String userIdList = GsonUtil.toGson(userMap.keySet());


    userIdList = userIdList.replace("[", "").replace("]", "");
    List<KankanUserRole> userRoleList = userRoleMapper.batchFindUser(userIdList);

    Map<Long, String> roleIdMap = new HashMap<>();
    userRoleList.stream().forEach(userRole -> roleIdMap.put(userRole.getUserId(), userRole.getRoleId()));

    List<String> roleIdList = userRoleList.stream().map(KankanUserRole::getRoleId).collect(Collectors.toList());
    List<UserRole> userRoles = userRoleService.findUserRole(roleIdList);

    Map<String, UserRole> roleMap = new HashMap<>();
    userRoles.stream().forEach(userRole -> roleMap.put(userRole.getRoleId(), userRole));

    List<UserDetailVo> userDetailVoList = new ArrayList<>();

    for (Map.Entry<Long, User> userEntry : userMap.entrySet()) {
      Long userId = userEntry.getKey();
      User userInfo = userEntry.getValue();
      if (roleId == null || roleId.equalsIgnoreCase( roleIdMap.get(userId))) {
        UserRole userRole = roleMap.get(roleIdMap.get(userId));
        userDetailVoList.add(new UserDetailVo(userInfo, userRole));
      }
    }
    return success(userDetailVoList);
  }

  @ApiOperation("查看用户信息")
  @GetMapping("get")
  public CommonResponse getUser(@RequestParam(value = "userEmail", required = false) String userEmail) {
    //获取用户信息
    UserEntity user = userService.findUserByEmail(userEmail);
    //获取权限信息
    KankanUserRole kankanUserRole = userRoleMapper.findByUserId(user.getId());
    //获取角色相关
    UserRole userRole = userRoleService.findUserRole(kankanUserRole.getRoleId());
    UserDetailVo userDetail = new UserDetailVo(user, userRole);
    return success(userDetail);
  }
}
