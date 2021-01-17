package com.kankan.vo;

import com.kankan.module.News;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
public class NewsVo {
  private Long id;
  private String resourceId;
  private Long tabId;
  private String picture;
  private String title;
  private String content;
  private Integer hotStatus;//是否设置称热点
  private Integer headStatus;
  private List<String> keyword;

  public NewsVo(News news) {
    BeanUtils.copyProperties(news, this);
  }
}
