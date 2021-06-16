package com.kankan.dao.mapper;

import com.kankan.dao.entity.KankanUserRole;
import com.kankan.module.KankanWorkTypeEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class KankanUserRoleMapperImpl implements KankanUserRoleMapper {

  @Autowired
  private MongoTemplate mongoTemplate;
  private Class<KankanUserRole> myClass = KankanUserRole.class;

  @Override
  public void insert(KankanUserRole kankanUserRole) {
    mongoTemplate.insert(kankanUserRole);
  }

  @Override
  public KankanUserRole findByUserId(Long userId) {
    Query query =new Query(Criteria.where("userId").is(userId));
    return mongoTemplate.findOne(query,myClass);
  }

  @Override
  public List<KankanUserRole> batchFindUser(String userList) {
    String [] userIds= userList.split(", ");
    Query query  =Query.query(Criteria.where("userId").in(userIds));
    return  mongoTemplate.find(query,myClass);
  }

  @Override
  public void updateRole(Long userId, String roleId) {
    Query query  =Query.query(Criteria.where("userId").in(userId));
    Update update =Update.update("roleId",roleId);
    mongoTemplate.updateFirst(query,update,myClass);
  }
}
