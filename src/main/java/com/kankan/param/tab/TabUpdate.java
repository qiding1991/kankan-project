package com.kankan.param.tab;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.ObjectUtils;

import com.kankan.module.Tab;

import lombok.Data;


/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-02
 */
@Data
public class TabUpdate {

    @NotNull(message = "tabId不能为空")
    private Long tabId;

    private String tabName;

    @Min(value = 0, message = "最小值为0")
    @Max(value = 128, message = "最大值为128")
    private Integer tabType;

    @Min(value = 0, message = "最小值为0")
    @Max(value = 128, message = "最大值为128")
    private Integer tabOrder;

    public Tab toTab() {
        Tab tab = Tab.builder().tabId(tabId).build();
        if (ObjectUtils.isNotEmpty(tabName)) {
            tab.tabName(tabName);
        }
        if (ObjectUtils.isNotEmpty(tabType)) {
            tab.tabType(tabType);
        }
        if (ObjectUtils.isNotEmpty(tabOrder)) {
            tab.tabOrder(tabOrder);
        }
        return tab;
    }
}
