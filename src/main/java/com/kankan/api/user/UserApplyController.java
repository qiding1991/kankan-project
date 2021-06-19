package com.kankan.api.user;


import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.dao.entity.KankanApply;
import com.kankan.param.KankanCompanyApply;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import static com.kankan.constant.ErrorCode.*;

@Api(tags = "用户-企业看看号-申请信息")
@RequestMapping("user")
@RestController
public class UserApplyController extends BaseController {

  @Autowired
  MongoTemplate mongoTemplate;

  @ApiOperation(value = "企业看看号申请")
  @PostMapping("/company/apply")
  public CommonResponse apply(@RequestBody KankanCompanyApply kankanCompanyApply) {
    Object applyInfo = getApplyInfo(kankanCompanyApply.getUserId());
    if (applyInfo != null && applyInfo instanceof KankanApply) {
      return CommonResponse.error(USER_APPLY_REPEATED_PERSON);
    }
    if (applyInfo != null) {
      KankanCompanyApply recode = (KankanCompanyApply) applyInfo;
      if (recode != null || recode.getApplyStatus() == 2) {
        return CommonResponse.error(USER_APPLY_FORBIDDEN);
      }
    }
    kankanCompanyApply.setApplyStatus(1);
    mongoTemplate.save(kankanCompanyApply);
    return success();
  }


  @ApiOperation("申请成为看看")
  @PostMapping("apply")
  public CommonResponse apply(@RequestBody KankanApply kankanApply) {
    Object applyInfo = getApplyInfo(kankanApply.getUserId());
    if (applyInfo != null && applyInfo instanceof KankanCompanyApply) {
      return CommonResponse.error(USER_APPLY_REPEATED_COMPANY);
    }
    if (applyInfo != null) {
      KankanApply recode = (KankanApply) applyInfo;
      if (recode != null || recode.getApplyStatus() == 2) {
        return CommonResponse.error(USER_APPLY_REPEATED);
      }
    }
    kankanApply.setApplyStatus(1);
    mongoTemplate.save(kankanApply);
    return success();
  }

  @ApiOperation("更新申请信息")
  @PostMapping("apply/update")
  public CommonResponse update(@RequestBody KankanApply kankanApply) {
    kankanApply.setApplyStatus(1);
    mongoTemplate.save(kankanApply);
    return success();
  }


  @ApiOperation("获取申请信息")
  @GetMapping("applyDetail")
  public CommonResponse applyDetail(@RequestParam(value = "userId") String userId) {
    Object applyInfo = getApplyInfo(userId);
    return success(applyInfo);
  }


  private Object getApplyInfo(String userId) {
    Query query = Query.query(Criteria.where("userId").is(userId));

    KankanCompanyApply applyInfo2 = mongoTemplate.findOne(query, KankanCompanyApply.class, "kankan_apply");
    KankanApply applyInfo = mongoTemplate.findOne(query, KankanApply.class, "kankan_apply");
    if(applyInfo2!=null&& StringUtils.isNotBlank(applyInfo2.getAdminName())){
      return  applyInfo2;
    }
    return applyInfo;
  }


}
