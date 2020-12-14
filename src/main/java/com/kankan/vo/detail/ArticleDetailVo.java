package com.kankan.vo.detail;

import com.kankan.module.KankanComment;
import com.kankan.module.KankanUser;
import com.kankan.module.KankanWork;
import com.kankan.module.MediaResource;
import com.kankan.service.CommentService;
import com.kankan.service.KankanUserService;
import com.kankan.service.KankanWorkService;
import com.kankan.vo.KankanCommentVo;
import com.kankan.vo.KankanUserVo;
import com.kankan.vo.tab.AdItemVo;
import com.kankan.vo.tab.ArticleItemVo;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
public class ArticleDetailVo {
    private String resourceId;
    private KankanUserVo userVo;
    private String content;
    private AdItemVo adItemVo;
    private String newsTitle = "作者其它文章";
    private List<ArticleItemVo> userArticle;
    private String commentTitle = "相关评论";
    private List<KankanCommentVo> commentVoList;


    public void addCommentInfo(CommentService commentService, KankanUserService userService) {
        KankanComment comment = KankanComment.builder().resourceId(resourceId).build();
        this.commentVoList = comment.resourceCommentInfo(commentService, userService);
    }


    public void addUserAndArticle(KankanUserService kankanUserService, KankanWorkService workService, MediaResource mediaResource) {
        KankanWork kankanWork = KankanWork.builder().resourceId(resourceId).build();
        KankanWork workInfo = kankanWork.resourceWork(workService);
        KankanUser kankanUser = kankanUserService.findUser(workInfo.getUserId());
        this.userVo = kankanUser.toVo();
        List<KankanWork> workList = workService.findUserWork(kankanUser.getUserId(), 0);
        this.userArticle = workList.stream().map(workItem -> new ArticleItemVo(workInfo, kankanUser, mediaResource)).collect(Collectors.toList());
    }
}
