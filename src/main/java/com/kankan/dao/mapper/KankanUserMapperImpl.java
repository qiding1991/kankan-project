package com.kankan.dao.mapper;

import com.kankan.dao.entity.KankanUserEntity;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class KankanUserMapperImpl implements KankanUserMapper {

  @Autowired
  private MongoTemplate mongoTemplate;
  private Class<KankanUserEntity> myClass = KankanUserEntity.class;

  @Override
  public void insert(KankanUserEntity entity) {
    mongoTemplate.insert(entity);
  }

  @Override
  public KankanUserEntity findByUserId(String userId) {
    Query query = Query.query(Criteria.where("userId").is(userId));
    return mongoTemplate.findOne(query, myClass);
  }

  @Override
  public List<KankanUserEntity> findAll() {
    return mongoTemplate.findAll(myClass);
  }

  @Override
  public List<KankanUserEntity> findByPage(String offset, Integer size) {
//    @Select("select * from kankan_user where id < #{offset} limit #{size}")
    Query query = new Query(Criteria.where("id").lt(offset)).limit(size);
    return mongoTemplate.find(query, myClass);
  }

  @Override
  public List<KankanUserEntity> findByType(String userType) {
    Query query = new Query(Criteria.where("userType").is(userType));
    return mongoTemplate.find(query, myClass);
  }

  @Override
  public void updateUserType(String userId, String userType) {
    Query query = Query.query(Criteria.where("userId").is(userId));
    Update update = Update.update("userType", userType);
    mongoTemplate.updateFirst(query, update, myClass);
  }

  @Override
  public void updateUserRecommendStatus(String userId, Integer recommendStatus) {
//    update kankan_user set recommend_status=#{recommendStatus} where user_id=#{userId}
    Query query = Query.query(Criteria.where("userId").is(userId));
    Update update = Update.update("recommendStatus", recommendStatus);
    mongoTemplate.updateFirst(query, update, myClass);
  }

  @Override
  public void updateFollowCount(String userId, Integer followCount) {
//iupdate kankan_user set follow_count=follow_count+${followCount} where user_id=#{userId
    Query query = Query.query(Criteria.where("userId").is(userId));
    Update update = new Update().inc("followCount", followCount);
    mongoTemplate.updateFirst(query, update, myClass);
  }

  @Override
  public void updateFansCount(String userId, Integer fansCount) {
    Query query = Query.query(Criteria.where("userId").is(userId));
    Update update = new Update().inc("fansCount", fansCount);
    mongoTemplate.updateFirst(query, update, myClass);
  }

  @Override
  public void updateReadCount(String userId, Integer readCount) {
    Query query = Query.query(Criteria.where("userId").is(userId));
    Update update = new Update().inc("readCount", readCount);
    mongoTemplate.updateFirst(query, update, myClass);
  }

  @Override
  public void updateWhiteStatus(String userId, Integer whiteStatus) {
    Query query = Query.query(Criteria.where("userId").is(userId));
    Update update = new Update().set("whiteStatus", whiteStatus);
    mongoTemplate.updateFirst(query, update, myClass);
  }

  @Override
  public List<KankanUserEntity> findHotUser(String orderField, Integer limit) {
//    @Select("select * from kankan_user order by  #{orderField} desc limit #{limit}")

    Query query = new Query()
        .with(Sort.by(Order.desc(orderField)))
        .limit(limit);
    return mongoTemplate.find(query, myClass);
  }

  @Override
  public List<KankanUserEntity> findByKeyword(String offset, Integer size, String keyword) {
//    @Select("select * from  kankan_user where user_name like concat('%',#{keyword},'%')  limit #{offset},#{size}")
    Query query = new Query().addCriteria(Criteria.where("userName").is("/"+keyword+"/"))
        .addCriteria(Criteria.where("id").gt(offset)).limit(size);
    return mongoTemplate.find(query, myClass);
  }
}
