package com.kankan.vo;

import com.kankan.vo.tab.TabItemVo;
import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */
@Data
public class KankanUserVo extends TabItemVo {
    private String userName;
    private String userId;
    private String remark;
    private String picture;
    private Long fansCount;
    private Long followCount;
    private Long readCount;
    private boolean followStatus;
}
