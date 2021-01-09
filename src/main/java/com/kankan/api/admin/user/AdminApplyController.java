package com.kankan.api.admin.user;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.dao.entity.KankanApply;
import com.kankan.dao.mapper.KankanAdMapper;
import com.kankan.dao.mapper.KankanApplyMapper;
import com.kankan.module.KankanUser;
import com.kankan.service.KankanUserService;
import com.kankan.vo.KankanadVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Validated
@Api(tags = "管理后台-看看号申请-管理")
@RestController
@RequestMapping("admin/kankan/apply")
public class AdminApplyController extends BaseController {

  @Resource
  private KankanApplyMapper applyMapper;

  @Autowired
  private KankanUserService userService;

  @ApiOperation("申请列表")
  @GetMapping("list")
  public CommonResponse list() {
    List<KankanApply> infoList = applyMapper.findAll();
    return success(infoList);
  }

  @ApiOperation("更新审核状态")
  @PostMapping("update/{userId}/{status}")
  public CommonResponse update(@PathVariable(value = "userId") Long userId, @PathVariable(value = "status") Integer status) {
    applyMapper.updateStatus(userId, status);
    if (status == 2) {
      KankanApply apply = applyMapper.findByUserId(userId);
      KankanUser kankanUser = KankanUser.builder().userId(userId)
        .userName(apply.getUsername())
        .picture(apply.getPhoto())
        .userType(1L)
        .remark(apply.getRemark())
        .build();
      kankanUser.save(userService);
    }
    return success();
  }

}
