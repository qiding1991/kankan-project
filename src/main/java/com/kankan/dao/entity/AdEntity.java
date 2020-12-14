package com.kankan.dao.entity;

import java.time.Instant;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.kankan.module.KankanAd;
import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-03
 */
@Data
public class AdEntity extends BaseEntity {

    private String resourceId;
    private String title;
    private String picture;

    public KankanAd parse() {
        KankanAd kankanAd = KankanAd.builder().resourceId(resourceId)
                .title(title).picture(picture)
                .id(this.getId())
                .build();
        return kankanAd;
    }
}
