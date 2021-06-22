package com.kankan.api.user;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.constant.PageData;
import com.kankan.module.Follow;
import com.kankan.module.KankanUser;
import com.kankan.module.PageQuery;
import com.kankan.param.FollowParam;
import com.kankan.service.FollowService;
import com.kankan.service.KankanUserService;
import com.kankan.vo.KankanUserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author <qiding@qiding.com> Created on 2020-12-06
 */
@Validated
@Api(tags = "关注")
@RestController
@RequestMapping("follow")
public class FollowController extends BaseController {

  private FollowService followService;
  private KankanUserService kankanUserService;

  public FollowController(FollowService followService, KankanUserService kankanUserService) {
    this.followService = followService;
    this.kankanUserService = kankanUserService;
  }

  @ApiOperation("关注列表")
  @GetMapping("list")
  public CommonResponse list(
      @RequestParam(value = "offset", required = false, defaultValue = "0") String offset,
      @RequestParam(value = "size") Integer size, @RequestParam(value = "userId") String userId) {
    Follow follow = Follow.builder().userId(userId).build();
    if ("1".equals(offset)) {
      offset = "0";
    }
    PageQuery pageQuery = PageQuery.builder().offset(offset).size(size).build();
    List<Follow> followList = follow.list(followService, pageQuery);
    List<String> userIdList = followList.stream().map(Follow::getFollowId).collect(Collectors.toList());
    List<KankanUserVo> userVoList = userIdList.stream().map(
        id -> ((Function<String, KankanUserVo>) userId1 -> KankanUser.builder().userId(userId1).build().findUser(kankanUserService).toVo()).apply(id))
        .collect(Collectors.toList());
    PageData pageData = PageData.pageData(userVoList, size);
    return success(pageData);
  }


  @ApiOperation("关注")
  @PostMapping("add")
  public CommonResponse add(@RequestBody FollowParam param) {
    Follow follow = param.toFollow();
    follow.add(followService);
    kankanUserService.incrFollowCount(param.getUserId());
    kankanUserService.incrFansCount(param.getFollowId());
    return success();
  }

  @ApiOperation("取消关注")
  @PostMapping("cancel")
  public CommonResponse cancel(@RequestBody FollowParam param) {
    Follow follow = param.toFollow();
    follow.cancel(followService);
    kankanUserService.decrFollowCount(param.getUserId());
    kankanUserService.decrFansCount(param.getFollowId());
    return success();
  }

  @ApiOperation("是否已经关注")
  @GetMapping("isFollow")
  public CommonResponse isFollow(@RequestParam(value = "userId") String userId, @RequestParam(value = "targetUserId") String targetUserId) {
    Follow follow = Follow.builder().userId(userId).followId(targetUserId).build();
    return success(follow.exists(followService));
  }
}
