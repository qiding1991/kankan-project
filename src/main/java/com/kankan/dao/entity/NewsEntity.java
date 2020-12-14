package com.kankan.dao.entity;

import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-03
 */
@Data
public class NewsEntity extends BaseEntity{
    private String resourceId;
    private Long tabId;
    private String title;
    private String picture;
}
