package com.kankan.vo.detail;

import com.kankan.module.*;
import com.kankan.param.tab.TabPageInfo;
import com.kankan.service.*;
import com.kankan.vo.KankanCommentVo;
import com.kankan.vo.KankanUserVo;
import com.kankan.vo.tab.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class VideoDetailVo {
    private String resourceId;
    private KankanUserVo userVo;
    private String content;
    private String videoTitle = "相关视频";
    private List<VideoItemVo> relateVideos;
    private String commentTitle = "相关评论";
    private List<KankanCommentVo> commentVoList;

    public void addBaseInfo(){
        this.videoTitle="相关视频";
        this.commentTitle="相关评论";
    }

    public void addCommentInfo(CommentService commentService, KankanUserService userService) {
        KankanComment comment = KankanComment.builder().resourceId(resourceId).build();
        this.commentVoList = comment.resourceCommentInfo(commentService, userService);
    }

    public void addRelatedVideos(MediaResource mediaResource, ResourceService resourceService, KankanUserService kankanUserService, KankanWorkService workService) {
        List<MediaResource> mediaResourceList =  resourceService.findRelatedResource(mediaResource);
        this.relateVideos=relatedVideos(mediaResourceList,workService,kankanUserService,resourceService);
    }

    /**
     * 相关信息
     * @param mediaResourceList
     * @param resourceService
     * @return
     */
    private List<VideoItemVo> relatedVideos(List<MediaResource> mediaResourceList, KankanWorkService workService,KankanUserService userService, ResourceService resourceService) {
        List<KankanWork> workList = mediaResourceList.stream().map(mediaResource -> workService.resourceWork(mediaResource.getResourceId()))
                .collect(Collectors.toList());
        return workList.stream().map(work -> work.toVideoItemVo(userService,resourceService)).collect(Collectors.toList());

    }

    public void addUserVo(KankanUserService kankanUserService,KankanWorkService workService) {
        KankanWork kankanWork = KankanWork.builder().resourceId(resourceId).build();
        KankanWork workInfo = kankanWork.resourceWork(workService);
        KankanUser kankanUser = kankanUserService.findUser(workInfo.getUserId());
        this.userVo = kankanUser.toVo();
    }
}
