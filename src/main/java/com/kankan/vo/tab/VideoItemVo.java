package com.kankan.vo.tab;

import com.kankan.dao.mapper.ThumpMapper;
import com.kankan.service.ThumpService;
import org.apache.commons.lang3.ObjectUtils;

import com.kankan.constant.EnumItemType;
import com.kankan.module.KankanUser;
import com.kankan.module.KankanWork;
import com.kankan.module.MediaResource;

import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.Collections;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-04
 */
@Data
public class VideoItemVo extends TabItemVo {
  //标题
  private String title;

  // 作者名称 | 视频
  private String subTitle;

  //图片地址
  private String picture;

  //视频地址
  private String videoUrl;

  //评论总数
  private Integer commentCount;

  //点赞总数
  private Integer thumbsCount;

  private Integer auditStatus;

  private Boolean thumbStatus;

  public VideoItemVo(KankanWork video, KankanUser writer, MediaResource resource) {
    this.title = video.getTitle();
    this.picture = video.getPicture();
    this.videoUrl = video.getVideoUrl();
    this.setResourceId(video.getResourceId());
    this.commentCount = ObjectUtils.defaultIfNull(resource.getCommentCount(), 0);
    this.thumbsCount = ObjectUtils.defaultIfNull(resource.getThumpCount(), 0);
    this.setItemId(video.getId());
    this.setItemType(EnumItemType.VIDEO.getCode());
    this.subTitle = writer.getUserName() + "|" + "视频";
    this.setPublishTime(video.getPublishTime());
  }

  public void addThumbStatus(String userId, String resourceId, ThumpMapper thumpMapper) {
    this.thumbStatus = !CollectionUtils.isEmpty(thumpMapper.findByResourceId(resourceId, userId));
  }
}

