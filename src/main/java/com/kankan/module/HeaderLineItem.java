package com.kankan.module;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;

import com.kankan.dao.entity.HeaderLineItemEntity;
import com.kankan.service.HeaderLineService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-05
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeaderLineItem {
    private String resourceId;
    private Long headerLineId;
    private String title;
    private Integer itemType;
    private Long id;

    public void creatHeadItem(HeaderLineService headerLineService) {
        headerLineService.createHeadItem(this);
    }

    public static List<HeaderLineItem> parseEntity(List<HeaderLineItemEntity> entityList) {
        return entityList.stream().map(HeaderLineItem::parseEntity).collect(Collectors.toList());
    }

    public static HeaderLineItem parseEntity(HeaderLineItemEntity entity) {
        HeaderLineItem headerLineItem = new HeaderLineItem();
        BeanUtils.copyProperties(entity, headerLineItem);
        return headerLineItem;

    }

    public List<HeaderLineItem> findHeadItemList(HeaderLineService headerLineService) {
        return headerLineService.findHeaderLineItem(this.headerLineId);
    }
}
