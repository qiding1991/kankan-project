package com.kankan.dao.mapper;

import com.kankan.dao.entity.WorkWhiteEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class WorkWhiteListMapperImpl implements WorkWhiteListMapper {

  @Autowired
  private MongoTemplate mongoTemplate;

  private final  Class<WorkWhiteEntity> entityClass = WorkWhiteEntity.class;


  @Override
  public void insert(WorkWhiteEntity workWhiteEntity) {
    mongoTemplate.insert(workWhiteEntity);
  }

  @Override
  public void delete(String userId) {
    Query query = Query.query(Criteria.where("userId").is(userId));
    mongoTemplate.remove(query, entityClass);
  }

  @Override
  public WorkWhiteEntity findOne(String userId) {
    Query query = Query.query(Criteria.where("userId").is(userId));
    return mongoTemplate.findOne(query, entityClass);
  }
}
