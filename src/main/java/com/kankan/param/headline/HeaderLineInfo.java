package com.kankan.param.headline;

import com.kankan.module.HeaderLine;
import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-03
 */
@Data
public class HeaderLineInfo {
    private String picture;
    private String title;
    private String tabId;

    public HeaderLine toHeadline() {
        return HeaderLine.builder().picture(picture).tabId(tabId).title(title).build();
    }
}
