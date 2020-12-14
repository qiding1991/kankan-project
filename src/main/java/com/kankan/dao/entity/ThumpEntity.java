package com.kankan.dao.entity;


import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
@Data
public class ThumpEntity extends BaseEntity {
    private String resourceId;
    private Long commentId;
    private Long userId;
}
