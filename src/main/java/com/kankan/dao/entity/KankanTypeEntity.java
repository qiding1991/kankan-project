package com.kankan.dao.entity;

import com.kankan.module.KankanType;
import com.kankan.module.KankanWork;

import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */
@Data
public class KankanTypeEntity extends BaseEntity {
    private String typeDesc;
    private Integer typeOrder;

    public KankanType parse() {
        return KankanType.builder().id(this.getId()).typeOrder(this.typeOrder)
                .typeDesc(this.typeDesc).build();

    }
}
