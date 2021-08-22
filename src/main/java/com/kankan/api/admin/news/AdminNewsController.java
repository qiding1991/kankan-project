package com.kankan.api.admin.news;

import com.kankan.constant.EnumItemType;
import com.kankan.service.HeaderLineService;
import com.kankan.service.HotPointService;
import java.util.Objects;
import javax.validation.Valid;

import com.kankan.api.admin.news.param.UpdateNewsInfo;
import com.kankan.dao.entity.NewsEntity;
import com.kankan.module.MediaResource;
import com.kankan.param.AuditParam;
import com.kankan.vo.NewsVo;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
 * @author <qiding@qiding.com> Created on 2020-12-03
 */
@Validated
@Api(tags = "管理后台-管理-新闻")
@RestController
@RequestMapping("admin/news")
public class AdminNewsController extends BaseController {

  @Autowired
  private ResourceService resourceService;
  @Autowired
  private NewsService newsService;
  @Autowired
  private HotPointService hotPointService;

  @Autowired
  private HeaderLineService headerLineService;


  @ApiOperation("创建新闻")
  @PostMapping("create")
  public CommonResponse createNews(@Valid @RequestBody NewsAddInfo newsAddInfo) {
    News news = newsAddInfo.toNews(resourceService);
    news.create(newsService);
    return success(news.getId());
  }

  @ApiOperation("更新新闻")
  @PostMapping("update")
  public CommonResponse updateNews(@RequestBody UpdateNewsInfo updateNewsInfo) {
    NewsEntity newsEntity = new NewsEntity();
    BeanUtils.copyProperties(updateNewsInfo, newsEntity);
    newsService.updateNews(newsEntity);

    News recordNews = newsService.findNewsById(updateNewsInfo.getId());
    MediaResource resource = resourceService.findResource(recordNews.getResourceId());
    if (StringUtils.isNotBlank(updateNewsInfo.getContent())) {
      resource.setContent(updateNewsInfo.getContent());
    }
    if (StringUtils.isNotBlank(updateNewsInfo.getTitle())) {
      resource.setTitle(updateNewsInfo.getTitle());
    }
    resourceService.updateResource(resource);
    return CommonResponse.success();
  }

  @ApiOperation("审核新闻")
  @PostMapping("audit")
  public CommonResponse auditNews(@RequestBody AuditParam auditParam) {
    newsService.auditNews(auditParam);
    return CommonResponse.success();
  }

  @ApiOperation("新闻列表")
  @PostMapping("list")
  public CommonResponse createNews() {
    News news = News.builder().build();
    List<News> infoList = news.findAll(newsService);
    List<NewsVo> newsVos = infoList.stream().map(news1 -> news1.toItemVo(resourceService)).collect(Collectors.toList());
    return success(newsVos);
  }

  @ApiOperation("获取当前用户创建的新闻")
  @GetMapping("findByUserId")
  public CommonResponse findByUserId(@RequestParam(value = "userId") String userId) {
    List<News> infoList = newsService.findByUserId(userId);
    List<NewsVo> newsVos = infoList.stream().map(news1 -> news1.toItemVo(resourceService)).collect(Collectors.toList());
    return success(newsVos);
  }

  @ApiOperation("删除新闻")
  @PostMapping("delete/{id}")
  public CommonResponse delete(@PathVariable(value = "id") String id) {

    //获取新闻详情
    News newsInfo = newsService.findById(id);
    newsService.delete(id);
    //删除热点
    hotPointService.delHot(EnumItemType.NEWS.getCode(), id);
    //删除头条
    if(Objects.nonNull(newsInfo)){
      headerLineService.delByResourceId(newsInfo.getResourceId());
    }

    return success();
  }
}
