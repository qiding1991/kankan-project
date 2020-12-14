package com.kankan.param.resource;

import com.kankan.module.MediaResource;
import com.kankan.module.resouce.ResourceThump;

import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
@Data
public class ThumpParam {
    private Long userId;
    private String resourceId;

    public ResourceThump toThump() {
        return toThump(0L);
    }

    public ResourceThump toThump(Long commentId) {
        return ResourceThump.builder().userId(userId).resourceId(resourceId).commentId(commentId).build();
    }

    public MediaResource toResource() {
       return MediaResource.builder().resourceId(resourceId).build();
    }
}
