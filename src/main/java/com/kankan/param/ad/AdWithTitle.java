package com.kankan.param.ad;

import com.kankan.module.KankanAd;
import com.kankan.module.MediaResource;
import com.kankan.service.ResourceService;
import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-03
 */
@Data
public class AdWithTitle {

    private String title;
    private String picture;
    private String content;

    public KankanAd toAd(ResourceService resourceService) {
        MediaResource resource= MediaResource.builder().content(content).title(title).build();
        String resourceId=resourceService.saveResource(resource);
        return KankanAd.builder().picture(picture).resourceId(resourceId).title(title).build();
    }
}
