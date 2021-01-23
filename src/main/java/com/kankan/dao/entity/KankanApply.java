package com.kankan.dao.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(value = "kankan_apply")
@Data
public class KankanApply{
  @Id
  private Long userId;
  private List<String> privilegeList;
  private String idUrl;
  private String photo;
  private String username;
  private String remark;
  private String email;
  private Integer applyStatus;
}
