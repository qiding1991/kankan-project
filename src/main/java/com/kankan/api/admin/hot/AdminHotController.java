package com.kankan.api.admin.hot;

import com.kankan.constant.EnumItemType;
import com.kankan.service.KankanWorkService;
import com.kankan.service.NewsService;
import org.springframework.web.bind.annotation.*;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.module.HotPoint;
import com.kankan.param.hot.HotInfo;
import com.kankan.service.HotPointService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-03
 */
@Api(tags = "管理后台-管理-热点")
@RestController
@RequestMapping("admin/hot")
public class AdminHotController extends BaseController {


  private HotPointService hotPointService;

  private NewsService newsService;

  private KankanWorkService workService;

  public AdminHotController(HotPointService hotPointService, NewsService newsService, KankanWorkService workService) {
    this.hotPointService = hotPointService;
    this.newsService = newsService;
    this.workService = workService;
  }

  @ApiOperation("创建热点")
  @PostMapping("create")
  public CommonResponse createHot(@RequestBody HotInfo hotInfo) {
    HotPoint hotPoint = hotInfo.toHotPoint();
    hotPoint.create(hotPointService);
    EnumItemType itemType = EnumItemType.getItem(hotInfo.getItemType());
    if (itemType == EnumItemType.NEWS) {
      newsService.updateHotStatus(hotInfo.getItemId(), 2);
    } else if (itemType == EnumItemType.VIDEO || itemType == EnumItemType.ARTICLE) {
      workService.setHot(hotInfo.getItemId(), 2);
    }
    return success(hotPoint.getId());
  }

  @ApiOperation("取消热点")
  @PostMapping("cancel")
  public CommonResponse cancelHot(@RequestBody HotInfo hotInfo) {
    HotPoint hotPoint = hotInfo.toHotPoint();
    hotPoint.delHotInfo(hotPointService);
    EnumItemType itemType = EnumItemType.getItem(hotInfo.getItemType());
    if (itemType == EnumItemType.NEWS) {
      newsService.updateHotStatus(hotInfo.getItemId(), 1);
    } else if (itemType == EnumItemType.VIDEO || itemType == EnumItemType.ARTICLE) {
      workService.setHot(hotInfo.getItemId(), 1);
    }
    return success();
  }


  @ApiOperation("热点列表")
  @GetMapping("list")
  public CommonResponse list() {
    HotPoint hotPoint = HotPoint.builder().build();
    List<HotPoint> infoList = hotPoint.listAll(hotPointService);
    return success(infoList);
  }


}
