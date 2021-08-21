package com.kankan.service;

import com.kankan.module.ShareDetail;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class ShareService {

  @Autowired
  private MongoTemplate mongoTemplate;

  public ShareDetail addShare(ShareDetail shareDetail) {
    mongoTemplate.insert(shareDetail);
    return shareDetail;
  }

  public ShareDetail getShare(String id) {
    Query query = Query.query(Criteria.where("id").is(id));
    return mongoTemplate.findOne(query, ShareDetail.class);
  }

  public List<ShareDetail> findPage(Integer startIndex, Integer pageSize) {
    Query query = new Query();
    query.skip(startIndex).limit(pageSize);
    return mongoTemplate.find(query, ShareDetail.class);
  }


  public void remove(String id) {
    Query query = Query.query(Criteria.where("id").is(id));
    mongoTemplate.remove(query, ShareDetail.class);
  }
}
