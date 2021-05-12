package com.kankan.api.admin.user;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.dao.entity.KankanApply;
import com.kankan.dao.entity.UserEntity;
import com.kankan.dao.mapper.KankanUserRoleMapper;
import com.kankan.module.KankanUser;
import com.kankan.module.User;
import com.kankan.module.privilege.UserPrivilege;
import com.kankan.param.JoinInWhiteParam;
import com.kankan.param.KankanCompanyApply;
import com.kankan.param.UserRoleParam;
import com.kankan.service.KankanUserService;
import com.kankan.service.UserService;
import com.kankan.util.Md5Util;
import com.kankan.vo.UserDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

import static com.kankan.constant.CommonResponse.error;
import static com.kankan.constant.ErrorCode.EMAIL_NOT_AVAILABLE_ERROR;
import static com.kankan.constant.ErrorCode.USER_TOKEN_CHECK_ERROR;

@Slf4j
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


  @ApiOperation("获取作者列表  recommendStatus  (1不推荐 2推荐) whiteStatus(1 不白名单 2 白名单)")
  @PostMapping("list")
  public CommonResponse list() {
    KankanUser kankanUser = KankanUser.builder().build();
    List<KankanUser> userList = kankanUser.list(kankanUserService);
    return success(userList);
  }


  @ApiOperation("更新白名单状态  1 不是白名单 2 是白名单")
  @PostMapping("updateWhiteStatus")
  public CommonResponse updateWhiteStatus(@RequestBody JoinInWhiteParam joinInWhiteParam) {
    kankanUserService.updateWhiteStatus(joinInWhiteParam.getUserId(), joinInWhiteParam.getWhiteStatus());
    return success(joinInWhiteParam.getWhiteStatus());
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
//    //保存到数据库
//    KankanApply kankanApply = KankanApply.builder()
//      .userId(user.getUserId())
//      .applyStatus(2)
//      .email(userRole.getUserEmail())
//      .photo(userRole.getUserPhoto())
//      .username(userRole.getUsername())
//      .privilege(userRole.getPrivilege())
//      .build();
//    mongoTemplate.save(kankanApply);

    UserPrivilege userPrivilege = UserPrivilege.builder().userId(user.getUserId()).privilege(userRole.getPrivilege()).build();
    mongoTemplate.save(userPrivilege);
    UserDetailVo userDetail = new UserDetailVo(user, userPrivilege);
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


//
//
//    //保存到数据库
//    KankanApply kankanApply = KankanApply.builder()
//      .userId(user.getUserId())
//      .applyStatus(2)
//      .email(userRole.getUserEmail())
//      .photo(userRole.getUserPhoto())
//      .username(userRole.getUsername())
//      .privilege(userRole.getPrivilege())
//      .build();
//    mongoTemplate.save(kankanApply);
//
//
//


    UserPrivilege userPrivilege = UserPrivilege.builder().userId(user.getUserId()).privilege(userRole.getPrivilege()).build();
    mongoTemplate.save(userPrivilege);
    UserDetailVo userDetail = new UserDetailVo(user, userPrivilege);
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
    List<UserDetailVo> userDetailVoList = findApplyInfo(userMap, privilege);
    return success(userDetailVoList);
  }

  @ApiOperation("查看用户信息")
  @GetMapping("get")
  public CommonResponse getUser(@RequestParam(value = "userEmail", required = false) String userEmail) {
    //获取用户信息
    UserEntity user = userService.findUserByEmail(userEmail);
    Object applyInfo = getApplyInfo(mongoTemplate, user.getId());
    UserDetailVo userDetail;
    if (applyInfo == null) {
      userDetail = new UserDetailVo(user);
    } else if (applyInfo instanceof KankanCompanyApply) {
      userDetail = new UserDetailVo(user, (KankanCompanyApply) applyInfo);
    } else {
      userDetail = new UserDetailVo(user, (KankanApply) applyInfo);
    }
    return success(userDetail);
  }


  public List<UserDetailVo> findApplyInfo(Map<Long, User> userMap, String privilege) {

    List<UserDetailVo> userDetailVoList = new ArrayList<>();
    Map<Long, UserDetailVo> userDetailVoMap = new HashMap<>();

    userMap.values().forEach((user) -> {
      userDetailVoMap.put(user.getUserId(), new UserDetailVo(user));
    });

    Query query = new Query(Criteria.where("_id").in(userMap.keySet()));
    if (privilege != null) {
      //TODO 添加权限选择
//          query.addCriteria(Criteria.where("privilegeList").)
    }
    List<KankanApply> userApplyList = mongoTemplate.find(query, KankanApply.class);
    List<KankanCompanyApply> companyApplyList = mongoTemplate.find(query, KankanCompanyApply.class);
    List<UserPrivilege> userPrivilegeList = mongoTemplate.find(query, UserPrivilege.class);
    userPrivilegeList.forEach(userPrivilege -> {
      Long userId = userPrivilege.getUserId();
      userDetailVoMap.put(userId, new UserDetailVo(userMap.get(userId), userPrivilege));
    });

    userApplyList.forEach(kankanApply -> {
      UserDetailVo userDetailVo = new UserDetailVo(userMap.get(kankanApply.getUserId()), kankanApply);
      userDetailVoMap.put(kankanApply.getUserId(), userDetailVo);
    });
    companyApplyList.forEach(kankanApply -> {
      UserDetailVo userDetailVo = new UserDetailVo(userMap.get(kankanApply.getUserId()), kankanApply);
      userDetailVoMap.put(kankanApply.getUserId(), userDetailVo);
    });
    userDetailVoList.addAll(userDetailVoMap.values());
    return userDetailVoList;
  }

  @ApiOperation("用户详情,根据userid获取用户详情")
  @GetMapping("detail")
  public CommonResponse detail(@RequestParam("userId") Long userId) {
    User user = userService.getUser(userId);
    UserDetailVo userDetail = new UserDetailVo(user);
    Object applyInfo = getApplyInfo(mongoTemplate, user.getUserId());
    log.info("applyInfo={}", applyInfo);
    if (applyInfo != null && applyInfo instanceof KankanApply) {
      userDetail = new UserDetailVo(user, (KankanApply) applyInfo);
    }
    if (applyInfo != null && applyInfo instanceof KankanCompanyApply) {
      userDetail = new UserDetailVo(user, (KankanCompanyApply) applyInfo);
    }
    UserPrivilege userPrivilege = getUserPrivilege(mongoTemplate, user.getUserId());
    log.info("userDetail={}", userDetail);
    if (userPrivilege != null && !CollectionUtils.isEmpty(userPrivilege.getPrivilege())) {
      log.info("userPrivilege={}", userPrivilege);
      if (StringUtils.isEmpty(userDetail.getApplyStatus())) {
        userDetail.setPrivilege(userPrivilege.getPrivilege());
      } else {
        userDetail = new UserDetailVo(user, userPrivilege);
      }
    }
    return CommonResponse.success(userDetail);
  }
}
