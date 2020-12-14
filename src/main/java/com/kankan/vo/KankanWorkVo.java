package com.kankan.vo;

import com.kankan.module.KankanWork;
import com.kankan.module.MediaResource;
import com.kankan.service.ResourceService;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-09
 */
@Data
public class KankanWorkVo {

    private Long id;
    private Long userId;
    private String picture;
    private String title;
    private String resourceId;
    private Integer type;
    private Long publishTime;
    private String content;
    private List<String> keyword;

    public KankanWorkVo(KankanWork kankanWork) {
        BeanUtils.copyProperties(kankanWork,this);
    }

    public void addResource(ResourceService resourceService) {
          MediaResource resource= resourceService.findResource(resourceId);
          this.content=resource.getContent();
          this.keyword=resource.getKeyWords();
    }
}
