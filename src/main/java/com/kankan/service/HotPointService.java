package com.kankan.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kankan.dao.entity.HotPointEntity;
import com.kankan.dao.mapper.HotPointMapper;
import com.kankan.module.HotPoint;
import com.kankan.param.tab.TabPageInfo;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-04
 */
@Service
public class HotPointService {

    @Resource
    private HotPointMapper hotPointMapper;

    public void createHotPoint(HotPoint hotPoint) {
        HotPointEntity hotPointEntity=new HotPointEntity(hotPoint);
        hotPointMapper.insert(hotPointEntity);
    }

    public List<HotPoint> findHot(TabPageInfo pageInfo) {
        List<HotPointEntity> hotPointEntityList= hotPointMapper.findHotInfo(pageInfo.getOffset(),pageInfo.getSize());
        return hotPointEntityList.stream().map(HotPoint::parseEntity).collect(Collectors.toList());
    }

    public List<HotPoint> listAll() {
        List<HotPointEntity> hotPointEntityList=hotPointMapper.findAll();
        return hotPointEntityList.stream().map(HotPoint::parseEntity).collect(Collectors.toList());
    }

  public HotPoint findHot(int itemType, Long itemId) {
         HotPointEntity hotPointEntity=hotPointMapper.findByItemTypeAndItemId(itemType,itemId);
         return HotPoint.parseEntity(hotPointEntity);
    }

    public void delHot(int itemType, Long itemId){
      hotPointMapper.delByItemTypeAndItemId(itemType, itemId);
    }

}
