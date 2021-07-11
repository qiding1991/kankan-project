package com.kankan.api.user;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.constant.ErrorCode;
import com.kankan.dao.entity.KankanApply;
import com.kankan.dao.entity.UserEntity;
import com.kankan.module.User;
import com.kankan.module.User.ThreePartLogin;
import com.kankan.module.privilege.UserPrivilege;
import com.kankan.param.KankanCompanyApply;
import com.kankan.param.mail.SendSmsCode;
import com.kankan.param.mail.VerifySmsCode;
import com.kankan.param.user.BindThreeAccount;
import com.kankan.param.user.LoginParam;
import com.kankan.param.user.RegisterInfo;
import com.kankan.param.user.ThreePartLoginParam;
import com.kankan.param.user.UserBaseInfo;
import com.kankan.service.CacheService;
import com.kankan.service.MailSender;
import com.kankan.service.TokenService;
import com.kankan.service.UserService;
import com.kankan.vo.UserDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.Objects;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.kankan.constant.CommonResponse.error;
import static com.kankan.constant.ErrorCode.*;

@Log4j2
@Validated
@Api(tags = "用户接口")
@RequestMapping("user")
@RestController
public class UserController extends BaseController {

  @Autowired
  private MailSender mailSender;
  @Autowired
  private UserService userService;
  @Autowired
  private CacheService cacheService;
  @Autowired
  private TokenService tokenService;

  @Autowired
  private MongoTemplate mongoTemplate;


  @ApiOperation("发送验证码")
  @PostMapping("sendSmsCode")
  public CommonResponse sendSmsCode(@Valid @RequestBody SendSmsCode sendSmsCode) {
    User user = sendSmsCode.toUser();
    if (user.emailNotExists(userService)) {
      user.sendActiveCode(mailSender, cacheService);
    } else {
      return error(EMAIL_NOT_AVAILABLE_ERROR);
    }
    return success();
  }

  @ApiOperation("验证码验证")
  @PostMapping("verifySmsCode")
  public CommonResponse verifySmsCode(@Valid @RequestBody VerifySmsCode verifySmsCode) {
    User user = verifySmsCode.toUser();
    //邮箱已经被别人使用
    if (!user.emailNotExists(userService)) {
      return error(EMAIL_NOT_AVAILABLE_ERROR);
    }

    //check activeCode
    if (!user.checkActiveCode(cacheService, verifySmsCode.getSmsCode())) {
      return CommonResponse.error(ErrorCode.SMS_CODE_ERROR);
    }
    //创建用户
    user = user.create(userService);
    //生成token
    String token = user.generateVerifyToken(tokenService, user);
    return success(token);
  }

  @ApiOperation("注册用户,smscode验证通过才能注册")
  @PostMapping("register")
  public CommonResponse register(@RequestHeader("verifyToken") String verifyToken,
      @Valid @RequestBody RegisterInfo registerInfo) {
    User user = registerInfo.toUser(tokenService, verifyToken);
    if (user.isEmpty()) {
      return error(VERIFY_TOKEN_CHECK_ERROR);
    }
    user = user.register(userService);
    String userToken = user.generateUserToken(tokenService);
    return success(userToken);
  }

  @ApiOperation("用户信息更新")
  @PostMapping("update")
  public CommonResponse update(@RequestHeader("userToken") String userToken,
      @Valid @RequestBody UserBaseInfo userInfo) {
    User user = userInfo.toUser(tokenService, userToken);
    //检查token
    if (user.isEmpty()) {
      return error(USER_TOKEN_CHECK_ERROR);
    }
    //检查更新邮箱
    if (StringUtils.isNotBlank(userInfo.getUserEmail()) && !user.emailNotExists(userService)) {
      return error(EMAIL_NOT_AVAILABLE_ERROR);
    }
    //更新token
    user.refreshToken(userToken, tokenService);
    //更新数据库
    user.saveBaseInfo(userService);
    return success();
  }

  @ApiOperation("邮箱密码登录")
  @PostMapping("login")
  public CommonResponse login(@Valid @RequestBody LoginParam loginParam) {
    User user = loginParam.toUser(userService);
    if (user.isEmpty()) {
      return error(PASSWORD_USE_NAME_ERROR);
    }
    String token = user.generateUserToken(tokenService);
    return success(token);
  }


  @ApiOperation("第三方登录")
  @PostMapping("loginWithThreeAccount")
  public CommonResponse loginWithThreeAccount(@Valid @RequestBody ThreePartLoginParam loginParam) {
    UserEntity userEntity = userService.byThreePartLoginParam(loginParam);
    if (Objects.nonNull(userEntity)) {
      String token = userEntity.toUser().generateUserToken(tokenService);
      return CommonResponse.success(token);
    }
    return error(THREE_NOT_REGISTER);
  }

  @ApiOperation("发送验证码，不校验账号是否被占用; true 账号已经占用，false 账号未设置")
  @PostMapping("sendSmsCodeNotVerify")
  public CommonResponse sendSmsCodeNotVerify(@Valid @RequestBody SendSmsCode sendSmsCode) {
    User user = sendSmsCode.toUser();
    user.sendActiveCode(mailSender, cacheService);
    UserEntity userEntity = ObjectUtils.defaultIfNull(
        userService.findUserByEmail(sendSmsCode.getUserEmail()), new UserEntity());
    Boolean userRegistered = StringUtils.isNotBlank(userEntity.getUsername());
    return success(userRegistered);
  }

  @ApiOperation("绑定第三方账号")
  @PostMapping("bindThreeAccount")
  public CommonResponse bindThreeAccount(@RequestHeader("userToken") String userToken,@RequestBody BindThreeAccount loginParam) {
    User user = User.toUser(tokenService, userToken);
    if (user != null) {
      userService.addThreeAccount(user.getUserId(),
          ThreePartLogin.builder().threePartType(loginParam.getThreePartType())
              .threePartId(loginParam.getThreePartId())
              .build()
      );
      return  success();
    }
    return error(THREE_ACCOUNT_BIND_FAIL);
  }


  @ApiOperation("验证码的登录")
  @PostMapping("loginWithSmsCode")
  public CommonResponse loginWithSmsCode(@RequestBody VerifySmsCode verifySmsCode){
    User user = verifySmsCode.toUser();
    //check activeCode
    if (!user.checkActiveCode(cacheService, verifySmsCode.getSmsCode())) {
      return CommonResponse.error(ErrorCode.SMS_CODE_ERROR);
    }
    UserEntity userEntity = userService.findUserByEmail(user.getUserEmail());
    user = userEntity.toUser();
    String userToken = user.generateUserToken(tokenService);
    return success(userToken);
  }


  @ApiOperation("用户详情")
  @GetMapping("detail")
  public CommonResponse detail(@RequestHeader("userToken") String userToken) {
    User user = User.toUser(tokenService, userToken);
    if (user.isEmpty()) {
      return error(USER_TOKEN_CHECK_ERROR);
    }
    user = userService.getUser(user.getUserId());
    UserDetailVo userDetail = new UserDetailVo(user);
    UserPrivilege userPrivilege = getUserPrivilege(mongoTemplate, user.getUserId());
    log.info("userDetail={}", userDetail);
    if (userPrivilege != null && !CollectionUtils.isEmpty(userPrivilege.getPrivilege())) {
      log.info("userPrivilege={}", userPrivilege);
      userDetail = new UserDetailVo(user, userPrivilege);
    } else {
      Object applyInfo = getApplyInfo(mongoTemplate, user.getUserId());
      log.info("applyInfo={}", applyInfo);
      if (applyInfo != null && applyInfo instanceof KankanApply) {
        userDetail = new UserDetailVo(user, (KankanApply) applyInfo);
      }
      if (applyInfo != null && applyInfo instanceof KankanCompanyApply) {
        userDetail = new UserDetailVo(user, (KankanCompanyApply) applyInfo);
      }
    }
    return CommonResponse.success(userDetail);
  }
}
