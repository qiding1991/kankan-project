package com.kankan.module.privilege;

import com.kankan.service.PrivilegeService;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "kankan-privilege")
@Data
public class Privilege {
  @Id
  @Indexed(unique = true)
  private String privilegeId;
  private String privilegeDesc;

  public void saveToDb(PrivilegeService privilegeService) {
     privilegeService.saveToDb(this);
  }
}
