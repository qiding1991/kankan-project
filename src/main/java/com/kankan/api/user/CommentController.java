package com.kankan.api.user;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.kankan.constant.EnumItemType;
import com.kankan.module.*;
import com.kankan.service.*;
import com.kankan.vo.comment.BaseCommentVo;
import com.kankan.vo.comment.NewsCommentVo;
import com.kankan.vo.comment.WorkCommentVo;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
@Log4j2
@Validated
@Api(tags = "回复、评论")
@RestController
@RequestMapping("comment")
public class CommentController extends BaseController {

  private CommentService commentService;
  private KankanWorkService workService;
  private ResourceService resourceService;
  private KankanUserService kankanUserService;
  private NewsService newsService;
  private TabService tabService;
  private KankanWorkService kankanWorkService;


  public CommentController(CommentService commentService, KankanWorkService workService,
                           ResourceService resourceService, KankanUserService kankanUserService, NewsService newsService, TabService tabService, KankanWorkService kankanWorkService) {
    this.commentService = commentService;
    this.workService = workService;
    this.resourceService = resourceService;
    this.kankanUserService = kankanUserService;
    this.newsService = newsService;
    this.tabService = tabService;
    this.kankanWorkService = kankanWorkService;
  }

  @ApiOperation("我评论的")
  @GetMapping("from")
  public CommonResponse from(@RequestParam(value = "userId") Long userId) {
    KankanComment comment = KankanComment.builder().userId(userId).build();
    List<KankanComment> infoList = comment.fromMe(commentService);
    log.info("comment by userId={},infoList={}",userId,infoList);
    //获取用户信息
    List<BaseCommentVo> resultList = infoList.stream().map(item -> {
      KankanUser kankanUser = kankanUserService.findUser(item.getUserId());
      MediaResource resource = resourceService.findResource(item.getResourceId());
      EnumItemType itemType = EnumItemType.getItem(resource.getMediaType());
      switch (itemType) {
        case NEWS:
          News news = News.fromResourceId(item.getResourceId(), newsService);
          Tab newTab = Tab.fromTabId(news.getTabId(), tabService);
          return new NewsCommentVo(newTab, news, kankanUser, item, resource);
        case VIDEO:
        case ARTICLE:
          KankanWork kankanWork = KankanWork.fromResourceId(item.getResourceId(), kankanWorkService);
          return new WorkCommentVo(kankanWork, kankanUser, item, resource);
        default:
          return null;
      }
    }).collect(Collectors.toList());
    //获取基本信息
    return success(resultList);
  }


  @ApiOperation("回复我的")
  @GetMapping("to")
  public CommonResponse to(@RequestParam(value = "userId") Long userId) {
    //1.获取我所有的作品的评论
    KankanWork kankanWork = KankanWork.builder().userId(userId).build();
    List<KankanWork> infoList = kankanWork.findMyWork(workService);
    log.info("user_id={},myWorkList={}",userId,infoList);
    List<KankanComment> workCondition = infoList.parallelStream().map(work -> KankanComment.builder().resourceId(work.getResourceId()).parentId(0L).build()).collect(Collectors.toList());
    log.info("resource commentList={}",workCondition);

    //2.评论我的回复
    KankanComment comment = KankanComment.builder().userId(userId).build();
    List<KankanComment> myComment = comment.myComment(commentService);
    log.info("my userId={}, comment={}",userId,myComment);
    List<KankanComment> relayCondition=myComment.stream().map(item->KankanComment.builder().resourceId(item.getResourceId()).parentId(item.getId()).build()).collect(Collectors.toList());
    log.info("my userId={}, comment={},relayCondition={}",userId,myComment,relayCondition);
    //3.查看所有的评论信息
    workCondition.addAll(relayCondition);
    List<KankanComment> commentList = commentService.findComment(workCondition);
    log.info("find commentList, userid={},workCondition={},commentList={}",userId,workCondition,commentList);
    //获取用户信息
    List<BaseCommentVo> resultList = commentList.stream().map(item -> {
      KankanUser kankanUser = kankanUserService.findUser(item.getUserId());
      MediaResource resource = resourceService.findResource(item.getResourceId());
      EnumItemType itemType = EnumItemType.getItem(resource.getMediaType());
      switch (itemType) {
        case NEWS:
          News news = News.fromResourceId(item.getResourceId(), newsService);
          Tab newTab = Tab.fromTabId(news.getTabId(), tabService);
          return new NewsCommentVo(newTab, news, kankanUser, item, resource);
        case VIDEO:
        case ARTICLE:
          KankanWork work = KankanWork.fromResourceId(item.getResourceId(), kankanWorkService);
          return new WorkCommentVo(work, kankanUser, item, resource);
        default:
          return null;
      }
    }).collect(Collectors.toList());

    return success(resultList);
  }
}
