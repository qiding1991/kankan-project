package com.kankan.module;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class ShareDetail {
  @Id
  private String id;
  private String title;
  private String content;
}
