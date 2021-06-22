package com.kankan.vo;

import com.kankan.vo.tab.TabItemVo;
import lombok.Data;

/**
 * @author <qiding@qiding.com> Created on 2020-12-08
 */
@Data
public class KankanUserVo extends TabItemVo {

  private String userName;
  private String userId;
  private String remark;
  private String picture;
  private Long fansCount = 0L;
  private Long followCount = 0L;
  private Long readCount = 0L;
  private boolean followStatus;
}
