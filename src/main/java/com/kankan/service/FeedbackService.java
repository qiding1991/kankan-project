package com.kankan.service;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.kankan.dao.entity.FeedbackEntity;
import com.kankan.dao.mapper.FeedbackMapper;
import com.kankan.module.Feedback;

/**
 * @author <qiding@qiding.com> Created on 2020-12-08
 */
@Service
public class FeedbackService {

  @Autowired
  private MongoTemplate mongoTemplate;

  public void addFeedBack(Feedback feedback) {
    mongoTemplate.insert(feedback);
  }

  public List<Feedback> feedbackList() {
    Query query = new Query().with(Sort.by(Order.desc("createTime")));
    return mongoTemplate.find(query, Feedback.class);
  }

//
//    @Resource
//    private FeedbackMapper feedbackMapper;
//
//    public void addFeedback(Feedback feedback) {
//        feedback(feedback, feedbackMapper::insert);
//    }
//
//    public void feedback(Feedback feedback, Consumer<FeedbackEntity> feedFunction) {
//        feedFunction.accept(((Function<Feedback, FeedbackEntity>) back -> transform(back)).apply(feedback));
//    }
//
//    public FeedbackEntity transform(Feedback feedback) {
//        FeedbackEntity entity = new FeedbackEntity();
//        BeanUtils.copyProperties(feedback, entity);
//        return entity;
//    }
}
