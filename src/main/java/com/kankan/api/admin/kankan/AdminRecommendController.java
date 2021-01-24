package com.kankan.api.admin.kankan;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.module.KankanRecommend;
import com.kankan.param.KankanRecommendDelParam;
import com.kankan.param.kankan.KankanRecommendParam;
import com.kankan.service.KankanRecommendService;
import com.kankan.vo.kankan.KankanRecommendVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
@Api(tags = "管理后台-看看号-推荐")
@RequestMapping("admin/kankan/recommend")
@RestController
public class AdminRecommendController extends BaseController {

    private KankanRecommendService recommendService;

    public AdminRecommendController(KankanRecommendService recommendService) {
        this.recommendService = recommendService;
    }

    @ApiOperation("推荐list")
    @PostMapping("list")
    public CommonResponse list() {
        List<KankanRecommend> recommendList =recommendService.findAll();
        List<KankanRecommendVo> infoList=recommendList.stream().map((recommend)->recommend.toVo()).collect(Collectors.toList());
        return success(infoList);
    }


    @ApiOperation("推荐用户")
    @PostMapping("add")
    public CommonResponse add(@RequestBody KankanRecommendParam param) {
        KankanRecommend recommend = param.toRecommend();
        recommend.add(recommendService);
        return success(2);
    }

    @ApiOperation("去掉推荐")
    @PostMapping("del")
    public CommonResponse del(@RequestBody KankanRecommendDelParam param) {
        KankanRecommend recommend = param.toRecommend();
        recommend.del(recommendService);
        return success();
    }

    @ApiOperation("更新排序")
    @PostMapping("update")
    public CommonResponse update(@RequestBody KankanRecommendParam param) {
        KankanRecommend recommend = param.toRecommend();
        recommend.update(recommendService);
        return success();
    }

}
