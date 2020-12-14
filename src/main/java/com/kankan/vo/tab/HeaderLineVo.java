package com.kankan.vo.tab;

import java.util.List;
import java.util.stream.Collectors;

import com.kankan.constant.EnumItemType;
import com.kankan.module.HeaderLine;
import com.kankan.module.HeaderLineItem;
import com.kankan.param.headline.HeaderLineItemInfo;

import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-04
 */
@Data
public class HeaderLineVo extends TabItemVo {
    private String picture;
    private String title;
    private List<HeaderLineItemVo> itemVoList;
    public HeaderLineVo(HeaderLine headerLine, List<HeaderLineItem> itemList) {
        this.picture = headerLine.getPicture();
        this.title = headerLine.getTitle();
        this.setItemId(headerLine.getId());
        this.itemVoList = itemList.stream().map(HeaderLineItemVo::new).collect(Collectors.toList());
        this.setItemType(EnumItemType.HEADER_LINE_GROUP.getCode());
    }
}
