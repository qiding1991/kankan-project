package com.kankan.param.work;

import java.util.List;
import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-03
 */
@Data
public class WorkUpdateInfo {
    private String id;
    private String picture;
    private String title;
    private String videoUrl;
    private Integer type;
    private String content;
    private List<String> keyword;
}
