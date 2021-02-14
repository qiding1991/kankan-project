package com.kankan.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.kankan.util.GsonUtil;
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
    HotPointEntity hotPointEntity = new HotPointEntity(hotPoint);
    hotPointMapper.insert(hotPointEntity);
    hotPoint.setId(hotPointEntity.getId());
  }

  public List<HotPoint> findHot(TabPageInfo pageInfo) {
    List<HotPointEntity> hotPointEntityList = hotPointMapper.findHotInfo(pageInfo.getOffset(), pageInfo.getSize());
    return hotPointEntityList.stream().map(HotPoint::parseEntity).collect(Collectors.toList());
  }

  public List<HotPoint> listAll() {
    List<HotPointEntity> hotPointEntityList = hotPointMapper.findAll();
    return hotPointEntityList.stream().map(HotPoint::parseEntity).collect(Collectors.toList());
  }


  public List<HotPoint> findByItemType(List<Integer> itemType,Integer limit){
    String types= GsonUtil.toGson(itemType);
    types=types.substring(1,types.length()-1);
    return hotPointMapper.findByItemType(types,limit);
  }



  public HotPoint findHot(int itemType, Long itemId) {
    HotPointEntity hotPointEntity = hotPointMapper.findByItemTypeAndItemId(itemType, itemId);
    if (hotPointEntity != null) {
      return HotPoint.parseEntity(hotPointEntity);
    }
    return null;

  }

  public void delHot(int itemType, Long itemId) {
    hotPointMapper.delByItemTypeAndItemId(itemType, itemId);
  }

}
