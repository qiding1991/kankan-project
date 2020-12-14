package com.kankan.dao.entity;

import org.springframework.beans.BeanUtils;

import com.kankan.module.HotPoint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotPointEntity extends BaseEntity {

    private Long itemId;
    private Integer itemType;
    private Integer itemOrder;

    public HotPointEntity(HotPoint hotPoint) {
        BeanUtils.copyProperties(hotPoint, this);
    }
}
