package com.kankan.module;

import java.util.List;

import com.kankan.service.TabService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tab {
    private Long tabId;
    private String tabName;
    private Integer tabType;
    private Integer tabOrder;
    public static Tab defaultTab() {
        return new Tab();
    }

    public List<Tab> tabList(TabService tabService) {
        return tabService.findTab();
    }

    public void tabName(String tabName) {
        this.tabName=tabName;
    }

    public void tabType(Integer tabType) {
        this.tabType=tabType;
    }

    public void tabOrder(Integer tabOrder) {
        this.tabOrder=tabOrder;
    }

    public void save2Db(TabService tabService) {
        tabService.updateTab(this);
    }

    public void insert2Db(TabService tabService) {
        tabService.createTab(this);
    }
}
