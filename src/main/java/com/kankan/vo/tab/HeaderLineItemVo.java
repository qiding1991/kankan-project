package com.kankan.vo.tab;

import com.kankan.module.HeaderLineItem;
import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-04
 */
@Data
public class HeaderLineItemVo extends TabItemVo {
    private String title;
    private String resourceId;

    public HeaderLineItemVo(HeaderLineItem headerLineItem) {
        this.title = headerLineItem.getTitle();
        this.resourceId=headerLineItem.getResourceId();
        this.setItemId(headerLineItem.getId());
        this.setItemType(headerLineItem.getItemType());
    }
}
