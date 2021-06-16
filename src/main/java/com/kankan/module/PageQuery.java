package com.kankan.module;

import lombok.Builder;
import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */
@Builder
@Data
public class PageQuery {
    private String offset;
    private Integer size;
}
