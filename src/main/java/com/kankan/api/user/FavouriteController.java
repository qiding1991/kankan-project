package com.kankan.api.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.constant.PageData;
import com.kankan.module.PageQuery;
import com.kankan.module.resouce.Favourite;
import com.kankan.service.FavouriteService;
import com.kankan.service.ResourceService;
import com.kankan.vo.FavouriteVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
@Validated
@Api(tags = "收藏")
@RestController
@RequestMapping("favourite")
public class FavouriteController extends BaseController {

    private FavouriteService favouriteService;

    private ResourceService resourceService;


    public FavouriteController(FavouriteService favouriteService, ResourceService resourceService) {
        this.favouriteService = favouriteService;
        this.resourceService = resourceService;
    }

    @ApiOperation("收藏列表")
    @GetMapping("list")
    public CommonResponse list(
            @RequestParam(value = "userId") Long userId,
            @RequestParam(value = "offset", required = false, defaultValue = "0") Long offset,
            @RequestParam(value = "size") Integer size) {

        PageQuery pageQuery= PageQuery.builder().offset(offset).size(size).build();
        Favourite favourite = Favourite.builder().pageQuery(pageQuery).userId(userId).build();

        List<Favourite> infoList = favourite.list(favouriteService);
        List<FavouriteVo> resultList =
                infoList.stream().map(favourite1 -> favourite1.toVo()).collect(Collectors.toList());
        return success(PageData.pageData(resultList, size));
    }

    @ApiOperation("删除收藏")
    @PostMapping("del")
    public CommonResponse del(@RequestBody List<Long> favouriteIdList) {
        favouriteIdList.parallelStream().map(Favourite::fromId)
                .forEach(favourite -> favourite.remove(favouriteService));
        return success();
    }
}
