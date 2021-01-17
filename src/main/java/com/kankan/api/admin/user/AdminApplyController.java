package com.kankan.api.admin.user;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.dao.entity.KankanApply;
import com.kankan.dao.entity.KankanUserRole;
import com.kankan.dao.mapper.KankanUserRoleMapper;
import com.kankan.param.ApplyUpdateParam;
import com.kankan.param.CompanyKankanParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Validated
@Api(tags = "管理后台-看看号申请-管理")
@RestController
@RequestMapping("admin/kankan/apply")
public class AdminApplyController extends BaseController {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Resource
  private KankanUserRoleMapper kankanUserRoleMapper;

  @ApiOperation("申请列表 ,1 个人  2 公司")
  @GetMapping("list")
  public CommonResponse list(@RequestParam(value = "applyType") Integer applyType) {
    List<Object> infoList = mongoTemplate.findAll(Object.class, "kankan_apply");
    if (applyType == 1) {
      return success(infoList.stream().filter(o -> o instanceof KankanApply).collect(Collectors.toList()));
    } else {
      return success(infoList.stream().filter(o -> o instanceof CompanyKankanParam).collect(Collectors.toList()));
    }
  }

  @ApiOperation("更新审核状态")
  @PostMapping("update")
  public CommonResponse update(@RequestBody ApplyUpdateParam updateParam) {
    Long userId = updateParam.getUserId();
    Integer applyStatus = updateParam.getApplyStatus();
    String roleId = updateParam.getRoleId();

    Query query = Query.query(Criteria.where("userId").is(userId));
    Update update = Update.update("applyStatus", applyStatus);
    mongoTemplate.updateMulti(query, update, "kankan_apply");

    KankanUserRole kankanUserRole = kankanUserRoleMapper.findByUserId(userId);
    if (kankanUserRole != null) {
      kankanUserRoleMapper.updateRole(userId, roleId);
    } else {
      kankanUserRole = new KankanUserRole();
      kankanUserRole.setUserId(userId);
      kankanUserRole.setRoleId(roleId);
      kankanUserRole.setCreateTime(System.currentTimeMillis());
      kankanUserRole.setUpdateTime(System.currentTimeMillis());
      kankanUserRole.setStatus(1);
      kankanUserRoleMapper.insert(kankanUserRole);
    }

    return success();
  }

}
