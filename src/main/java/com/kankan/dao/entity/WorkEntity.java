package com.kankan.dao.entity;

import java.time.Instant;

import com.kankan.module.KankanWork;

import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-03
 */
@Data
public class WorkEntity extends BaseEntity {

    private String resourceId;
    private Long userId;
    private Integer type;
    private String title;
    private String picture;

    public KankanWork parse() {
        return KankanWork.builder().id(this.getId()).type(type).userId(userId).resourceId(resourceId).title(title).picture(picture)
                .publishTime(this.getCreateTime()).build();

    }
}
