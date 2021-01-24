package com.kankan.api.admin.kankan;

import com.kankan.param.UpdateUserTypeParam;
import com.kankan.service.KankanUserService;
import org.springframework.web.bind.annotation.*;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.module.KankanType;
import com.kankan.param.kankan.KankanTypeParam;
import com.kankan.param.kankan.KankanTypeUpdataParam;
import com.kankan.service.KankanTypeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
@Api(tags = "管理后台-看看号-分类")
@RequestMapping("admin/kankan")
@RestController
public class AdminKankanController extends BaseController {

  private KankanTypeService typeService;

  private KankanUserService kankanUserService;

  public AdminKankanController(KankanTypeService typeService, KankanUserService kankanUserService) {
    this.typeService = typeService;
    this.kankanUserService = kankanUserService;
  }

  @ApiOperation("添加-看看分类")
  @PostMapping("addType")
  public CommonResponse addType(@RequestBody KankanTypeParam param) {
    KankanType kankanType = param.toType();
    kankanType.addType(typeService);
    return success(kankanType.getId());
  }

  @ApiOperation("更新-分类")
  @PostMapping("updateType")
  public CommonResponse updateType(@RequestBody KankanTypeUpdataParam param) {
    KankanType kankanType = param.toType();
    kankanType.updateType(typeService);
    return success();
  }

  @ApiOperation("分类-列表")
  @PostMapping("listType")
  public CommonResponse listType() {
    KankanType kankanType = KankanType.builder().build();
    List<KankanType> kankanTypeList = kankanType.findAll(typeService);
    return success(kankanTypeList);
  }

  @ApiOperation("分类-删除")
  @PostMapping("delType/{id}")
  public CommonResponse delType(@PathVariable(value = "id") Long id) {
    KankanType kankanType = KankanType.builder().id(id).build();
    kankanType.remove(typeService);
    return success();
  }

  @ApiOperation("更新用户分类")
  @PostMapping("updateUserType")
  public CommonResponse updateUserType(@RequestBody UpdateUserTypeParam userTypeParam) {
    kankanUserService.updateUserType(userTypeParam.getUserId(), userTypeParam.getUserType());
    return success();
  }


}
