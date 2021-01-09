package com.kankan.service;

import com.kankan.module.privilege.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

  @Autowired
  private MongoTemplate mongoTemplate;


  public List<UserRole> findAll() {
    return mongoTemplate.findAll(UserRole.class);
  }

  public void saveToDB(UserRole userRole) {
    mongoTemplate.save(userRole);
  }

  public UserRole findUserRole(String roleId) {
    Query query = Query.query(Criteria.where("roleId").is(roleId));
    return mongoTemplate.findOne(query, UserRole.class);
  }
}
