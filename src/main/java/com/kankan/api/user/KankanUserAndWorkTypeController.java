package com.kankan.api.user;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.dao.entity.KankanTypeEntity;
import com.kankan.module.KankanWorkTypeEntity;
import com.kankan.service.KankanTypeService;
import com.kankan.service.KankanWorkTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@Validated
@Api(tags = "分类相关接口，用户分类，作品分类")
@RequestMapping("user")
@RestController
public class KankanUserAndWorkTypeController extends BaseController {

  @Autowired
  private KankanTypeService kankanTypeService;

  @Autowired
  private KankanWorkTypeService workTypeService;


  @ApiOperation("用户分类列表")
  @GetMapping("kankanType/List")
  public CommonResponse userTypeList() {
    List<KankanTypeEntity> infoList = kankanTypeService.findAll();
    return success(infoList);
  }

  @ApiOperation("作品分类列表")
  @GetMapping("workType/list")
  public CommonResponse workTypeList() {
    List<KankanWorkTypeEntity> infoList = workTypeService.findAll();
    return success(infoList);
  }


}
