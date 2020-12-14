package com.kankan.module.resouce;

import com.kankan.service.ThumpService;

import lombok.Builder;
import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
@Builder
@Data
public class ResourceThump {
    private String resourceId;
    private Long commentId;
    private Long userId;
    public void save(ThumpService thumpService) {
         thumpService.saveThump(this);
    }
}
