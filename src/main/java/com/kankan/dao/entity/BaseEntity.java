package com.kankan.dao.entity;

import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import java.time.Instant;
import org.springframework.data.annotation.Id;

@Data
public class BaseEntity {

  @Id
  private String id;
  private Integer status = 1;
  private Long createTime = Instant.now().toEpochMilli();
  private Long updateTime = Instant.now().toEpochMilli();


  public void setCreateTime(Long createTime) {
    this.createTime = ObjectUtils.defaultIfNull(createTime, Instant.now().toEpochMilli());
  }

  public void setUpdateTime(Long updateTime) {
    this.updateTime = ObjectUtils.defaultIfNull(updateTime, Instant.now().toEpochMilli());
  }

  public Long getCreateTime() {
    return ObjectUtils.defaultIfNull(createTime, Instant.now().toEpochMilli());
  }

  public Long getUpdateTime() {
    return ObjectUtils.defaultIfNull(updateTime, Instant.now().toEpochMilli());
  }
}
