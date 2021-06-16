package com.kankan.module;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.BeanUtils;

import com.kankan.dao.entity.WorkEntity;
import com.kankan.service.KankanUserService;
import com.kankan.service.KankanWorkService;
import com.kankan.service.ResourceService;
import com.kankan.vo.KankanWorkVo;
import com.kankan.vo.tab.ArticleItemVo;
import com.kankan.vo.tab.VideoItemVo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KankanWork {
  private String id;
  private String userId;
  private String picture;
  private String videoUrl;
  private String title;
  private String resourceId;
  private Integer type;
  private Long publishTime;
  private Integer hotStatus;//是否设置称热点
  private Integer headStatus;
  private Integer auditStatus;


  public static KankanWork fromResourceId(String resourceId, KankanWorkService workService) {
    KankanWork kankanWork = workService.findByResourceId(resourceId);
    return kankanWork;
  }

  public void addWork(KankanWorkService workService) {
    workService.addWork(this);
  }

  public static KankanWork parseEntity(WorkEntity workEntity) {
    KankanWork kankanWork = new KankanWork();
    BeanUtils.copyProperties(workEntity, kankanWork);
    kankanWork.publishTime = workEntity.getCreateTime();
    return kankanWork;
  }

  public ArticleItemVo toArticleItemVo(KankanUserService kankanUserService,
                                       ResourceService resourceService) {
    KankanUser user = kankanUserService.findUser(userId);
    MediaResource resource = resourceService.findResource(resourceId);
    return new ArticleItemVo(this, user, resource);
  }

  public VideoItemVo toVideoItemVo(KankanUserService kankanUserService, ResourceService resourceService) {
    KankanUser user = kankanUserService.findUser(userId);
    MediaResource resource = resourceService.findResource(resourceId);
    return new VideoItemVo(this, user, resource);
  }

  public List<KankanWork> findMyWork(KankanWorkService workService) {
    return workService.findUserWork(this.userId);
  }

  public List<KankanWork> findAllWork(KankanWorkService workService) {
    return workService.findAllWork();
  }

  public KankanWork resourceWork(KankanWorkService workService) {
    return workService.resourceWork(resourceId);
  }
}
