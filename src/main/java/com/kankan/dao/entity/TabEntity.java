package com.kankan.dao.entity;

import java.time.Instant;

import com.kankan.module.Tab;

import lombok.Data;
import org.springframework.data.annotation.Id;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-02
 */
@Data
public class TabEntity extends BaseEntity {

    private String tabName;
    private Integer tabType;
    private Integer tabOrder;


    public Tab tab() {
        return Tab.builder().tabId(getId()).tabName(tabName).tabType(tabType).tabOrder(tabOrder).build();
    }
}
