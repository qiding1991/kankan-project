package com.kankan.dao.mapper;

import java.util.List;

//import org.apache.ibatis.annotations.Mapper;

import com.kankan.dao.entity.TabEntity;

/**
 * @author <qiding@qiding.com> Created on 2020-12-02
 */
//@Mapper
public interface TabMapper {

  List<TabEntity> findTab();

  void updateTab(TabEntity entity);

  void addTab(TabEntity entity);

  TabEntity findTabById(String tabId);

  void removeTab(String tabId);
}
