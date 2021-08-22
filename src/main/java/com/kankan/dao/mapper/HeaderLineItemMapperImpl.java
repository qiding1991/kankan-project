package com.kankan.dao.mapper;

import com.kankan.dao.entity.HeaderLineItemEntity;
import com.kankan.dao.entity.HotPointEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class HeaderLineItemMapperImpl implements HeaderLineItemMapper {

  @Autowired
  private MongoTemplate mongoTemplate;
  private Class<HeaderLineItemEntity> myClass = HeaderLineItemEntity.class;


  @Override
  public void insert(HeaderLineItemEntity itemEntity) {
    mongoTemplate.insert(itemEntity);
  }

  @Override
  public List<HeaderLineItemEntity> findHeaderLineItem(String headerLineId) {
    Query query = Query.query(Criteria.where("headerLineId").is(headerLineId));
    return mongoTemplate.find(query, myClass);
  }

  @Override
  public HeaderLineItemEntity findByResourceId(String resourceId) {
    Query query = Query.query(Criteria.where("resourceId").is(resourceId));
    return mongoTemplate.findOne(query, myClass);
  }

  @Override
  public void delByHeadLineIdAndResourceId(String headerLineId, String resourceId) {
    Query query = Query.query(Criteria.where("resourceId").is(resourceId).and("headerLineId").is(headerLineId));
    mongoTemplate.remove(query, myClass);
  }

  @Override
  public void delByResourceId(String resourceId) {
    Query query = Query.query(Criteria.where("resourceId").is(resourceId));
    mongoTemplate.remove(query, myClass);
  }
}
