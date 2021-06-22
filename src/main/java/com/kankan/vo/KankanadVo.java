package com.kankan.vo;

import com.kankan.module.KankanAd;
import com.kankan.service.ResourceService;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class KankanadVo {

    private String id;
    private String resourceId;
    private String title;
    private String picture;
    private String content;

    public KankanadVo(KankanAd ad, ResourceService resourceService) {
        BeanUtils.copyProperties(ad,this);
        this.setContent(resourceService.findResource(resourceId).getContent());
    }
}
