package com.kankan.dao.mapper;

import com.kankan.dao.entity.TabEntity;
import com.kankan.dao.entity.ThumpEntity;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class TabMapperImpl implements TabMapper {

  @Autowired
  private MongoTemplate mongoTemplate;
  private Class<TabEntity> myClass = TabEntity.class;


  @Override
  public List<TabEntity> findTab() {
    Query query = new Query().with(Sort.by(Order.desc("tabOrder")));
    return mongoTemplate.find(query, myClass);
  }

  @Override
  public void updateTab(TabEntity entity) {
    Update update = new Update();
    update(update, entity, TabEntity::getTabName, "tabName");
    update(update, entity, TabEntity::getTabType, "tabType");
    update(update, entity, TabEntity::getTabOrder, "tabOrder");
    Query query = new Query(Criteria.where("id").is(entity.getId()));
    mongoTemplate.updateFirst(query, update, myClass);
  }

  <T, R> void update(Update update, T param, Function<T, R> function, String keyName) {
    if (ObjectUtils.allNotNull(function.apply(param))) {
      update.set(keyName, function.apply(param));
    }
  }


  @Override
  public void addTab(TabEntity entity) {
    mongoTemplate.insert(entity);
  }

  @Override
  public TabEntity findTabById(String tabId) {
    return mongoTemplate.findById(tabId, myClass);
  }

  @Override
  public void removeTab(String tabId) {
    Query query = new Query(Criteria.where("id").is(tabId));
    mongoTemplate.remove(query, myClass);
  }
}
