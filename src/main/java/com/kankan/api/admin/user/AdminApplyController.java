package com.kankan.api.admin.user;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.dao.entity.KankanApply;
import com.kankan.dao.mapper.KankanUserRoleMapper;
import com.kankan.module.KankanUser;
import com.kankan.module.UserMessage;
import com.kankan.module.privilege.UserPrivilege;
import com.kankan.param.ApplyUpdateParam;
import com.kankan.param.KankanCompanyApply;
import com.kankan.service.KankanUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Validated
@Api(tags = "管理后台-看看号申请-管理")
@RestController
@RequestMapping("admin/kankan/apply")
public class AdminApplyController extends BaseController {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private KankanUserService userService;

  @Resource
  private KankanUserRoleMapper kankanUserRoleMapper;

  @ApiOperation("申请列表 ,1 个人  2 公司")
  @GetMapping("list")
  public CommonResponse list(@RequestParam(value = "applyType") Integer applyType) {
    List<Object> infoList = mongoTemplate.findAll(Object.class, "kankan_apply");
    if (applyType == 1) {
      return success(infoList.stream().filter(o -> o instanceof KankanApply).collect(Collectors.toList()));
    } else {
      return success(infoList.stream().filter(o -> o instanceof KankanCompanyApply).collect(Collectors.toList()));
    }
  }

  @ApiOperation("更新审核状态")
  @PostMapping("update")
  public CommonResponse update(@RequestBody ApplyUpdateParam updateParam) {
    String userId = updateParam.getUserId();
    Integer applyStatus = updateParam.getApplyStatus();
    Query query = Query.query(Criteria.where("_id").is(userId));
    Update update = Update.update("applyStatus", applyStatus);
    mongoTemplate.updateMulti(query, update, "kankan_apply");
    Object applyInfo = getApplyInfo(mongoTemplate, userId);

    String userType;
    if (applyInfo instanceof KankanApply) {
      userType = ((KankanApply) applyInfo).getUserType();
    } else {
      userType = ((KankanCompanyApply) applyInfo).getUserType();
    }
    userType = ObjectUtils.defaultIfNull(userType, "0");
    if (updateParam.getApplyStatus() == 2) {
      UserPrivilege userPrivilege = UserPrivilege.builder().privilege(getPrivilege(applyInfo)).userId(userId).build();
      mongoTemplate.save(userPrivilege);
      //数据写入到kankan用户表
      KankanUser user = KankanUser.builder()
        .userId(updateParam.getUserId())
        .userName(getUsername(applyInfo))
        .userType(userType)
        .remark(getRemark(applyInfo))
        .build();
      userService.createUser(user);
      //发送站内信
      sendInnerMessage(userId, getUsername(applyInfo), "看看号审核通过");
    }
    sendInnerMessage(userId, getUsername(applyInfo), "看看号审核不通过");
    return success();
  }

  public void sendInnerMessage(String userId, String username, String message) {
    UserMessage userMessage = UserMessage.builder().userId(userId).content(username + "," + message).build();
    userMessage.setCreateTime(Instant.now().toEpochMilli());
    userMessage.setRead(false);
    mongoTemplate.save(userMessage);
  }

}
