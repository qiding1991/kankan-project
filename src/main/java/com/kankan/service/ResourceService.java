package com.kankan.service;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

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
@Service
public class ResourceService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public String saveResource(MediaResource mediaResource) {
        MediaResource response = mongoTemplate.insert(mediaResource);
        return response.getResourceId();
    }

    public MediaResource findResource(String resourceId) {
        Query query= Query.query(Criteria.where("resourceId").is(resourceId));
        return mongoTemplate.findOne(query,MediaResource.class);
    }

    public List<MediaResource> findResource(Set<String> resourceIdSet) {
        Query query= Query.query(Criteria.where("resourceId").in(resourceIdSet));
        return mongoTemplate.find(query,MediaResource.class);
    }



    public void incrementCommentCount(MediaResource resource) {
        updateCount(resource.getResourceId(),()->"commentCount",MediaResource::getCommentCount);
    }

    public void incrementThumpCount(MediaResource resource) {
        updateCount(resource.getResourceId(),()->"thumpCount",MediaResource::getThumpCount);
    }


    public void incrementReadCount(MediaResource resource) {
        updateCount(resource.getResourceId(),()->"readCount",MediaResource::getReadCount);
    }

    private void updateCount(String resourceId,Supplier<String> param, Function<MediaResource,Integer> function){
        Query query= Query.query(Criteria.where("resourceId").is(resourceId));
        MediaResource old= mongoTemplate.findOne(query,MediaResource.class);
        Update update=Update.update(param.get(),ObjectUtils.defaultIfNull(function.apply(old),0)+1);
        mongoTemplate.updateFirst(query, update,MediaResource.class);
    }

    public  List<MediaResource>  findRelatedResource(MediaResource mediaResource) {
        Query query=Query.query(Criteria.where("keyWords").elemMatch(new Criteria().in(mediaResource.getKeyWords())));
        query.addCriteria(Criteria.where("resourceId").nin(mediaResource.getResourceId()));
        query.addCriteria(Criteria.where("mediaType").is(mediaResource.getMediaType()));
        return mongoTemplate.find(query,MediaResource.class);
    }

}
