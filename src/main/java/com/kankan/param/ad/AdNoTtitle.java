package com.kankan.param.ad;

import com.kankan.constant.EnumItemType;
import com.kankan.module.KankanAd;
import com.kankan.module.MediaResource;
import com.kankan.service.ResourceService;

import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-03
 */
@Data
public class AdNoTtitle {
    private String picture;
    private String content;

    public KankanAd toAd(ResourceService resourceService) {
        MediaResource resource =
                MediaResource.builder().content(content).mediaType(EnumItemType.AD.getCode()).build();
        String resourceId= resourceService.saveResource(resource);
        return KankanAd.builder().picture(picture).resourceId(resourceId).title("").build();
    }
}
