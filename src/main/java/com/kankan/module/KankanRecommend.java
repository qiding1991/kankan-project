package com.kankan.module;

import com.kankan.service.KankanRecommendService;
import com.kankan.vo.kankan.KankanRecommendVo;

import lombok.Builder;
import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */
@Data
@Builder
public class KankanRecommend {

  private Long id;
  private Long userId;
  private Integer recommendOrder;

  public void add(KankanRecommendService recommendService) {
    if (recommendService.getMyKankanRecommend(userId) == null) {
      recommendService.addRecommend(this);
    }
  }

  public KankanRecommendVo toVo() {
    return KankanRecommendVo.builder().recommendOrder(recommendOrder).userId(userId).build();
  }

  public void del(KankanRecommendService recommendService) {
    recommendService.remove(this);
  }

  public void update(KankanRecommendService recommendService) {
    recommendService.update(this);
  }
}
