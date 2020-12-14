package com.kankan.api.admin.news;

import javax.validation.Valid;

import com.kankan.vo.NewsVo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.module.News;
import com.kankan.param.news.NewsAddInfo;
import com.kankan.service.NewsService;
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
@Api(tags = "管理后台-管理-新闻")
@RestController
@RequestMapping("admin/news")
public class AdminNewsController extends BaseController {

    private ResourceService resourceService;
    private NewsService newsService;

    public AdminNewsController(ResourceService resourceService, NewsService newsService) {
        this.resourceService = resourceService;
        this.newsService = newsService;
    }

    @ApiOperation("创建新闻")
    @PostMapping("create")
    public CommonResponse createNews(@Valid @RequestBody NewsAddInfo newsAddInfo){
        News news=newsAddInfo.toNews(resourceService);
        news.create(newsService);
        return success();
    }

    @ApiOperation("新闻列表")
    @PostMapping("list")
    public CommonResponse createNews(){
        News news= News.builder().build();
        List<News> infoList= news.findAll(newsService);
        List<NewsVo> newsVos = infoList.stream().map(news1 -> news1.toItemVo(resourceService)).collect(Collectors.toList());
        return success(newsVos);
    }



}
