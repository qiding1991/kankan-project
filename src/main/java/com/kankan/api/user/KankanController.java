package com.kankan.api.user;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.module.KankanRecommend;
import com.kankan.module.KankanType;
import com.kankan.module.KankanUser;
import com.kankan.service.KankanRecommendService;
import com.kankan.service.KankanTypeService;
import com.kankan.service.KankanUserService;
import com.kankan.vo.KankanTypeVo;
import com.kankan.vo.KankanUserVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */

@Validated
@Api(tags = "看看专栏")
@RestController
@RequestMapping("kankan/recommend")
public class KankanController extends BaseController {


    private KankanRecommendService recommendService;

    private KankanUserService userService;

    private KankanTypeService typeService;

    public KankanController(KankanRecommendService recommendService, KankanUserService userService,
            KankanTypeService typeService) {
        this.recommendService = recommendService;
        this.userService = userService;
        this.typeService = typeService;
    }

    @ApiOperation("推荐列表")
    @GetMapping("list")
    public CommonResponse recommendList() {
        List<KankanRecommend> recommendList = recommendService.findAll();
        List<KankanUserVo> userList =
                recommendList.stream()
                        .map(recommend -> ((Function<KankanRecommend, KankanUserVo>) kankanRecommend -> transform(
                                recommend)).apply(recommend)).collect(Collectors.toList());
        return success(userList);
    }

    private KankanUserVo transform(KankanRecommend recommend) {
        KankanUser kankanUser = KankanUser.builder().userId(recommend.getUserId()).build();
        return kankanUser.findUser(userService).toVo();
    }


    @ApiOperation("分类+用户列表")
    @GetMapping("userList")
    public CommonResponse userList() {
        //获取分类
        KankanType kankanType = KankanType.builder().build();
        List<KankanType> typeList = kankanType.findAll(typeService);
        //获取分类下的用户
        List<KankanTypeVo> infoList = typeList.stream().map(KankanType::toVo).collect(Collectors.toList());
        infoList.forEach(this::addKankanUser);
        return success(infoList);
    }

    private void addKankanUser(KankanTypeVo kankanTypeVo) {
        KankanUser user = KankanUser.builder().userType(kankanTypeVo.getId()).build();
        List<KankanUser> infoList = user.commonTypeUser(userService);
        List<KankanUserVo> voList = infoList.stream().map(KankanUser::toVo).collect(Collectors.toList());
        kankanTypeVo.setUserVoList(voList);
    }
}
