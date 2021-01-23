package com.kankan.api.user;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.constant.ErrorCode;
import com.kankan.dao.entity.KankanApply;
import com.kankan.module.User;
import com.kankan.param.KankanCompanyApply;
import com.kankan.param.mail.SendSmsCode;
import com.kankan.param.mail.VerifySmsCode;
import com.kankan.param.user.LoginParam;
import com.kankan.param.user.RegisterInfo;
import com.kankan.param.user.UserBaseInfo;
import com.kankan.service.CacheService;
import com.kankan.service.MailSender;
import com.kankan.service.TokenService;
import com.kankan.service.UserService;
import com.kankan.vo.UserDetailVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.kankan.constant.CommonResponse.error;
import static com.kankan.constant.ErrorCode.*;

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

  @ApiOperation("用户详情")
  @GetMapping("detail")
  public CommonResponse detail(@RequestHeader("userToken") String userToken) {
    User user = User.toUser(tokenService, userToken);
    if (user.isEmpty()) {
      return error(USER_TOKEN_CHECK_ERROR);
    }

    Object applyInfo = getApplyInfo(user.getUserId());
    UserDetailVo userDetail = new UserDetailVo(user, applyInfo);
    return CommonResponse.success(userDetail);
  }


  private Object getApplyInfo(Long userId) {
    Query query = Query.query(Criteria.where("_id").is(userId));
    Object applyInfo = mongoTemplate.findOne(query, Object.class, "kankan_apply");
    return applyInfo;
  }
}
