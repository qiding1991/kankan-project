package com.kankan.dao.entity;

import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import java.time.Instant;

@Data
public class BaseEntity {
    private Long id;
    private Integer status = 1;
    private Long createTime = Instant.now().toEpochMilli();
    private Long updateTime = Instant.now().toEpochMilli();

    public Long getCreateTime(){
        return ObjectUtils.defaultIfNull(createTime,Instant.now().toEpochMilli());
    }


    public Long getUpdateTime() {
        return ObjectUtils.defaultIfNull(updateTime,Instant.now().toEpochMilli());
    }
}
