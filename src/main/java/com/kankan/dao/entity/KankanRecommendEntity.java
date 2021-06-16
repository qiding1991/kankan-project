package com.kankan.dao.entity;

import org.springframework.beans.BeanUtils;

import com.kankan.module.KankanRecommend;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */
@NoArgsConstructor
@Data
public class KankanRecommendEntity extends BaseEntity{
    private String userId;
    private Integer recommendOrder;

    public KankanRecommendEntity(KankanRecommend kankanRecommend) {
        BeanUtils.copyProperties(kankanRecommend,this);
    }

    public KankanRecommend toKankanRecommend() {
         return KankanRecommend.builder().userId(userId).recommendOrder(recommendOrder).build();
    }
}
