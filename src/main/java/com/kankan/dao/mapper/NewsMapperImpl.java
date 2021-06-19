package com.kankan.dao.mapper;

import com.kankan.dao.entity.NewsEntity;
import io.netty.util.internal.StringUtil;
import java.util.List;
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
  public List<NewsEntity> findNews(String tabId, String offset, Integer size) {
    Query query = new Query(Criteria.where("tabId").is(tabId))
        .with(Sort.by(Order.desc("id")))
        .limit(size);

    if(!"0".equals(offset)&& StringUtils.isNotBlank(offset)){
       query.addCriteria(Criteria.where("id").lt(new ObjectId(offset)));
    }
    return mongoTemplate.find(query, myClass);
  }

  @Override
  public NewsEntity findNewsById(String newsId) {
    return mongoTemplate.findById(newsId, myClass);
  }

  @Override
  public List<NewsEntity> findAllNews() {
    return mongoTemplate.findAll(myClass);
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
    return mongoTemplate.find(query, myClass);
  }
}
