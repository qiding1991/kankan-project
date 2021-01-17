package com.kankan.module;

import com.kankan.dao.entity.HotPointEntity;
import com.kankan.service.HotPointService;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-04
 */
@Builder
@Data
public class HotPoint {

  private Long id;
  private Long itemId;
  private Integer itemType;
  private Integer itemOrder;

  public void create(HotPointService hotPointService) {
    hotPointService.createHotPoint(this);
  }

  public static HotPoint parseEntity(HotPointEntity hotPointEntity) {
    return HotPoint.builder()
      .id(hotPointEntity.getId())
      .itemId(hotPointEntity.getItemId())
      .itemType(hotPointEntity.getItemType())
      .build();
  }


  public List<HotPoint> listAll(HotPointService hotPointService) {
    return hotPointService.listAll();
  }

  public void delHotInfo(HotPointService hotPointService) {
    hotPointService.delHot(itemType, itemId);
  }
}
