package com.kankan.module;

import com.kankan.service.KankanAdService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KankanAd {

    private String id;
    private String resourceId;
    private String title;
    private String picture;

    public void create(KankanAdService kankanAdService) {
           kankanAdService.createAd(this);
    }

    public List<KankanAd> findAll(KankanAdService kankanAdService) {
          return kankanAdService.findAll();
    }
}
