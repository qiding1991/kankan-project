package com.kankan.api.admin.user;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@Api(tags = "管理后台-看看号申请-管理")
@RestController
@RequestMapping("admin/kankan/apply")
public class AdminApplyController extends BaseController {

  @Autowired
  private MongoTemplate mongoTemplate;

  @ApiOperation("申请列表")
  @GetMapping("list")
  public CommonResponse list() {
    List<Object> infoList= mongoTemplate.findAll(Object.class,"kankan_apply");
    return success(infoList);
  }

  @ApiOperation("更新审核状态")
  @PostMapping("update/{userId}/{applyStatus}")
  public CommonResponse update(@PathVariable(value = "userId") Long userId, @PathVariable(value = "applyStatus") Integer applyStatus) {
    Query query=Query.query(Criteria.where("userId").is(userId));
    Update update=Update.update("applyStatus",applyStatus);
    mongoTemplate.updateMulti(query,update,"kankan_apply");
    return success();
  }

}
