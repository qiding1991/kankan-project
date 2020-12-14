package com.kankan.dao.entity;

import com.kankan.module.resouce.Favourite;

import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
@Data
public class FavouriteEntity extends BaseEntity {
    private String resourceId;
    private Long userId;

    public Favourite parse() {
        return Favourite.builder().resourceId(resourceId).userId(userId).id(this.getId()).build();
    }
}
