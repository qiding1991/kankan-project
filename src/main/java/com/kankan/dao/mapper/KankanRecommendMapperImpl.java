package com.kankan.dao.mapper;

import com.kankan.dao.entity.KankanRecommendEntity;
import com.kankan.dao.entity.KankanTypeEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class KankanRecommendMapperImpl implements KankanRecommendMapper {

  @Autowired
  private MongoTemplate mongoTemplate;
  private Class<KankanRecommendEntity> myClass = KankanRecommendEntity.class;

  @Override
  public List<KankanRecommendEntity> findAll() {
    return mongoTemplate.findAll(myClass);
  }

  @Override
  public void insert(KankanRecommendEntity entity) {
    mongoTemplate.insert(entity);
  }

  @Override
  public void deleteByUserId(KankanRecommendEntity entity) {
    Query query = Query.query(Criteria.where("userId")
        .is(entity.getUserId()));
    mongoTemplate.remove(query, myClass);
  }

  @Override
  public void updateByUserId(KankanRecommendEntity entity) {
    Query query = Query.query(Criteria.where("userId")
        .is(entity.getUserId()));

    Update update = Update.update("recommendOrder",entity.getRecommendOrder());
    mongoTemplate.updateFirst(query,update,myClass);
  }

  @Override
  public KankanRecommendEntity findKankanRecommend(String userId) {
    Query query = Query.query(Criteria.where("userId")
        .is(userId));
    return  mongoTemplate.findOne(query,myClass);
  }
}
