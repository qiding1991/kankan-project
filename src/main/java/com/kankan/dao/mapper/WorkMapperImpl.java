package com.kankan.dao.mapper;

import com.kankan.dao.entity.WorkEntity;
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
public class WorkMapperImpl implements WorkMapper {

  @Autowired
  private MongoTemplate mongoTemplate;
  private Class<WorkEntity> myClass = WorkEntity.class;

  @Override
  public void insert(WorkEntity workEntity) {
    mongoTemplate.insert(workEntity);
  }

  @Override
  public List<WorkEntity> findArticle(String offset, Integer size) {
//
//      <select id="findArticle" resultType="WorkEntity">
//        select * from work_info where  `type`=0 and id &lt; #{offset} order by id desc limit #{size}
//    </select>
//
//    select * from work_info where  `type`=0 and id &lt; #{offset} order by id desc limit #{size}
    Query query = Query.query(Criteria.where("type").is(0).and("auditStatus").is(2))
        .limit(size).with(Sort.by(Order.desc("id")));
    if(!"0".equals(offset)&& StringUtils.isNotBlank(offset)){
       query.addCriteria(Criteria.where("id").lt(new ObjectId(offset)));
    }
    return mongoTemplate.find(query, myClass);
  }

  @Override
  public List<WorkEntity> findVideo(String offset, Integer size) {
//
//    <select id="findVideo" resultType="WorkEntity">
//        select * from work_info where  `type`=1 and  id &lt; #{offset} order by id desc limit #{size}
//    </select>
    Query query = Query.query(Criteria.where("type").is(1).and("auditStatus").is(2))
        .limit(size).with(Sort.by(Order.desc("id")));
    if(!"0".equals(offset)&& StringUtils.isNotBlank(offset)){
      query.addCriteria(Criteria.where("id").lt(new ObjectId(offset)));
    }
    return mongoTemplate.find(query, myClass);

  }

  @Override
  public WorkEntity findById(String itemId) {
//    @Select("select * from work_info where id=#{itemId}")
    Query query = Query.query(Criteria.where("id").is(itemId));
    return mongoTemplate.findOne(query, myClass);
  }

  @Override
  public List<WorkEntity> findUserWork(String userId) {
    Query query = Query.query(Criteria.where("userId").is(userId));
    return mongoTemplate.find(query, myClass);
  }

  @Override
  public List<WorkEntity> findAllWork() {
    return mongoTemplate.findAll(myClass);
  }

  @Override
  public WorkEntity findByResourceId(String resourceId) {
    Query query = Query.query(Criteria.where("resourceId").is(resourceId));
    return mongoTemplate.findOne(query, myClass);
  }

  @Override
  public List<WorkEntity> findUserWorkByType(String userId, Integer workType) {
//    @Select("select * from work_info where user_id=#{userId}  and type=#{workType}")
    Query query = Query.query(Criteria.where("userId").is(userId).and("type").is(workType));
    return mongoTemplate.find(query, myClass);
  }

  @Override
  public void updateWork(String id, Integer auditStatus) {
//    @Update("update work_info set audit_status=#{auditStatus} where id =#{id}")
    Query query = Query.query(Criteria.where("id").is(id));
    Update update = new Update().set("auditStatus", auditStatus);
    mongoTemplate.updateFirst(query, update, myClass);
  }

  @Override
  public void setHot(String workId, Integer hotStatus) {
    Query query = Query.query(Criteria.where("id").is(workId));
    Update update = new Update().set("hotStatus", hotStatus);
    mongoTemplate.updateFirst(query, update, myClass);
  }

  @Override
  public void setHeader(String workId, Integer headStatus) {
    Query query = Query.query(Criteria.where("id").is(workId));
    Update update = new Update().set("headStatus", headStatus);
    mongoTemplate.updateFirst(query, update, myClass);
  }

  @Override
  public List<WorkEntity> findWorkTitle(String articleIds) {
    Query query = Query.query(Criteria.where("id").in(articleIds));
    return mongoTemplate.find(query, myClass);
  }

  @Override
  public List<WorkEntity> findArticleByKeyword(String offset, Integer size, String keyword) {
//    @Select("select * from work_info where type=0 and   title like concat('%',#{keyword},'%')"
//        + "  limit #{offset},#{size} ")
    Query query = Query.query(Criteria.where("type").is(0)
        .and("id").lt(offset)
        .and("title").is("/" + keyword + "/"))
        .limit(size).with(Sort.by(Order.desc("id")));
    return mongoTemplate.find(query, myClass);
  }
}
