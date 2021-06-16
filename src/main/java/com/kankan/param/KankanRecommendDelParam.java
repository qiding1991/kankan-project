package com.kankan.param;

import com.kankan.module.KankanRecommend;

import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */
@Data
public class KankanRecommendDelParam {
    private String userId;
    public KankanRecommend toRecommend() {
        return KankanRecommend.builder().userId(userId).build();
    }
}
