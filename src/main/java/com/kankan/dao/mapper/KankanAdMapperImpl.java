package com.kankan.dao.mapper;

import com.kankan.dao.entity.AdEntity;
import com.kankan.dao.entity.KankanRecommendEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class KankanAdMapperImpl implements KankanAdMapper {

  @Autowired
  private MongoTemplate mongoTemplate;
  private Class<AdEntity> myClass = AdEntity.class;


  @Override
  public void insert(AdEntity adEntity) {
    mongoTemplate.insert(adEntity);
  }

  @Override
  public AdEntity findById(String id) {
     return mongoTemplate.findById(id,myClass);
  }

  @Override
  public List<AdEntity> findAll() {
    return mongoTemplate.findAll(myClass);
  }
}
