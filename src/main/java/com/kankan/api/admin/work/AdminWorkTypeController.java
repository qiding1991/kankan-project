package com.kankan.api.admin.work;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.module.KankanWorkTypeEntity;
import com.kankan.param.AddWorkTypeParam;
import com.kankan.param.UpdateWorkTypeParam;
import com.kankan.service.KankanWorkTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@Api(tags = "管理后台-专刊-作品分类管理")
@RestController
@RequestMapping("admin/workType")
public class AdminWorkTypeController  extends BaseController {

  @Autowired
  private KankanWorkTypeService kankanWorkTypeService;


  /**
   * 添加作品分类
   */
  @ApiOperation("添加作品分类")
  @PostMapping("add")
  public CommonResponse addWorkType(@RequestBody AddWorkTypeParam workTypeParam){
    KankanWorkTypeEntity workTypeEntity=new KankanWorkTypeEntity();
    workTypeEntity.setTypeName(workTypeParam.getTypeName());
    kankanWorkTypeService.addKankanWorkType(workTypeEntity);
    return success(workTypeEntity.getId());
  }

  /**
   * 更新作品分类
   */
  @ApiOperation("更新作品分类")
  @PostMapping("update")
  public CommonResponse updateWorkType(@RequestBody UpdateWorkTypeParam updateWorkTypeParam){
    kankanWorkTypeService.updateKankanWorkType(updateWorkTypeParam.getId(), updateWorkTypeParam.getTypeName());
    return success(updateWorkTypeParam.getId());
  }

  /**
   * 作品分类列表
   */
  @ApiOperation("所有的作品分类")
  @PostMapping("list")
  public CommonResponse workTypeList(){
      List<KankanWorkTypeEntity> infoList= kankanWorkTypeService.findAll();
      return success(infoList);
  }

}
