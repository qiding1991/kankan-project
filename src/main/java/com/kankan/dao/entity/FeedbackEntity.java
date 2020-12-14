package com.kankan.dao.entity;

import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */
@Data
public class FeedbackEntity extends BaseEntity{
    private Long userId;
    private String feedback;
}
