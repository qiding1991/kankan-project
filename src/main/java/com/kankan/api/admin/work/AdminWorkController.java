package com.kankan.api.admin.work;

import com.kankan.dao.entity.WorkEntity;
import com.kankan.module.MediaResource;
import com.kankan.module.privilege.UserPrivilege;
import com.kankan.param.work.WorkUpdateInfo;
import javax.validation.Valid;

import com.google.common.collect.ImmutableMap;
import com.kankan.param.AuditParam;
import com.kankan.service.*;
import com.kankan.vo.KankanWorkVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @author <qiding@qiding.com> Created on 2020-12-03
 */
@Validated
@Api(tags = "管理后台-专刊-发布作品")
@RestController
@RequestMapping("admin/work")
public class AdminWorkController extends BaseController {

  @Autowired
  private ResourceService resourceService;
  @Autowired
  private KankanWorkService workService;
  @Autowired
  private HotPointService hotPointService;
  @Autowired
  private HeaderLineService headerLineService;
  @Autowired
  private KankanUserService kankanUserService;
  @Autowired
  private UserPrivilegeService userPrivilegeService;


  @ApiOperation("发布作品")
  @PostMapping("create")
  public CommonResponse createWork(@Valid @RequestBody WorkAddInfo workAddInfo) {
    Integer whiteStatus = kankanUserService.whiteStatus(workAddInfo.getUserId());
    KankanWork work = workAddInfo.toWork(resourceService);
    work.setAuditStatus(whiteStatus);
    work.addWork(workService);
    //更新审核状态
    workService.auditWork(work.getId(), whiteStatus);
    //修改返回值
    Map<String, Object> resultMap = ImmutableMap.of("id", work.getId(), "aditStatus", whiteStatus);
    return success(resultMap);
  }

  @ApiOperation("作品列表")
  @GetMapping("list")
  public CommonResponse listWork(@RequestParam(value = "userId", required = false) String userId) {

    if (StringUtils.isNotBlank(userId)) {
      UserPrivilege userPrivilege = userPrivilegeService.findByUserId(userId);
      if (userPrivilege.getPrivilege().contains("manage_kankan")) {
        userId = null;
      }
    }

    KankanWork work = KankanWork.builder().build();
    List<KankanWork> infoList;
    if (userId == null) {
      infoList = work.findAllWork(workService);
    } else {
      work.setUserId(userId);
      infoList = work.findMyWork(workService);
    }
    List<KankanWorkVo> voList = infoList.stream().map(kankanWork -> toVo(kankanWork, hotPointService, headerLineService, resourceService))
        .collect(Collectors.toList());
    return success(voList);
  }


  @ApiOperation("审核作品")
  @PostMapping("audit")
  public CommonResponse audit(@RequestBody AuditParam auditParam) {
    workService.auditWork(auditParam.getId(), auditParam.getAuditStatus());
    return success();
  }


  @ApiOperation("删除作品")
  @PostMapping("delete/{id}")
  public CommonResponse delete(@PathVariable(value = "id") String id) {
    workService.delete(id);
    return success();
  }


  @ApiOperation("更新作品")
  @PostMapping("updateWork")
  public CommonResponse updateWork(@RequestBody WorkUpdateInfo updateInfo) {
    WorkEntity kankanWork = workService.findById(updateInfo.getId());
    kankanWork.setTitle(updateInfo.getTitle());
    kankanWork.setPicture(updateInfo.getPicture());
    workService.updateWork(kankanWork);

    MediaResource resource = resourceService.findResource(kankanWork.getResourceId());
    resource.setContent(updateInfo.getContent());
    resource.setTitle(updateInfo.getTitle());
    resource.setKeyWords(updateInfo.getKeyword());
    resourceService.saveResource(resource);

    return success();
  }


  private KankanWorkVo toVo(KankanWork kankanWork, HotPointService hotPointService, HeaderLineService headerLineService,
      ResourceService resourceService) {
    KankanWorkVo kankanWorkVo = new KankanWorkVo(kankanWork);
    kankanWorkVo.addResource(resourceService);

    kankanWorkVo.addHotStatus(hotPointService);

    kankanWorkVo.addHeadLine(headerLineService);

    return kankanWorkVo;
  }
}
