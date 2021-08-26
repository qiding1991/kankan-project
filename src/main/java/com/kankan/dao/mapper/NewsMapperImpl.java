package com.kankan.dao.mapper;

import com.kankan.dao.entity.NewsEntity;

import io.netty.util.internal.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class NewsMapperImpl implements NewsMapper {

  @Autowired
  private MongoTemplate mongoTemplate;
  private Class<NewsEntity> myClass = NewsEntity.class;

  @Override
  public void insert(NewsEntity entity) {
    mongoTemplate.insert(entity);
  }

  @Override
  public List<NewsEntity> findNews(String tabId, Long offset, Integer size) {
    Query query = new Query(Criteria.where("tabId").is(tabId)
        .and("updateTime").lt(offset))
        .with(Sort.by(Order.desc("updateTime")))
        .limit(size);
    query.addCriteria(Criteria.where("status").is(1));
    return mongoTemplate.find(query, myClass);
  }

  @Override
  public NewsEntity findNewsById(String newsId) {
    return mongoTemplate.findById(newsId, myClass);
  }

  @Override
  public List<NewsEntity> findAllNews() {
    Query query = new Query(Criteria.where("status").is(1));
//    query= query.with(Sort.by(Order.desc("updateTime"))).limit(10);
    return mongoTemplate.find(query, myClass);
//    Integer pageSize = 10;
//    AtomicInteger index = new AtomicInteger(2);
//    List<NewsEntity> newsList = new ArrayList<>();
//    Supplier<Boolean> hasMore = () -> {
//      List<NewsEntity> infoList = mongoTemplate.find(
//          query.skip((index.getAndIncrement() - 1) * pageSize).with(Sort.by(Order.desc("update_at"))).limit(pageSize), myClass);
//      if(CollectionUtils.isEmpty(infoList)){
//        return false;
//      }
//      newsList.addAll(infoList);
//      return  true;
//    };
//    while(hasMore.get()){}
//    return newsList;
  }

  @Override
  public NewsEntity findNewsByResourceId(String resourceId) {
    Query query = Query.query(Criteria.where("resourceId")
        .is(resourceId));
    return mongoTemplate.findOne(query, myClass);
  }

  @Override
  public void setHot(String newsId, Integer hotStatus) {
    update(newsId.toString(), hotStatus, "hotStatus");
  }

  @Override
  public void setHeader(String newsId, Integer headStatus) {
    update(newsId.toString(), headStatus, "headStatus");
  }


  public void update(String id, Integer status, String keyName) {
    Update update = Update.update(keyName, status);
    Query query = Query.query(Criteria.where("id")
        .is(id));
    mongoTemplate.updateFirst(query, update, myClass);
  }


  @Override
  public List<NewsEntity> findByUserId(String userId) {
    Query query = Query.query(Criteria.where("userId")
        .is(userId));
    query.addCriteria(Criteria.where("status").is(1));
    return mongoTemplate.find(query, myClass);
  }

  @Override
  public List<NewsEntity> findTitles(String newsIds) {
    Query query = Query.query(Criteria.where("id")
        .in(newsIds.split(",")));
    return mongoTemplate.find(query, myClass);
  }

  @Override
  public List<NewsEntity> findByKeyword(String keyword, String offset, Integer size) {

    //    @Select("select * from news_info where title like  concat('%',#{keyword},'%')  limit #{offset},#{size} ")
    Query query = new Query(Criteria.where("title").is("/" + keyword + "/"))
        .addCriteria(Criteria.where("id").lt(offset))
        .with(Sort.by(Order.desc("id"))).limit(size);
    query.addCriteria(Criteria.where("status").is(1));
    return mongoTemplate.find(query, myClass);
  }

  @Override
  public void delete(String id) {
    Query query = Query.query(Criteria.where("id").is(id));
    Update update = new Update().set("status", 0);
    mongoTemplate.updateFirst(query, update, myClass);
  }

  @Override
  public List<NewsEntity> findRelated(String tabId, String id) {
    Query query = Query.query(Criteria.where("tabId").is(tabId).and("id").ne(id));
    query.addCriteria(Criteria.where("status").is(1));
    return mongoTemplate.find(query, myClass);
  }

  @Override
  public void updateNews(NewsEntity newsEntity) {
    Query query = new Query(Criteria.where("id").is(newsEntity.getId()));
    Update update = new Update();
    setProper(update, newsEntity::getPicture, "picture");
    setProper(update, newsEntity::getTitle, "title");
    mongoTemplate.updateFirst(query, update, myClass);
  }

  private void setProper(Update update, Supplier<Object> propSupplier, String properName) {
    Object properValue = propSupplier.get();
    if (!org.springframework.util.StringUtils.isEmpty(properValue)) {
      update.set(properName, properValue);
    }
  }


  @Override
  public void auditNews(String id, Integer auditStatus) {
    Query query = new Query(Criteria.where("id").is(id));
    Update update = new Update();
    setProper(update, () -> auditStatus, "auditStatus");
    mongoTemplate.updateFirst(query, update, myClass);
  }
}
