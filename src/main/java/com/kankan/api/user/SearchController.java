package com.kankan.api.user;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.module.Tab;
import com.kankan.service.TabService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户搜索服务")
@RestController
@RequestMapping("user/search")
public class SearchController extends BaseController {

  @Autowired
  private TabService tabService;

  @ApiOperation(value = "根据关键字搜索")
  @GetMapping("byKeyWords")
  public CommonResponse searchHot(@RequestParam(value = "tabId") Long tabId,
                                  @RequestParam(value = "keyword") String keyword) {
    //获取tab分类
    Tab tab = tabService.findTab(tabId);
    //搜索条件
    //1.新闻
    //2.热点
    //3.文章
    //


    return success();
  }

}
