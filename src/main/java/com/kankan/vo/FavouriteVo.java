package com.kankan.vo;

import org.springframework.beans.BeanUtils;

import com.kankan.module.resouce.Favourite;

import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */
@Data
public class FavouriteVo {
    private Long id;
    private String resourceId;

    public FavouriteVo(Favourite favourite) {
        BeanUtils.copyProperties(favourite,this);
    }
}
