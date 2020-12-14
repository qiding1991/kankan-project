package com.kankan.api.admin.work;

import javax.validation.Valid;

import com.kankan.vo.KankanWorkVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.module.KankanWork;
import com.kankan.param.work.WorkAddInfo;
import com.kankan.service.KankanWorkService;
import com.kankan.service.ResourceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
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

    public AdminWorkController(ResourceService resourceService, KankanWorkService workService) {
        this.resourceService = resourceService;
        this.workService = workService;
    }


    @ApiOperation("发布作品")
    @PostMapping("create")
    public CommonResponse createWork(@Valid @RequestBody WorkAddInfo workAddInfo) {
        KankanWork work = workAddInfo.toWork(resourceService);
        work.addWork(workService);
        return success();
    }

    @ApiOperation("作品列表")
    @GetMapping("list")
    public CommonResponse listWork() {
        KankanWork work = KankanWork.builder().build();
        List<KankanWork> infoList = work.findAllWork(workService);
        List<KankanWorkVo> voList = infoList.stream().map(kankanWork -> toVo(kankanWork, resourceService)).collect(Collectors.toList());
        return success(voList);
    }
    private KankanWorkVo toVo(KankanWork kankanWork, ResourceService resourceService) {
        KankanWorkVo kankanWorkVo = new KankanWorkVo(kankanWork);
        kankanWorkVo.addResource(resourceService);
        return kankanWorkVo;
    }


}
