package com.kankan.api.user;

import com.kankan.module.KankanWork;
import com.kankan.param.tab.TabPageInfo;
import com.kankan.service.FollowService;
import com.kankan.service.KankanWorkService;
import com.kankan.service.ResourceService;
import com.kankan.vo.tab.ArticleItemVo;
import com.kankan.vo.tab.TabItemVo;
import com.kankan.vo.tab.VideoItemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.module.KankanUser;
import com.kankan.service.KankanUserService;
import com.kankan.vo.KankanUserVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
@Validated
@Api(tags = "专栏用户接口")
@RestController
@RequestMapping("kankan/user")
public class KankanUserController extends BaseController {

  @Autowired
  private KankanUserService kankanUserService;

  @Autowired
  private KankanWorkService workService;

  @Autowired
  private ResourceService resourceService;

  @Autowired
  private FollowService followService;


  @ApiOperation("作者基本信息-粉丝数、阅读量、关注基本信息")
  @GetMapping("baseInfo")
  public CommonResponse baseInfo(@RequestParam(value = "currentUserId", required = false) Long currentUserId, @RequestParam(value = "userId") Long userId) {
    KankanUser user = KankanUser.builder().userId(userId).build();
    user.completeInfo(kankanUserService);
    KankanUserVo kankanUserVo = user.toVo();
    if (currentUserId != null) {
      kankanUserVo.setFollowStatus(followService.exists(currentUserId, userId));
    }
    return success(kankanUserVo);
  }


  @ApiOperation("作者的文章")
  @GetMapping("article")
  public CommonResponse article(@RequestParam(value = "userId") Long userId) {
    return getUserWorkInfo(userId, 0);

  }


  @ApiOperation("作者的视频")
  @GetMapping("video")
  public CommonResponse video(@RequestParam(value = "userId") Long userId) {
    return getUserWorkInfo(userId, 1);
  }

  private CommonResponse getUserWorkInfo(Long userId, Integer workType) {
    List<KankanWork> workList = workService.findUserWork(userId, workType);
    List<TabItemVo> itemVoList = workList.stream().map(this::transform).collect(Collectors.toList());
    return success(itemVoList);
  }


  /**
   * 看看信息展示
   */
  private TabItemVo transform(KankanWork kankanWork) {
    if (0 == kankanWork.getType()) {
      ArticleItemVo articleItemVo = kankanWork.toArticleItemVo(kankanUserService, resourceService);
      return articleItemVo;
    } else {
      VideoItemVo itemVo = kankanWork.toVideoItemVo(kankanUserService, resourceService);
      return itemVo;
    }
  }
}
