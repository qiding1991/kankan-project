package com.kankan.dao.mapper;

import com.kankan.dao.entity.HeaderLineInfoEntity;
import com.kankan.dao.entity.HeaderLineItemEntity;
import com.kankan.module.HeaderLine;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class HeaderLineInfoMapperImpl implements HeaderLineInfoMapper {

  @Autowired
  private MongoTemplate mongoTemplate;
  private Class<HeaderLineInfoEntity> myClass = HeaderLineInfoEntity.class;

  @Override
  public void save(HeaderLineInfoEntity headerLineInfoEntity) {
    mongoTemplate.save(headerLineInfoEntity);
  }

  @Override
  public HeaderLineInfoEntity findHeaderLineInfo(String tabId) {
    Query query = Query.query(Criteria.where("tabId").is(tabId));
    return mongoTemplate.findOne(query, myClass);
  }

  @Override
  public HeaderLine findHeaderLineById(String id) {
    return HeaderLine.parseEntity(mongoTemplate.findById(id, myClass));
  }

  @Override
  public List<HeaderLineInfoEntity> findHeaderLine() {
    return mongoTemplate.findAll(myClass);
  }
}
