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
  private String commentId;
  private String userId;

  public void save(ThumpService thumpService) {
    thumpService.saveThump(this);
  }

  public void cancel(ThumpService thumpService) {
    thumpService.cancelThump(this);
  }
}
