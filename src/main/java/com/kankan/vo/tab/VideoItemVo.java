package com.kankan.vo.tab;

import org.apache.commons.lang3.ObjectUtils;

import com.kankan.constant.EnumItemType;
import com.kankan.module.KankanUser;
import com.kankan.module.KankanWork;
import com.kankan.module.MediaResource;

import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-04
 */
@Data
public class VideoItemVo extends TabItemVo {
    //标题
    private String title;

    // 作者名称 | 视频
    private String subtitle;

    //图片地址
    private String picture;

    //视频地址
    private String video;

    //评论总数
    private Integer commentCount;

    //点赞总数
    private Integer thumbsCount;

    public VideoItemVo(KankanWork video, KankanUser writer, MediaResource resource) {
        this.title = video.getTitle();
        this.picture = video.getPicture();
        this.setResourceId(video.getResourceId());
        this.commentCount = ObjectUtils.defaultIfNull(resource.getCommentCount(),0);
        this.thumbsCount =ObjectUtils.defaultIfNull(resource.getThumpCount(),0);
        this.setItemId(video.getId());
        this.setItemType(EnumItemType.VIDEO.getCode());
        this.subtitle = writer.getUserName() + "|" + "视频";
        this.setPublishTime(video.getPublishTime());
    }
}
