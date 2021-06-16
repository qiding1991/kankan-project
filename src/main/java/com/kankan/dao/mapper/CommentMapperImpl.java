package com.kankan.dao.mapper;

import com.kankan.dao.entity.CommentEntity;
import com.kankan.dao.entity.FavouriteEntity;
import com.kankan.module.KankanComment;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class CommentMapperImpl implements CommentMapper {

  @Autowired
  private MongoTemplate mongoTemplate;
  private Class<CommentEntity> myClass = CommentEntity.class;


  @Override
  public void insert(CommentEntity entity) {
    mongoTemplate.insert(entity);
  }

  @Override
  public List<CommentEntity> findByUserId(String userId) {
    Query query = Query.query(Criteria.where("userId").is(userId));
    return mongoTemplate.find(query, myClass);
  }


  @Override
  public List<CommentEntity> findByCondition(List<KankanComment> workCondition) {
    List<CommentEntity> entityList = new ArrayList<>();

//      select * from comment where resource_id=#{obj.resourceId} and parent_id =#{obj.parentId}
    for (KankanComment comment : workCondition) {
      Query query = new Query(Criteria
          .where("resourceId").is(comment.getResourceId())
          .and("parentId").is(comment.getParentId()));
      entityList.addAll(mongoTemplate.find(query, myClass));
    }
    return  entityList;
  }

  @Override
  public List<CommentEntity> findResourceComment(String resourceId) {
    Query query = Query.query(Criteria.where("resourceId").is(resourceId));
    return mongoTemplate.find(query, myClass);
  }

  @Override
  public void incrementThumpCount(String id) {
    Query query = Query.query(Criteria.where("id").is(id));
    Update update = new Update();
    update.inc("thumpCount", 1);
    mongoTemplate.updateFirst(query, update, myClass);
  }

  @Override
  public CommentEntity findById(String id) {
    return mongoTemplate.findById(id, myClass);
  }

  @Override
  public void decreaseThumpCount(String id) {
    Query query = Query.query(Criteria.where("id").is(id));
    Update update = new Update();
    update.inc("thumpCount", -1);
    mongoTemplate.updateFirst(query, update, myClass);
  }

//  @Override
//  public List<CommentEntity> findByParentId(Long id) {
//
//  }

  @Override
  public void deleteById(String id) {
    Query query = Query.query(Criteria.where("id").is(id));
    mongoTemplate.remove(query, myClass);
  }
}
