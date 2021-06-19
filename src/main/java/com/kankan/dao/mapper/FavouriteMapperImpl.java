package com.kankan.dao.mapper;

import com.kankan.dao.entity.FavouriteEntity;
import com.kankan.dao.entity.FeedbackEntity;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class FavouriteMapperImpl implements FavouriteMapper {

  @Autowired
  private MongoTemplate mongoTemplate;
  private Class<FavouriteEntity> myClass = FavouriteEntity.class;


  @Override
  public void insert(FavouriteEntity entity) {
    mongoTemplate.insert(entity);
  }

  @Override
  public List<FavouriteEntity> findUserFavourite(String userId, String offset, Integer size) {
    Query query = Query.query(Criteria.where("userId").is(userId)).limit(size);
    if("0".equals(offset)&& StringUtils.isNotBlank(offset)){
       query.addCriteria(Criteria.where("id").gt(new ObjectId(offset)));
    }
    return mongoTemplate.find(query, myClass);
  }

  @Override
  public void remove(String userId, String resourceId) {
    Query query = new Query(Criteria.where("userId").is(userId).and("resourceId").is(resourceId));
    mongoTemplate.remove(query, myClass);
  }

  @Override
  public FavouriteEntity findByUserIdAndResourceId(String userId, String resourceId) {
    Query query = new Query(Criteria.where("userId").is(userId).and("resourceId").is(resourceId));
    return mongoTemplate.findOne(query, myClass);
  }
}
