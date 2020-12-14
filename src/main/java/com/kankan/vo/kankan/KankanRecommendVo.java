package com.kankan.vo.kankan;

import lombok.Builder;
import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */
@Builder
@Data
public class KankanRecommendVo {
    private Long userId;
    private Integer recommendOrder;
}
