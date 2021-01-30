package com.kankan.vo.detail;

import com.kankan.dao.mapper.ThumpMapper;
import com.kankan.module.*;
import com.kankan.service.CommentService;
import com.kankan.service.KankanUserService;
import com.kankan.service.KankanWorkService;
import com.kankan.service.UserService;
import com.kankan.vo.KankanCommentVo;
import com.kankan.vo.KankanUserVo;
import com.kankan.vo.tab.AdItemVo;
import com.kankan.vo.tab.ArticleItemVo;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
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
  private Boolean favouriteStatus;

  public void addBaseInfo(MediaResource resource){
        this.newsTitle="作者其它文章";
        this.commentTitle="相关评论";
        this.content=resource.getContent();
    }


    public void addCommentInfo(CommentService commentService, UserService userService) {
        KankanComment comment = KankanComment.builder().resourceId(resourceId).build();
        this.commentVoList = comment.resourceCommentInfo(commentService, userService);
    }


    public void addUserAndArticle(KankanUserService kankanUserService, KankanWorkService workService, MediaResource mediaResource) {
        KankanWork kankanWork = KankanWork.builder().resourceId(resourceId).build();
        KankanWork workInfo = kankanWork.resourceWork(workService);
        KankanUser kankanUser = kankanUserService.findUser(workInfo.getUserId());
        this.userVo = kankanUser.toVo();
        List<KankanWork> workList = workService.findUserWork(kankanUser.getUserId(), 0);
        this.userArticle = workList.stream().filter(work -> !work.getId().equals(workInfo.getId())).map(workItem -> new ArticleItemVo(workInfo, kankanUser, mediaResource)).collect(Collectors.toList());
    }

  public void setFavouriteStatus(Boolean favouriteStatus) {
        this.favouriteStatus=favouriteStatus;
  }

  public void  addThumpStatus(Long userId, ThumpMapper thumpMapper){
    KankanCommentVo.addThumpStatus(this.commentVoList,userId, thumpMapper);
  }


}
