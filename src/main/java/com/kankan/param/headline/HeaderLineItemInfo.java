package com.kankan.param.headline;

import com.kankan.module.HeaderLineItem;
import lombok.Data;

@Data
public class HeaderLineItemInfo {
    private Long headerLineId;
    private String resourceId;
    public HeaderLineItem toHeadline() {
        return HeaderLineItem.builder().resourceId(resourceId).headerLineId(headerLineId).build();
    }
}
