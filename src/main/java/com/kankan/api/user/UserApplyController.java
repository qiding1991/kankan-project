package com.kankan.api.user;


import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.dao.entity.KankanApply;
import com.kankan.param.CompanyKankanParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

import static com.kankan.constant.ErrorCode.USER_APPLY_REPEATED;

@Api(tags = "用户-企业看看号-申请信息")
@RequestMapping("user")
@RestController
public class UserApplyController extends BaseController {

  @Autowired
  MongoTemplate mongoTemplate;

  @ApiOperation(value = "企业看看号申请")
  @PostMapping("/company/apply")
  public CommonResponse apply(@RequestBody CompanyKankanParam companyKankanParam) {
    if (getApplyInfo(companyKankanParam.getUserId()) != null) {
      return CommonResponse.error(USER_APPLY_REPEATED);
    }
    companyKankanParam.setId(null);
    companyKankanParam.setApplyStatus(1);
    mongoTemplate.insert(companyKankanParam);
    return success();
  }


  @ApiOperation("申请成为看看")
  @PostMapping("apply")
  public CommonResponse apply(@RequestBody KankanApply kankanApply) {
    if (getApplyInfo(kankanApply.getUserId()) != null) {
      return CommonResponse.error(USER_APPLY_REPEATED);
    }
    kankanApply.setApplyStatus(1);
    mongoTemplate.insert(kankanApply);
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
  public CommonResponse applyDetail(@RequestParam(value = "userId") Long userId) {
    Object applyInfo = getApplyInfo(userId);
    return success(applyInfo);
  }


  private Object getApplyInfo(Long userId) {
    Query query = Query.query(Criteria.where("userId").is(userId));
    Object applyInfo = mongoTemplate.findOne(query, Object.class, "kankan_apply");
    return applyInfo;
  }


}
