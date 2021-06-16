package com.kankan.param.tab;

import lombok.Builder;
import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-04
 */
@Data
@Builder
public class TabPageInfo {
     private String tabId;
     private String offset;
     private Integer size;
}
