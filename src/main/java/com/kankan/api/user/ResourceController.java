package com.kankan.api.user;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.dao.mapper.ThumpMapper;
import com.kankan.module.KankanComment;
import com.kankan.module.MediaResource;
import com.kankan.module.resouce.Favourite;
import com.kankan.module.resouce.ResourceThump;
import com.kankan.param.resource.CommentParam;
import com.kankan.param.resource.FavouriteParam;
import com.kankan.param.resource.ThumpParam;
import com.kankan.service.*;
import com.kankan.vo.KankanCommentVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
@Validated
@Api(tags = "用户-详情-点赞、评论")
@RestController
@RequestMapping("resource")
public class ResourceController extends BaseController {

  private ResourceService resourceService;

  private CommentService commentService;

  private ThumpService thumpService;

  private FavouriteService favouriteService;

  private KankanUserService userService;

  @Resource
  private ThumpMapper thumpMapper;

  public ResourceController(ResourceService resourceService, CommentService commentService,
                            ThumpService thumpService, FavouriteService favouriteService, KankanUserService userService) {
    this.resourceService = resourceService;
    this.commentService = commentService;
    this.thumpService = thumpService;
    this.favouriteService = favouriteService;
    this.userService = userService;

  }

  @ApiOperation("评论新闻、文章、视频")
  @PostMapping("comment")
  public CommonResponse comment(@RequestBody CommentParam param) {
    //保存
    KankanComment comment = param.toComment();
    comment.save(commentService);
    //回复数+1
    MediaResource resource = param.toResource();
    resource.incrementCommentCount(resourceService);
    return success();
  }


  @ApiOperation("删除评论")
  @PostMapping("comment/cancel/{commentId}")
  public CommonResponse cancelComment(@PathVariable(value = "commentId") Long commentId) {
    //保存
    KankanComment comment = KankanComment.builder().id(commentId).build();
    String resourceId = comment.remove(commentService);
    if (StringUtils.isNotEmpty(resourceId)) {
      MediaResource resource = MediaResource.builder().resourceId(resourceId).build();
      resource.decrCommentCount(resourceService);
    }
    return success();
  }


  @ApiOperation("评论回复")
  @PostMapping("comment/{commentId}")
  public CommonResponse comment(@PathVariable(value = "commentId") Long parentId, @RequestBody CommentParam param) {
    KankanComment comment = param.toComment(parentId);
    comment.save(commentService);
    return success();
  }


  @ApiOperation("点赞新闻、文章、视频")
  @PostMapping("thump")
  public CommonResponse thump(@RequestBody ThumpParam param) {
    //保存点赞
    ResourceThump thump = param.toThump();
    thump.save(thumpService);
    //点赞数+1
    MediaResource resource = param.toResource();
    resource.incrementThumpCount(resourceService);
    return success();
  }

  @ApiOperation("取消点赞")
  @PostMapping("thump/cancel")
  public CommonResponse cancelThump(@RequestBody ThumpParam param) {
    //删除点赞
    ResourceThump thump = param.toThump();
    thump.cancel(thumpService);
    //点赞数-1
    MediaResource resource = param.toResource();
    resource.decreaseThumpCount(resourceService);
    return success();
  }


  @ApiOperation("点赞 评论")
  @PostMapping("thump/{commentId}")
  public CommonResponse thump(@PathVariable(value = "commentId") Long commentId, @RequestBody ThumpParam param) {
    //保存点赞
    ResourceThump thump = param.toThump(commentId);
    thump.save(thumpService);
    //评论数加+1
    KankanComment kankanComment = KankanComment.builder().id(commentId).build();
    kankanComment.incrementThumpCount(commentService);
    return success();
  }

  @ApiOperation("取消点赞评论")
  @PostMapping("thump/{commentId}/cancel")
  public CommonResponse cancelThump(@PathVariable(value = "commentId") Long commentId, @RequestBody ThumpParam param) {
    //删除点赞
    ResourceThump thump = param.toThump();
    thump.cancel(thumpService);
    //点赞数-1
    //评论数加+1
    KankanComment kankanComment = KankanComment.builder().id(commentId).build();
    kankanComment.decreaseThumpCount(commentService);
    return success();
  }


  @ApiOperation("收藏")
  @PostMapping("favourite")
  public CommonResponse favourite(@RequestBody FavouriteParam param) {
    Favourite favourite = param.toFavourite();
    favourite.save(favouriteService);
    return success();
  }


  @ApiOperation("取消收藏")
  @PostMapping("favourite/cancel")
  public CommonResponse favouriteCancel(@RequestBody FavouriteParam param) {
    Favourite favourite = param.toFavourite();
    favourite.remove(favouriteService);
    return success();
  }


  @ApiOperation("相关资源")
  @GetMapping("related")
  public CommonResponse related(@RequestParam(value = "resourceId") String resourceId,
                                @RequestParam(value = "mediaType") Integer mediaType) {
    MediaResource resource = MediaResource.builder().mediaType(mediaType).resourceId(resourceId).build();
    List<MediaResource> infoList = resource.findRelated(resourceService);
    return success(infoList);
  }

  @ApiOperation("获取所有的评论")
  @GetMapping("comment/list")
  public CommonResponse commentList(@RequestParam(value = "userId",required = false) Long userId,
                                    @RequestParam(value = "resourceId") String resourceId) {
    MediaResource mediaResource = MediaResource.builder().resourceId(resourceId).build();
    mediaResource.incrementReadCount(resourceService);
    KankanComment comment = KankanComment.builder().resourceId(resourceId).build();
    List<KankanCommentVo> commentVoList = comment.resourceCommentInfo(commentService, userService);
    KankanCommentVo.addThumpStatus(commentVoList, userId, thumpMapper);
    return success(commentVoList);
  }


  @ApiOperation("获取某条评论相关的评论")
  @GetMapping("comment/list/{commentId}")
  public CommonResponse commentList(@PathVariable(value = "commentId") Long commentId,
                                    @RequestParam(value = "userId",required = false) Long userId, @RequestParam(value = "resourceId") String resourceId) {
    MediaResource mediaResource = MediaResource.builder().resourceId(resourceId).build();
    mediaResource.incrementReadCount(resourceService);
    KankanComment comment = KankanComment.builder().resourceId(resourceId).build();
    KankanCommentVo commentVo = comment.resourceCommentInfo(commentService, userService, commentId);
    KankanCommentVo.addThumpStatus(commentVo, userId,thumpMapper);
    return success(commentVo);
  }
}



