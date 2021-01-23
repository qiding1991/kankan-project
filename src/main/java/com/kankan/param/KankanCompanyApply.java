package com.kankan.param;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "kankan_apply")
@Data
public class KankanCompanyApply {
  private String adminName;
  private String phone;
  private List<String> privilege;
  @Id
  private Long userId;
  private Integer applyStatus;
  private String city;
  private Integer applyType;
  private String remark;
  private List<String> topicPhoto;
  private List<String> applyPhoto;
  private List<String> otherPhoto;
}
