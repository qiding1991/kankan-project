package com.kankan.service;

import com.kankan.module.privilege.UserPrivilege;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class UserPrivilegeService {

  @Autowired
  private MongoTemplate mongoTemplate;

  public UserPrivilege findByUserId(String userId){
    Query query = Query.query(Criteria.where("userId").is(userId));
    return  mongoTemplate.findOne(query,UserPrivilege.class);
  }

}
