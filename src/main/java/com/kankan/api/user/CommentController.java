package com.kankan.api.user;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kankan.api.BaseController;
import com.kankan.constant.CommonResponse;
import com.kankan.module.KankanComment;
import com.kankan.module.KankanWork;
import com.kankan.module.MediaResource;
import com.kankan.service.CommentService;
import com.kankan.service.KankanWorkService;
import com.kankan.service.ResourceService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
@Validated
@Api(tags = "回复、评论")
@RestController
@RequestMapping("comment")
public class CommentController extends BaseController {

    private CommentService commentService;
    private KankanWorkService workService;
    private ResourceService resourceService;

    public CommentController(CommentService commentService, KankanWorkService workService,
            ResourceService resourceService) {
        this.commentService = commentService;
        this.workService = workService;
        this.resourceService = resourceService;
    }

    @ApiOperation("我评论的")
    @GetMapping("from")
    public CommonResponse from(@RequestParam(value = "userId") Long userId) {
        KankanComment comment = KankanComment.builder().userId(userId).build();
        List<KankanComment> infoList = comment.fromMe(commentService);
        return success(infoList);
    }


    @ApiOperation("回复我的")
    @GetMapping("to")
    public CommonResponse to(@RequestParam(value = "userId") Long userId) {
        //1.获取我所有的作品的评论
        KankanWork kankanWork = KankanWork.builder().userId(userId).build();
        List<KankanWork> infoList = kankanWork.findMyWork(workService);
        List<KankanComment> workCondition=infoList.parallelStream().map(work->KankanComment.builder().resourceId(work.getResourceId()).parentId(0L).build()).collect(Collectors.toList());
        //2.评论我的回复
        KankanComment comment = KankanComment.builder().userId(userId).build();
        List<KankanComment> relayCondition= comment.toMe(commentService);
        //3.查看所有的评论信息
        workCondition.addAll(relayCondition);
        List<KankanComment> commentList= commentService.findComment(workCondition);
        //4.获取resource相关的资源
        Set<String> resourceIdList=commentList.parallelStream().map(KankanComment::getResourceId).collect(Collectors.toSet());
        List<MediaResource> resourceList=resourceService.findResource(resourceIdList);
        return success(infoList);
    }


}
