package com.kankan.dao.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@Document(value = "kankan_apply")
@Data
public class KankanApply{
  @Id
  private Long userId;
  private List<String> privilege;
  private String idUrl;
  private Long userType;
  private String photo;
  private String username;
  private String remark;
  private String email;
  private Integer applyStatus;
  private Integer applyType;
}
