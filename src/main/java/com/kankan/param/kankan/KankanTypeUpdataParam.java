package com.kankan.param.kankan;

import com.kankan.module.KankanType;
import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */
@Data
public class KankanTypeUpdataParam {
    private Long id;
    private String typeDesc;
    private Integer typeOrder;

    public KankanType toType() {
        return KankanType.builder().id(id).typeDesc(typeDesc).typeOrder(typeOrder).build();
    }
}
