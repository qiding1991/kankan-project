package com.kankan.api.admin.ad;

import javax.validation.Valid;

import com.kankan.vo.KankanadVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.module.KankanAd;
import com.kankan.param.ad.AdWithTitle;
import com.kankan.param.ad.AdNoTtitle;
import com.kankan.service.KankanAdService;
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
@Api(tags = "管理后台-管理-广告")
@RestController
@RequestMapping("admin/ad")
public class AdminAdController extends BaseController {

    private KankanAdService kankanAdService;

    private ResourceService resourceService;

    public AdminAdController(KankanAdService kankanAdService, ResourceService resourceService) {
        this.kankanAdService = kankanAdService;
        this.resourceService = resourceService;
    }

    @ApiOperation("创建ad有标题")
    @PostMapping("create/withTitle")
    public CommonResponse createAd(@Valid @RequestBody AdWithTitle adparam) {
        KankanAd kankanAd = adparam.toAd(resourceService);
        kankanAd.create(kankanAdService);
        return success(kankanAd.getId());
    }

    @ApiOperation("创建ad无标题")
    @PostMapping("create/noTitle")
    public CommonResponse createAd2(@Valid @RequestBody AdNoTtitle adNoTtitle) {
        KankanAd kankanAd = adNoTtitle.toAd(resourceService);
        kankanAd.create(kankanAdService);
        return success(kankanAd.getId());
    }

    @ApiOperation("广告列表")
    @GetMapping("list")
    public CommonResponse list() {
        KankanAd kankanAd = KankanAd.builder().build();
        List<KankanAd> infoList = kankanAd.findAll(kankanAdService);
        List<KankanadVo> resultList = infoList.stream().map(ad -> new KankanadVo(ad, resourceService)).collect(Collectors.toList());
        return success(resultList);
    }
}
