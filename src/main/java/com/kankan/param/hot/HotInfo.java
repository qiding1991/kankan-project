package com.kankan.param.hot;

import com.kankan.module.HotPoint;

import io.swagger.models.auth.In;
import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-04
 */
@Data
public class HotInfo {
    private String itemId;
    private Integer itemType;
    private Integer itemOrder;

    public HotPoint toHotPoint() {
        return HotPoint.builder().itemId(itemId).itemOrder(itemOrder).itemType(itemType).build();
    }
}
