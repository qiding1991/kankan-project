package com.kankan.dao.entity;

import com.kankan.module.HeaderLineItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HeaderLineItemEntity extends BaseEntity {
    private String headerLineId;
    private String resourceId;
    public HeaderLineItemEntity(HeaderLineItem headerLine) {
        BeanUtils.copyProperties(headerLine,this);
    }
}
