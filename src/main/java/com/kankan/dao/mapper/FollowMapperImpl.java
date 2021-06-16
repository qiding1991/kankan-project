package com.kankan.dao.mapper;

import com.kankan.dao.entity.FollowEntity;
//import com.kankan.dao.entity.HeaderLineInfoEntity;
import java.util.List;
import java.util.Queue;
//import org.apache.ibatis.annotations.Delete;
//import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class FollowMapperImpl implements FollowMapper {

  @Autowired
  private MongoTemplate mongoTemplate;
  private Class<FollowEntity> myClass = FollowEntity.class;


  @Override
  public List<FollowEntity> findUserFollow(String userId, String offset, Integer size) {

//    @Select("select * from kankan_follow where user_id=#{userId} and id > #{offset} limit #{size}")

    Query query = Query.query(Criteria.where("userId").is(userId).and("id").lt(offset)).limit(size);
    return mongoTemplate.find(query, myClass);
  }

  @Override
  public Integer findCount(String userId, String followId) {

//    @Select("select count(1) from kankan_follow where user_id=#{userId} and follow_id=#{followId}")
    Query query = Query.query(Criteria.where("userId").is(userId).and("followId").is(followId));
    return (int) mongoTemplate.count(query, myClass);
  }

  @Override
  public void insert(FollowEntity followEntity) {
    mongoTemplate.insert(followEntity);
  }

  @Override
  public void delete(String userId, String followId) {
//    @Delete("delete from kankan_follow where user_id=#{userId} and follow_id=#{followId}")
    Query query = Query.query(Criteria.where("userId").is(userId).and("followId").is(followId));
    mongoTemplate.remove(query, myClass);
  }
}
