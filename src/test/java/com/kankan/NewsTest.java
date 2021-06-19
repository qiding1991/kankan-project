package com.kankan;

import com.kankan.dao.entity.NewsEntity;
import lombok.extern.log4j.Log4j2;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;

@Log4j2
@SpringBootTest
public class NewsTest extends BaseTest {

  @Autowired
  private MongoTemplate mongoTemplate;


  @Test
  public void testMongoLt() {
    Query query = new Query(Criteria.where("id").gte(new ObjectId("60cd696e0646cb73d2bcd3cd")));

    NewsEntity entity = mongoTemplate.findOne(query, NewsEntity.class);

    System.out.printf("1111");
  }

}
