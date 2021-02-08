package com.kankan.service;

import com.kankan.dao.mapper.KankanWorkTypeMapper;
import com.kankan.module.KankanWorkTypeEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class KankanWorkTypeService {

  @Resource
  private KankanWorkTypeMapper kankanWorkTypeMapper;

  public KankanWorkTypeEntity addKankanWorkType(KankanWorkTypeEntity kankanWorkTypeEntity){
     kankanWorkTypeMapper.insert(kankanWorkTypeEntity);
     return kankanWorkTypeEntity;
  }

  public void delKankanWorkType(Long id){
     kankanWorkTypeMapper.removeKankanWorkType(id);
  }
  public void updateKankanWorkType(Long id,String desc){
    kankanWorkTypeMapper.updateWorkType(id,desc);
  }


  public List<KankanWorkTypeEntity> findAll(){
     return kankanWorkTypeMapper.findAll();
  }

}
