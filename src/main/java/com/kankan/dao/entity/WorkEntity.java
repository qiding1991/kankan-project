package com.kankan.dao.entity;

import com.kankan.module.KankanWork;
import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-03
 */
@Data
public class WorkEntity extends BaseEntity {

  private String resourceId;
  private String userId;
  private Integer type;
  private String title;
  private String picture;
  private String videoUrl;
  private Integer hotStatus;//是否设置称热点
  private Integer headStatus;
  private Integer auditStatus;

  public KankanWork parse() {
    return KankanWork.builder().id(this.getId()).type(type)
      .videoUrl(videoUrl)
      .auditStatus(this.auditStatus)
      .headStatus(this.headStatus)
      .hotStatus(this.hotStatus)
      .userId(userId).resourceId(resourceId).title(title).picture(picture)
      .publishTime(this.getCreateTime()).build();
  }
}
