package com.kankan.api.admin.user;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.dao.entity.KankanApply;
import com.kankan.dao.entity.UserEntity;
import com.kankan.dao.mapper.KankanUserRoleMapper;
import com.kankan.module.KankanUser;
import com.kankan.module.User;
import com.kankan.param.KankanCompanyApply;
import com.kankan.param.UserRoleParam;
import com.kankan.service.KankanUserService;
import com.kankan.service.UserService;
import com.kankan.util.Md5Util;
import com.kankan.vo.UserDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

import static com.kankan.constant.ErrorCode.EMAIL_NOT_AVAILABLE_ERROR;

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
  MongoTemplate mongoTemplate;

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
      .userPhoto(userRole.getUserPhoto())
      .password(Md5Util.md5Hex(userRole.getPassword()))
      .build();
    Boolean emailExists = user.emailNotExists(userService);
    if (!emailExists) {
      return CommonResponse.error(EMAIL_NOT_AVAILABLE_ERROR, "邮箱重复");
    }
    //创建用户
    user = user.create(userService);
    //保存到数据库
    KankanApply kankanApply = KankanApply.builder()
      .userId(user.getUserId())
      .applyStatus(2)
      .email(userRole.getUserEmail())
      .photo(userRole.getUserPhoto())
      .username(userRole.getUsername())
      .privilegeList(userRole.getPrivilege())
      .build();
    mongoTemplate.save(kankanApply);
    UserDetailVo userDetail = new UserDetailVo(user, kankanApply);
    return success(userDetail);
  }


  @ApiOperation("更新用户")
  @PostMapping("update/{userId}")
  public CommonResponse update(@PathVariable(value = "userId") Long userId, @RequestBody UserRoleParam userRole) {
    User user = User.builder()
      .userEmail(userRole.getUserEmail())
      .username(userRole.getUsername())
      .userPhoto(userRole.getUserPhoto())
      .password(Md5Util.md5Hex(userRole.getPassword()))
      .userId(userId)
      .build();

    if (userRole.getUserEmail() != null) {
      UserEntity userRecord = userService.findUserByEmail(userRole.getUserEmail());
      if (!userRecord.getId().equals(userId)) {
        Boolean emailExists = user.emailNotExists(userService);
        if (!emailExists) {
          return CommonResponse.error(EMAIL_NOT_AVAILABLE_ERROR, "邮箱重复");
        }
      }
    }
    //创建用户
    user.updateUser(userService);


    //保存到数据库
    KankanApply kankanApply = KankanApply.builder()
      .userId(user.getUserId())
      .applyStatus(2)
      .email(userRole.getUserEmail())
      .photo(userRole.getUserPhoto())
      .username(userRole.getUsername())
      .privilegeList(userRole.getPrivilege())
      .build();
    mongoTemplate.save(kankanApply);

    UserDetailVo userDetail = new UserDetailVo(user, kankanApply);
    return success(userDetail);
  }


  @ApiOperation("用户列表")
  @GetMapping("find")
  public CommonResponse findUser(
    @RequestParam(value = "username", required = false) String username,
    @RequestParam(value = "roleId", required = false) String roleId,
    @RequestParam(value = "privilege", required = false) String privilege,
    @RequestParam(value = "userEmail", required = false) String userEmail) {
    User user = User.builder().userEmail(userEmail).username(username).build();
    List<User> userList = userService.findUser(user);
    Map<Long, User> userMap = new HashMap<>(userList.size());
    userList.stream().forEach(userInfo -> userMap.put(userInfo.getUserId(), userInfo));

    Map<Long, Object> applyMap = findApplyInfo(userMap.keySet(), privilege);

    List<UserDetailVo> userDetailVoList = new ArrayList<>();

    for (Map.Entry<Long, User> userEntry : userMap.entrySet()) {
      Long userId = userEntry.getKey();
      User userInfo = userEntry.getValue();
      Object applyInfo = applyMap.get(userId);
      userDetailVoList.add(new UserDetailVo(userInfo, applyInfo));
    }
    return success(userDetailVoList);
  }

  @ApiOperation("查看用户信息")
  @GetMapping("get")
  public CommonResponse getUser(@RequestParam(value = "userEmail", required = false) String userEmail) {
    //获取用户信息
    UserEntity user = userService.findUserByEmail(userEmail);
    Object applyInfo=getApplyInfo(mongoTemplate,user.getId());
    UserDetailVo userDetail = new UserDetailVo(user, applyInfo);
    return success(userDetail);
  }


  public Map<Long, Object> findApplyInfo(Set<Long> userId, String privilege) {
    Query query = new Query(Criteria.where("_id").in(userId));
    if (privilege != null) {
      //TODO 添加权限选择
//          query.addCriteria(Criteria.where("privilegeList").)
    }
    List<KankanApply> userApplyList = mongoTemplate.find(query, KankanApply.class);
    List<KankanCompanyApply> companyApplyList = mongoTemplate.find(query, KankanCompanyApply.class);

    Map<Long, Object> userPrivilege = new HashMap<>();

    userApplyList.forEach(applyInfo -> userPrivilege.put(applyInfo.getUserId(), applyInfo));
    companyApplyList.forEach(applyInfo -> userPrivilege.put(applyInfo.getUserId(), applyInfo));
    return userPrivilege;
  }


}
