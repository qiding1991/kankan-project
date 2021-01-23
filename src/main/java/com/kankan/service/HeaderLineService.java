package com.kankan.service;

import java.util.List;
import java.util.stream.Collectors;

import com.kankan.dao.entity.HeaderLineItemEntity;
import com.kankan.dao.entity.HeaderLineInfoEntity;
import com.kankan.dao.mapper.HeaderLineInfoMapper;
import com.kankan.dao.mapper.HeaderLineItemMapper;
import com.kankan.module.HeaderLineItem;
import com.kankan.module.HeaderLine;
import com.kankan.param.headline.HeaderLineItemInfo;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-03
 */
@Service
public class HeaderLineService {

  @Resource
  HeaderLineInfoMapper headerLineInfoMapper;

  @Resource
  HeaderLineItemMapper headerLineItemMapper;

  public void createHeadLine(HeaderLine headerLine) {
    HeaderLineInfoEntity headerLineInfoEntity = new HeaderLineInfoEntity(headerLine);
    headerLineInfoMapper.insert(headerLineInfoEntity);
  }

  public void createHeadItem(HeaderLineItem headerLineItem) {
    HeaderLineItemEntity itemEntity = new HeaderLineItemEntity(headerLineItem);
    headerLineItemMapper.insert(itemEntity);
  }

  public HeaderLine findHeaderLineInfo(Long tabId) {
    HeaderLineInfoEntity entity = headerLineInfoMapper.findHeaderLineInfo(tabId);
    if(entity!=null){
      return HeaderLine.parseEntity(entity);
    }
    return  null;
  }

  public List<HeaderLineItem> findHeaderLineItem(Long headerLineId) {
    List<HeaderLineItemEntity> entity = headerLineItemMapper.findHeaderLineItem(headerLineId);
    return HeaderLineItem.parseEntity(entity);
  }

  public HeaderLine findHeaderLineById(Long itemId) {
    return headerLineInfoMapper.findHeaderLineById(itemId);
  }

  public List<HeaderLine> findHeaderLine() {
    List<HeaderLineInfoEntity> infoList = headerLineInfoMapper.findHeaderLine();
    return infoList.stream().map(HeaderLineInfoEntity::parse).collect(Collectors.toList());
  }

  public HeaderLineItem findHeaderLineItem(String resourceId) {
    HeaderLineItemEntity headerLineItemEntity = headerLineItemMapper.findByResourceId(resourceId);
    if(headerLineItemEntity!=null){

      return HeaderLineItem.parseEntity(headerLineItemEntity);
    }
    return null;
  }

    public void delHeadItem(HeaderLineItem item) {
      headerLineItemMapper.delByHeadLineIdAndResourceId(item.getHeaderLineId(),item.getResourceId());
    }
}
