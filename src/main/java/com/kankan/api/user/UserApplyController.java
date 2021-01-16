package com.kankan.api.user;


import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.param.CompanyKankanParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户-企业看看号-申请信息")
@RequestMapping("user/company")
@RestController
public class UserApplyController extends BaseController {

  @Autowired
  MongoTemplate mongoTemplate;

  @ApiOperation(value = "企业看看号申请")
  @PostMapping("apply")
  public CommonResponse apply(@RequestBody CompanyKankanParam companyKankanParam){
      companyKankanParam.setId(null);
      companyKankanParam.setKankanStatus(1);
      mongoTemplate.insert(companyKankanParam);
      return success();
  }
}
