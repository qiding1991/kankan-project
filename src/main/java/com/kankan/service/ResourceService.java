package com.kankan.service;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.kankan.module.MediaResource;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-03
 */
@Log4j2
@Service
public class ResourceService {

  @Autowired
  private MongoTemplate mongoTemplate;

  public String saveResource(MediaResource mediaResource) {
    MediaResource response = mongoTemplate.insert(mediaResource);
    return response.getResourceId();
  }

  public MediaResource findResource(String resourceId) {
    Query query = Query.query(Criteria.where("resourceId").is(resourceId));
    return mongoTemplate.findOne(query, MediaResource.class);
  }

  public List<MediaResource> findResource(Set<String> resourceIdSet) {
    Query query = Query.query(Criteria.where("resourceId").in(resourceIdSet));
    return mongoTemplate.find(query, MediaResource.class);
  }


  public void decrCommentCount(MediaResource resource) {
    updateCount(resource.getResourceId(), () -> "commentCount", MediaResource::getCommentCount, () -> -1);
  }

  public void incrementCommentCount(MediaResource resource) {
    updateCount(resource.getResourceId(), () -> "commentCount", MediaResource::getCommentCount, () -> 1);
  }

  public void incrementThumpCount(MediaResource resource) {
    updateCount(resource.getResourceId(), () -> "thumpCount", MediaResource::getThumpCount, () -> 1);
  }

  public void decreaseThumpCount(MediaResource resource) {
    updateCount(resource.getResourceId(), () -> "thumpCount", MediaResource::getThumpCount, () -> -1);
  }

  public void incrementReadCount(MediaResource resource) {
    updateCount(resource.getResourceId(), () -> "readCount", MediaResource::getReadCount, () -> 1);
  }

  private void updateCount(String resourceId, Supplier<String> param, Function<MediaResource, Integer> function, Supplier<Integer> count) {
    try {
      log.info("请求参数 resourceId={},param={}", resourceId, param.get());
      Query query = Query.query(Criteria.where("resourceId").is(resourceId));
      MediaResource old = mongoTemplate.findOne(query, MediaResource.class);
      Update update = Update.update(param.get(), ObjectUtils.defaultIfNull(function.apply(old), 0) + count.get());
      mongoTemplate.updateFirst(query, update, MediaResource.class);
    } catch (Exception e) {
      log.error("请求参数 resourceId={},param={}", resourceId, param.get(), e);
    }
  }

  public List<MediaResource> findRelatedResource(MediaResource mediaResource) {
    Query query = Query.query(Criteria.where("keyWords").elemMatch(new Criteria().in(mediaResource.getKeyWords())));
    query.addCriteria(Criteria.where("resourceId").nin(mediaResource.getResourceId()));
    query.addCriteria(Criteria.where("mediaType").is(mediaResource.getMediaType()));
    return mongoTemplate.find(query, MediaResource.class);
  }


}
