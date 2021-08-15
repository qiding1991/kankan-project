package com.kankan.api.admin.news.param;

import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2021-08-15
 */
@Data
public class UpdateNewsInfo {
    private String id;
    private String picture;
    private String title;
    private String content;
    private String desc;
}
