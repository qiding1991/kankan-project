package com.kankan.param.headline;

import java.util.List;

import com.kankan.module.HeaderLineItem;
import com.kankan.module.MediaResource;
import com.kankan.service.ResourceService;

import lombok.Data;

@Data
public class HeaderLineItemInfo {

    private Long headerLineId;
    private String title;
    private String content;
    private List<String> keyword;

    public HeaderLineItem toHeadline(ResourceService resourceService) {
        MediaResource resource = MediaResource.builder().title(title).content(content).keyWords(keyword).build();
        String mediaId = resourceService.saveResource(resource);
        return HeaderLineItem.builder().title(title)
                .resourceId(mediaId)
                .headerLineId(headerLineId)
                .build();
    }
}
