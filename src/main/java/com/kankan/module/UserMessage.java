package com.kankan.module;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(value = "user-message")
@Data
public class UserMessage {
  @Id
  private String id;
  private Long userId;
  private Integer msgType;
  private String title;
  private String content;
  private boolean isRead;
  private Long createTime;
  private Long readTime;
}
