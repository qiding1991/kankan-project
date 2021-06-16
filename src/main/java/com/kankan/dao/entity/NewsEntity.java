package com.kankan.dao.entity;

import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-03
 */
@Data
public class NewsEntity extends BaseEntity{
    private String resourceId;
    private String tabId;
    private String userId;
    private String title;
    private String picture;
    private Integer hotStatus;//是否设置称热点
    private Integer headStatus;
}
