package com.kankan.param.work;

import java.util.List;

import com.kankan.module.KankanWork;
import com.kankan.module.MediaResource;
import com.kankan.service.ResourceService;

import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-03
 */
@Data
public class WorkAddInfo {
    private Long userId;
    private String picture;
    private String title;
    private Integer type;
    private String content;
    private List<String> keyword;

    public KankanWork toWork(ResourceService resourceService) {
        MediaResource resource =
                MediaResource.builder().title(title).content(content).mediaType(type).keyWords(keyword).build();
        String resourceId = resourceService.saveResource(resource);
        return KankanWork.builder().userId(userId).picture(picture).type(type).title(title).resourceId(resourceId)
                .build();
    }
}
