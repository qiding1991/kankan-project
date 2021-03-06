package com.kankan.api.admin.work;

import javax.validation.Valid;

import com.google.common.collect.ImmutableMap;
import com.kankan.param.AuditParam;
import com.kankan.service.*;
import com.kankan.vo.KankanWorkVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.module.KankanWork;
import com.kankan.param.work.WorkAddInfo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-03
 */
@Validated
@Api(tags = "管理后台-专刊-发布作品")
@RestController
@RequestMapping("admin/work")
public class AdminWorkController extends BaseController {

  private ResourceService resourceService;
  private KankanWorkService workService;
  private HotPointService hotPointService;
  private HeaderLineService headerLineService;
  private KankanUserService kankanUserService;

  public AdminWorkController(ResourceService resourceService, KankanWorkService workService, HotPointService hotPointService, HeaderLineService headerLineService, KankanUserService kankanUserService) {
    this.resourceService = resourceService;
    this.workService = workService;
    this.hotPointService = hotPointService;
    this.headerLineService = headerLineService;
    this.kankanUserService = kankanUserService;
  }


  @ApiOperation("发布作品")
  @PostMapping("create")
  public CommonResponse createWork(@Valid @RequestBody WorkAddInfo workAddInfo) {
    Integer whiteStatus= kankanUserService.whiteStatus(workAddInfo.getUserId());
    KankanWork work = workAddInfo.toWork(resourceService);
    work.setAuditStatus(whiteStatus);
    work.addWork(workService);
    //更新审核状态
    workService.auditWork(work.getId(),whiteStatus);
    //修改返回值
    Map<String,Object> resultMap= ImmutableMap.of("id",work.getId(),"aditStatus",whiteStatus);
    return success(resultMap);
  }

  @ApiOperation("作品列表")
  @GetMapping("list")
  public CommonResponse listWork(@RequestParam(value = "userId",required = false) Long userId) {
    KankanWork work = KankanWork.builder().build();
    List<KankanWork> infoList;
    if(userId==null){
      infoList = work.findAllWork(workService);
    }else {
      work.setUserId(userId);
      infoList=work.findMyWork(workService);
    }
    List<KankanWorkVo> voList = infoList.stream().map(kankanWork -> toVo(kankanWork,hotPointService,headerLineService, resourceService)).collect(Collectors.toList());
    return success(voList);
  }





  @ApiOperation("审核作品")
  @PostMapping("audit")
  public CommonResponse audit(@RequestBody AuditParam auditParam) {
    workService.auditWork(auditParam.getId(), auditParam.getAuditStatus());
    return success();
  }

  private KankanWorkVo toVo(KankanWork kankanWork, HotPointService hotPointService, HeaderLineService headerLineService, ResourceService resourceService) {
    KankanWorkVo kankanWorkVo = new KankanWorkVo(kankanWork);
    kankanWorkVo.addResource(resourceService);

    kankanWorkVo.addHotStatus(hotPointService);

    kankanWorkVo.addHeadLine(headerLineService);

    return kankanWorkVo;
  }
}
