package com.kankan.dao.mapper;

import com.kankan.dao.entity.FeedbackEntity;
import com.kankan.dao.entity.FollowEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class FeedbackMapperImpl implements FeedbackMapper {

  @Autowired
  private MongoTemplate mongoTemplate;
  private Class<FeedbackEntity> myClass = FeedbackEntity.class;


  @Override
  public void insert(FeedbackEntity feedbackEntity) {
    mongoTemplate.insert(feedbackEntity);
  }
}
