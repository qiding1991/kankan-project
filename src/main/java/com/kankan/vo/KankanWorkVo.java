package com.kankan.vo;

import com.kankan.constant.EnumItemType;
import com.kankan.module.HeaderLineItem;
import com.kankan.module.HotPoint;
import com.kankan.module.KankanWork;
import com.kankan.module.MediaResource;
import com.kankan.service.HeaderLineService;
import com.kankan.service.HotPointService;
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
  private Integer status;
  private Integer isHot = 0;
  private Integer isHeaderLine = 0;

  private Integer hotStatus;//是否设置称热点
  private Integer headStatus;
  private Integer auditStatus;



  public KankanWorkVo(KankanWork kankanWork) {
    BeanUtils.copyProperties(kankanWork, this);
  }

  public void addResource(ResourceService resourceService) {
    MediaResource resource = resourceService.findResource(resourceId);
    this.content = resource.getContent();
    this.keyword = resource.getKeyWords();
  }

  public HotPoint addHotStatus(HotPointService hotPointService) {
    HotPoint hotPoint = hotPointService.findHot(EnumItemType.ARTICLE.getCode(), id);
    if (hotPoint != null) {
      this.isHot = 1;
    }
    return hotPoint;
  }

  public HeaderLineItem addHeadLine(HeaderLineService headerLineService) {
    HeaderLineItem headerLineItem = headerLineService.findHeaderLineItem(resourceId);
    if (headerLineItem != null) {
      this.isHeaderLine = 1;
    }
    return headerLineItem;
  }
}
