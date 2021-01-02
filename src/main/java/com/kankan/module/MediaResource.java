package com.kankan.module;

import java.util.List;
import java.util.Map;

import com.kankan.constant.EnumItemType;
import com.kankan.service.CommentService;

import com.kankan.service.KankanUserService;
import com.kankan.vo.tab.TabItemVo;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.kankan.service.ResourceService;

import lombok.Builder;
import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
@Builder
@Data
@Document("media-resource")
public class MediaResource {
  @Id
  private String resourceId;
  private Integer mediaType;
  private Map<String, String> extendInfo;
  private List<String> keyWords;
  private String content;
  private String title;
  private Integer readCount;
  private Integer commentCount;
  private Integer thumpCount;

  public void incrementCommentCount(ResourceService resourceService) {
    resourceService.incrementCommentCount(this);
  }
  public void decrCommentCount(ResourceService resourceService) {
    resourceService.decrCommentCount(this);
  }


  public void incrementReadCount(ResourceService resourceService) {
    resourceService.incrementReadCount(this);
  }

  public void incrementThumpCount(ResourceService resourceService) {
    resourceService.incrementThumpCount(this);
  }

  public void decreaseThumpCount(ResourceService resourceService){
    resourceService.decreaseThumpCount(this);
  }

  public List<MediaResource> findRelated(ResourceService resourceService) {
    MediaResource mediaResource = resourceService.findResource(resourceId);
    return resourceService.findRelatedResource(mediaResource);
  }


  public List<KankanComment> allComment(CommentService commentService) {
    List<KankanComment> kankanCommentList = commentService.findResourceComment(this.getResourceId());
    return kankanCommentList;
  }

}
