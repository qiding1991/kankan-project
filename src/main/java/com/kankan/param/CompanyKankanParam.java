package com.kankan.param;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "kankan_apply")
@Data
public class CompanyKankanParam {
  @Id
  private String id;
  private String adminName;
  private String phone;
  private Long userId;
  private Integer applyStatus;
  private String city;
  private String topicPhoto;
  private String applyPhoto;
  private String otherPhoto;
}
