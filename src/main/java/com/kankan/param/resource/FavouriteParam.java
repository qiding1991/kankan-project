package com.kankan.param.resource;

import com.kankan.module.resouce.Favourite;

import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
@Data
public class FavouriteParam {
    private Long userId;
    private String resourceId;

    public Favourite toFavourite() {
        return Favourite.builder().userId(userId).resourceId(resourceId).build();
    }
}
