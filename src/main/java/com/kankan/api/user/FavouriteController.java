package com.kankan.api.user;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.kankan.constant.EnumItemType;
import com.kankan.module.*;
import com.kankan.service.*;
import com.kankan.vo.tab.ArticleItemVo;
import com.kankan.vo.tab.NewsItemVo;
import com.kankan.vo.tab.TabItemVo;
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
import com.kankan.module.resouce.Favourite;

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

  private KankanWorkService workService;

  private KankanUserService userService;

  private NewsService newsService;

  private TabService tabService;


  public FavouriteController(FavouriteService favouriteService, ResourceService resourceService, KankanWorkService workService, KankanUserService userService, NewsService newsService, TabService tabService) {
    this.favouriteService = favouriteService;
    this.resourceService = resourceService;
    this.workService = workService;
    this.userService = userService;
    this.newsService = newsService;
    this.tabService = tabService;
  }

  @ApiOperation("收藏列表")
  @GetMapping("list")
  public CommonResponse list(
    @RequestParam(value = "userId") String userId,
    @RequestParam(value = "offset", required = false, defaultValue = "0") String offset,
    @RequestParam(value = "size") Integer size) {
    PageQuery pageQuery = PageQuery.builder().offset(offset).size(size).build();
    Favourite favourite = Favourite.builder().pageQuery(pageQuery).userId(userId).build();
    List<Favourite> infoList = favourite.list(favouriteService);
    List<MediaResource> mediaResources = infoList.stream().map(fav -> resourceService.findResource(fav.getResourceId())).collect(Collectors.toList());
    List<TabItemVo> itemVoList = mediaResources.stream().map(resource -> toItemVo(resource.getMediaType(), resource)).filter(tabItemVo -> tabItemVo!=null).collect(Collectors.toList());
    return success(PageData.pageData(itemVoList, size));
  }

  private TabItemVo toItemVo(Integer mediaType, MediaResource mediaResource) {
    EnumItemType itemType = EnumItemType.getItem(mediaType);
    switch (itemType) {
      case ARTICLE:
      case VIDEO:
        KankanWork kankanWork=KankanWork.fromResourceId(mediaResource.getResourceId(),workService);
        KankanUser  kankanUser= KankanUser.fromUserId(kankanWork.getUserId(),userService);
        return new ArticleItemVo(kankanWork,kankanUser,mediaResource);
      case NEWS:
        News news=News.fromResourceId(mediaResource.getResourceId(),newsService);
        Tab tab=Tab.fromTabId(news.getTabId(),tabService);
        return  new NewsItemVo(tab,news,mediaResource);
      default:
        return null;
    }
  }

  @ApiOperation("删除收藏")
  @PostMapping("del")
  public CommonResponse del(@RequestBody List<String> favouriteIdList) {
    favouriteIdList.parallelStream().map(Favourite::fromId)
      .forEach(favourite -> favourite.remove(favouriteService));
    return success();
  }
}
