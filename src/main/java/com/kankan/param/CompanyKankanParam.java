package com.kankan.param;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "kankan_apply")
@Data
public class CompanyKankanParam {

  private String adminName;
  private String phone;
  @Id
  private Long userId;
  private Integer applyStatus;
  private String city;
  private List<String> topicPhoto;
  private List<String> applyPhoto;
  private List<String> otherPhoto;
}
