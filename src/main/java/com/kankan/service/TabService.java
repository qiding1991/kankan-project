package com.kankan.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.kankan.dao.entity.TabEntity;
import com.kankan.dao.mapper.TabMapper;
import com.kankan.module.Tab;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-02
 */
@Service
public class TabService {

    @Resource
    private TabMapper tabMapper;

    public List<Tab> findTab() {
        List<TabEntity> tabEntitryList = tabMapper.findTab();
        return tabEntitryList.stream()
                .sorted(Comparator.comparing(TabEntity::getTabOrder))
                .map(TabEntity::tab)
                .collect(Collectors.toList());
    }

    public void updateTab(Tab tab) {
        TabEntity entity=new TabEntity();
        BeanUtils.copyProperties(tab,entity);
        entity.setId(tab.getTabId());
        tabMapper.updateTab(entity);
    }

    public void createTab(Tab tab) {
        TabEntity entity=new TabEntity();
        BeanUtils.copyProperties(tab,entity);
        tabMapper.addTab(entity);
        tab.setTabId(entity.getId());
    }

    public Tab findTab(String tabId) {
       TabEntity entity=  tabMapper.findTabById(tabId);
       if(entity==null){
         return null;
       }
       return entity.tab();
    }

  public void removeTab(String tabId) {
       tabMapper.removeTab(tabId);
  }
}
