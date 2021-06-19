package com.kankan.api.admin.header;

import javax.validation.Valid;

import com.kankan.constant.EnumItemType;
import com.kankan.module.*;
import com.kankan.param.headline.HeaderLineItemInfo;
import com.kankan.service.KankanWorkService;
import com.kankan.service.NewsService;
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

  private NewsService newsService;

  private KankanWorkService workService;


  public AdminHeaderLineController(HeaderLineService headerLineService, ResourceService resourceService, NewsService newsService, KankanWorkService workService) {
    this.headerLineService = headerLineService;
    this.resourceService = resourceService;
    this.newsService = newsService;
    this.workService = workService;
  }

  @ApiOperation("头条信息组")
  @PostMapping("headerLineInfo")
  public CommonResponse headerLineInfo(@Valid @RequestBody HeaderLineInfo headerLineInfo) {
    HeaderLine headerLine = headerLineInfo.toHeadline();
    headerLine.creatHeaderLine(headerLineService);
    return CommonResponse.success(headerLine.getId());
  }

  @ApiOperation("头条信息列表")
  @GetMapping("headerLineInfo/list")
  public CommonResponse headerLineInfoList() {
    HeaderLine headerLine = HeaderLine.builder().build();
    List<HeaderLine> infoList = headerLine.findHeaderLine(headerLineService);
    return CommonResponse.success(infoList);
  }


  @ApiOperation("创建头条item")
  @PostMapping("headerLineItem")
  public CommonResponse headerLineItem(@Valid @RequestBody HeaderLineItemInfo headerItem) {
    HeaderLineItem headerLine = headerItem.toHeadline();
    headerLine.creatHeadItem(headerLineService);

    MediaResource resource = resourceService.findResource(headerItem.getResourceId());
    EnumItemType enumItemType = EnumItemType.getItem(resource.getMediaType());

    if (enumItemType == EnumItemType.NEWS) {
      News news = newsService.findNews(headerItem.getResourceId());
      if(news!=null){
        newsService.updateHeaderStatus(news.getId(), 2);
      }
    } else if (enumItemType == EnumItemType.VIDEO || enumItemType == EnumItemType.ARTICLE) {
      KankanWork work = workService.findByResourceId(headerItem.getResourceId());
      workService.updateHeaderStatus(work.getId(), 2);
    }
    return CommonResponse.success(headerLine.getId());
  }


  @ApiOperation("删除头条")
  @PostMapping("del/headerLineItem")
  public CommonResponse delHeaderLineItem(@Valid @RequestBody HeaderLineItemInfo headerItem) {
    HeaderLineItem headerLine = headerItem.toHeadline();
    headerLine.delHeadItem(headerLineService);

    MediaResource resource = resourceService.findResource(headerItem.getResourceId());
    EnumItemType enumItemType = EnumItemType.getItem(resource.getMediaType());

    if (enumItemType == EnumItemType.NEWS) {
      News news = newsService.findNews(headerItem.getResourceId());
      if(news!=null){
        newsService.updateHeaderStatus(news.getId(), 1);
      }
    } else if (enumItemType == EnumItemType.VIDEO || enumItemType == EnumItemType.ARTICLE) {
      KankanWork work = workService.findByResourceId(headerItem.getResourceId());
      workService.updateHeaderStatus(work.getId(), 1);
    }

    return CommonResponse.success();
  }






  @ApiOperation("头条item列表")
  @GetMapping("headerLineItem/list/{headerLineId}")
  public CommonResponse headerLineItemList(@PathVariable(value = "headerLineId") String headerLineId) {
    HeaderLineItem headerLine = HeaderLineItem.builder().headerLineId(headerLineId).build();
    List<HeaderLineItem> infoList = headerLine.findHeadItemList(headerLineService);
    return CommonResponse.success(infoList);
  }


}
