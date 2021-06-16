package com.kankan.dao.mapper;

import com.kankan.dao.entity.ThumpEntity;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class ThumpMapperImpl implements ThumpMapper {

  @Autowired
  private MongoTemplate mongoTemplate;
  private Class<ThumpEntity> myClass = ThumpEntity.class;

  @Override
  public void insert(ThumpEntity entity) {
    mongoTemplate.insert(entity);
  }

  @Override
  public void remove(ThumpEntity entity) {

//         <update id="remove"  parameterType="ThumpEntity">
//        delete  from thumbs where user_id=#{userId}
//        <if test="resourceId!=null">
//        and resource_id=#{resourceId}
//        </if>
//        <if test="commentId!=null">
//        and comment_id=#{commentId}
//        </if>
//     </update>
//

    String resourceId = entity.getResourceId();
    String commentId = entity.getCommentId();

    Query query = new Query(Criteria.where("userId").is(entity.getUserId()));
    if (StringUtils.isNotBlank(resourceId)) {
      query.addCriteria(Criteria.where("resourceId").is(resourceId));
    }
    if (StringUtils.isNotBlank(commentId)) {
      query.addCriteria(Criteria.where("commentId").is(commentId));
    }
    mongoTemplate.remove(query, myClass);
  }

  @Override
  public List<ThumpEntity> findByResourceId(String resourceId, String userId) {
//    @Select("select * from thumbs where resource_id=#{resourceId} and comment_id=0 and user_id=#{userId}")
    Query query = new Query(Criteria.where("resourceId").is(resourceId)
        .and("commentId").is(0).and("userId").is(userId));
    return mongoTemplate.find(query, myClass);
  }

  @Override
  public List<ThumpEntity> findByCommentId(String commentId, String userId) {
//    @Select("select * from thumbs where comment_id=#{commentId} and user_id=#{userId}")
    Query query = new Query(Criteria.where("commentId").is(commentId)
        .and("userId").is(userId));
    return mongoTemplate.find(query, myClass);
  }
}
