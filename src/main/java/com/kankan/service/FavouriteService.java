package com.kankan.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.kankan.dao.entity.FavouriteEntity;
import com.kankan.dao.mapper.FavouriteMapper;
import com.kankan.module.resouce.Favourite;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
@Service
public class FavouriteService {

    @Resource
    private FavouriteMapper favouriteMapper;

    public void createFavourite(Favourite favourite) {
        FavouriteEntity entity=new FavouriteEntity();
        BeanUtils.copyProperties(favourite,entity);
        favouriteMapper.insert(entity);
    }

    public List<Favourite> findFavourite(Favourite favourite) {
       List<FavouriteEntity>  favouriteEntityList=favouriteMapper.findUserFavourite(favourite.getUserId(),favourite.getPageQuery().getOffset(),favourite.getPageQuery().getSize());
       return favouriteEntityList.stream().map(FavouriteEntity::parse).collect(Collectors.toList());
    }

    public void remove(Favourite favourite) {
          favouriteMapper.remove(favourite.getUserId(),favourite.getResourceId());
    }

    public FavouriteEntity findFavourite(Long userId,String resourceId){
      return favouriteMapper.findByUserIdAndResourceId(userId,resourceId);
    }

}
