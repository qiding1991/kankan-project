package com.kankan.param.kankan;

import com.kankan.module.KankanRecommend;

import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */
@Data
public class KankanRecommendParam {
    private String userId;
    private Integer recommendOrder;
    public KankanRecommend toRecommend() {
      return KankanRecommend.builder().recommendOrder(recommendOrder).userId(userId).build();
    }
}
