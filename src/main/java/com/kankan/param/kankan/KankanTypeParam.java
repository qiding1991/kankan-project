package com.kankan.param.kankan;

import com.kankan.module.KankanType;

import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */
@Data
public class KankanTypeParam {

    private String typeDesc;
    private Integer typeOrder;

    public KankanType toType() {
        return KankanType.builder().typeDesc(typeDesc).typeOrder(typeOrder).build();
    }
}
