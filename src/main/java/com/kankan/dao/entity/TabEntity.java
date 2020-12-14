package com.kankan.dao.entity;

import java.time.Instant;

import com.kankan.module.Tab;

import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-02
 */
@Data
public class TabEntity {
    private Long id;
    private String tabName;
    private Integer tabType;
    private Integer tabOrder;
    private Integer status = 1;
    private Long createTime = Instant.now().toEpochMilli();
    private Long updateTime = Instant.now().toEpochMilli();

    public Tab tab() {
        return Tab.builder().tabId(id).tabName(tabName).tabType(tabType).tabOrder(tabOrder).build();
    }
}
