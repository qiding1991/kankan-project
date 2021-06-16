package com.kankan.module.privilege;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(value = "user-privilege")
@Data
public class UserPrivilege {
  @Id
  private String userId;
  private List<String> privilege;
}
