package com.kankan.dao.mapper;

import com.kankan.dao.entity.KankanTypeEntity;
import com.kankan.dao.entity.KankanUserEntity;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class KankanTypeMapperImpl implements KankanTypeMapper {

  @Autowired
  private MongoTemplate mongoTemplate;
  private Class<KankanTypeEntity> myClass = KankanTypeEntity.class;


  @Override
  public void insert(KankanTypeEntity entity) {
     mongoTemplate.insert(entity);
  }

  @Override
  public void update(KankanTypeEntity kankanTypeEntity) {

    Update update= new Update();
    String typeDesc = kankanTypeEntity.getTypeDesc();
    Integer typeOrder = kankanTypeEntity.getTypeOrder();
    if(ObjectUtils.allNotNull(typeDesc)){
      update.set("typeDesc",typeDesc);
    }
    if(ObjectUtils.allNotNull(typeOrder)){
      update.set("typeOrder",typeOrder);
    }
    Query querty= new Query(Criteria.where("id").is(kankanTypeEntity.getId()));
    mongoTemplate.updateFirst(querty,update,myClass);
  }

  @Override
  public void delById(String id) {
     Query query = Query.query(Criteria.where("id").is(id));
     mongoTemplate.remove(query, myClass);
  }

  @Override
  public List<KankanTypeEntity> findAll() {
    return mongoTemplate.findAll(myClass);
  }
}
