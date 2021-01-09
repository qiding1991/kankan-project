package com.kankan.service;

import com.kankan.module.privilege.Privilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeService {
  @Autowired
  private MongoTemplate mongoTemplate;

  public Privilege getPrivilege(String privilegeId) {
    Query query = Query.query(Criteria.where("privilegeId").is(privilegeId));
    return mongoTemplate.findOne(query, Privilege.class);
  }

  public List<Privilege> findAll() {
    return mongoTemplate.findAll(Privilege.class);
  }

  public void saveToDb(Privilege privilege) {
    mongoTemplate.save(privilege);
  }
}
