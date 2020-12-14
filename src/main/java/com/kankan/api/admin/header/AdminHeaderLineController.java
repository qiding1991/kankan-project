package com.kankan.api.admin.header;

import javax.validation.Valid;

import com.kankan.module.HeaderLine;
import com.kankan.module.HeaderLineItem;
import com.kankan.param.headline.HeaderLineItemInfo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.kankan.constant.CommonResponse;
import com.kankan.param.headline.HeaderLineInfo;
import com.kankan.service.HeaderLineService;
import com.kankan.service.ResourceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-03
 */
@Validated
@Api(tags = "管理后台-管理-头条")
@RestController
@RequestMapping("admin/headerLine")
public class AdminHeaderLineController {

    private HeaderLineService headerLineService;

    private ResourceService resourceService;

    public AdminHeaderLineController(HeaderLineService headerLineService, ResourceService resourceService) {
        this.headerLineService = headerLineService;
        this.resourceService = resourceService;
    }

    @ApiOperation("头条信息组")
    @PostMapping("headerLineInfo")
    public CommonResponse  headerLineInfo(@Valid @RequestBody HeaderLineInfo headerLineInfo){
          HeaderLine headerLine= headerLineInfo.toHeadline();
          headerLine.creatHeaderLine(headerLineService);
          return CommonResponse.success();
    }

    @ApiOperation("头条信息列表")
    @GetMapping("headerLineInfo/list")
    public CommonResponse  headerLineInfoList(){
        HeaderLine headerLine= HeaderLine.builder().build();
        List<HeaderLine> infoList= headerLine.findHeaderLine(headerLineService);
        return CommonResponse.success(infoList);
    }


    @ApiOperation("创建头条item")
    @PostMapping("headerLineItem")
    public CommonResponse  headerLineItem(@Valid @RequestBody HeaderLineItemInfo headerItem){
        HeaderLineItem headerLine=headerItem.toHeadline(resourceService);
        headerLine.creatHeadItem(headerLineService);
        return CommonResponse.success();
    }


    @ApiOperation("头条item列表")
    @GetMapping("headerLineItem/list/{headerLineId}")
    public CommonResponse  headerLineItemList(@PathVariable(value = "headerLineId") Long headerLineId){
        HeaderLineItem headerLine= HeaderLineItem.builder().headerLineId(headerLineId).build();
        List<HeaderLineItem> infoList= headerLine.findHeadItemList(headerLineService);
        return CommonResponse.success(infoList);
    }



}
