package com.kankan.dao.mapper;

import com.kankan.module.KankanWorkTypeEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class KankanWorkTypeMapperImpl implements KankanWorkTypeMapper {

  @Autowired
  private MongoTemplate mongoTemplate;
  private Class<KankanWorkTypeEntity> myClass = KankanWorkTypeEntity.class;

  @Override
  public void insert(KankanWorkTypeEntity kankanWorkTypeEntity) {
    mongoTemplate.insert(kankanWorkTypeEntity);
  }

  @Override
  public void removeKankanWorkType(Long id) {
    Query query = Query.query(Criteria.where("id").is(id));
    mongoTemplate.remove(query, myClass);
  }

  @Override
  public void updateWorkType(Long id, String typeName) {
    Query query = Query.query(Criteria.where("id").is(id));
    Update update = Update.update("typeName", typeName);
    mongoTemplate.updateFirst(query, update, myClass);
  }

  @Override
  public List<KankanWorkTypeEntity> findAll() {
    return mongoTemplate.findAll(myClass);
  }
}
