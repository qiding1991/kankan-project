package com.kankan.api.admin.hot;

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

    public AdminHotController(HotPointService hotPointService) {
        this.hotPointService = hotPointService;
    }

    @ApiOperation("创建热点")
    @PostMapping("create")
    public CommonResponse createHot(@RequestBody HotInfo hotInfo){
          HotPoint hotPoint=hotInfo.toHotPoint();
          hotPoint.create(hotPointService);
         return success();
    }


    @ApiOperation("热点列表")
    @GetMapping("list")
    public CommonResponse list(){
        HotPoint hotPoint= HotPoint.builder().build();
        List<HotPoint> infoList= hotPoint.listAll(hotPointService);
        return success(infoList);
    }


}
