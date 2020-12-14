package com.kankan.module.resouce;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.beans.BeanUtils;

import com.kankan.module.PageQuery;
import com.kankan.service.FavouriteService;
import com.kankan.vo.FavouriteVo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Favourite {

    private String resourceId;
    private Long userId;
    private Long id;
    private PageQuery pageQuery;


    public void save(FavouriteService favouriteService) {
        favouriteService.createFavourite(this);
    }

    public List<Favourite> list(FavouriteService favouriteService) {
        return favouriteService.findFavourite(this);
    }

    public FavouriteVo toVo() {
        return new FavouriteVo(this);

    }

    public static Favourite fromId(Long id) {
         return Favourite.builder().id(id).build();
    }

    public void remove(FavouriteService favouriteService) {
        favouriteService.remove(this);
    }
}
