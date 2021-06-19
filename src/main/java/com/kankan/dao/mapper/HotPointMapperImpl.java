package com.kankan.dao.mapper;

import com.kankan.dao.entity.HotPointEntity;
import com.kankan.module.HotPoint;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class HotPointMapperImpl implements HotPointMapper {

  @Autowired
  private MongoTemplate mongoTemplate;
  private Class<HotPointEntity> myClass = HotPointEntity.class;


  @Override
  public void insert(HotPointEntity hotPointEntity) {
    mongoTemplate.insert(hotPointEntity);
  }

  @Override
  public List<HotPointEntity> findHotInfo(String offset, Integer size) {
//    return nuselect item_id,item_type from hot_info where status = 1 and item_order &lt; #{offset}  order by item_order desc limit #{size}ll;
    Query query = new Query(Criteria.where("status").is(1))
        .with(Sort.by(Order.desc("itemOrder")))
        .limit(size);
    if(!"0".equals(offset)&& StringUtils.isNotBlank(offset)){
      query.addCriteria(Criteria.where("itemOrder").lt(offset));
    }
    return  mongoTemplate.find(query,myClass);
  }

  @Override
  public List<HotPointEntity> findAll() {
    return mongoTemplate.findAll(myClass);
  }

  @Override
  public HotPointEntity findByItemTypeAndItemId(int itemType, String itemId) {
    Query query = Query.query(Criteria.where("itemId").is(itemId)
        .and("itemType").is(itemType));
    return mongoTemplate.findOne(query, myClass);

  }

  @Override
  public void delByItemTypeAndItemId(int itemType, String itemId) {
    Query query = Query.query(Criteria.where("itemId").is(itemId)
        .and("itemType").is(itemType));
    mongoTemplate.remove(query, myClass);
  }

  @Override
  public List<HotPoint> findByItemType(String itemType, Integer limit) {
//    @Select("select * from hot_info where  item_type in (${itemType})  limit #{limit}")
    Query query = Query.query(Criteria.where("itemType").in(itemType.split(","))).limit(limit);
    List<HotPointEntity> hotPointEntity = mongoTemplate.find(query, myClass);
    return hotPointEntity.stream().map(HotPoint::parseEntity).collect(Collectors.toList());
  }
}
