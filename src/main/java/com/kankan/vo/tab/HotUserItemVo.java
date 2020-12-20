package com.kankan.vo.tab;

import lombok.Data;

@Data
public class HotUserItemVo extends TabItemVo {
  private String picture;
  private String title="热门看看号";
  private String desc="注册看看号 >";
  private String more="更多";

}
