package com.kankan.param.news;

import java.util.List;

import com.kankan.constant.EnumItemType;
import com.kankan.module.MediaResource;
import com.kankan.module.News;
import com.kankan.service.ResourceService;

import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-03
 */
@Data
public class NewsAddInfo {
    private Long tabId;
    private String picture;
    private String title;
    private String content;
    private Long userId;
    private List<String> keyword;


    public News toNews(ResourceService resourceService) {
        MediaResource resource = MediaResource.builder().content(content).title(title).mediaType(EnumItemType.NEWS.getCode()).keyWords(keyword).build();
        String resourceId = resourceService.saveResource(resource);
        return News.builder().picture(picture).userId(userId).resourceId(resourceId).title(title).tabId(tabId).build();
    }
}
