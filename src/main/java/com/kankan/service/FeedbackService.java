package com.kankan.service;

import java.util.function.Consumer;
import java.util.function.Function;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.kankan.dao.entity.FeedbackEntity;
import com.kankan.dao.mapper.FeedbackMapper;
import com.kankan.module.Feedback;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */
@Service
public class FeedbackService {

    @Resource
    private FeedbackMapper feedbackMapper;

    public void addFeedback(Feedback feedback) {
        feedback(feedback, feedbackMapper::insert);
    }

    public void feedback(Feedback feedback, Consumer<FeedbackEntity> feedFunction) {
        feedFunction.accept(((Function<Feedback, FeedbackEntity>) back -> transform(back)).apply(feedback));
    }

    public FeedbackEntity transform(Feedback feedback) {
        FeedbackEntity entity = new FeedbackEntity();
        BeanUtils.copyProperties(feedback, entity);
        return entity;
    }
}
