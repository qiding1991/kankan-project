package com.kankan.service;

import com.kankan.dao.entity.WorkWhiteEntity;
import com.kankan.dao.mapper.WorkWhiteListMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
public class WorkWhiteService {
  @Resource
  private WorkWhiteListMapper workWhiteListMapper;

  public String addWorkWhite(String userId) {
    WorkWhiteEntity workWhiteEntity= workWhiteListMapper.findOne(userId);
    if(workWhiteEntity==null){
      workWhiteEntity = new WorkWhiteEntity();
      workWhiteEntity.setUserId(userId);
      workWhiteListMapper.insert(workWhiteEntity);
    }
    return workWhiteEntity.getId();
  }

  public void delWorkWhite(String userId){
     workWhiteListMapper.delete(userId);
  }

  public Boolean isWhite(String userId){
     WorkWhiteEntity workWhiteEntity= workWhiteListMapper.findOne(userId);
     return StringUtils.isEmpty(workWhiteEntity);
  }
}
