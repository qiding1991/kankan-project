package com.kankan.param.tab;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

import com.kankan.module.Tab;

import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-02
 */
@Data
public class TabAdd {

    @NotNull(message = "不能为空")
    @NotBlank(message = "tabName,不能为空")
    private String tabName;

    @NotNull(message = "tabType,不能为空")
    @Min(value = 0, message = "最小值为0")
    @Max(value = 128, message = "最大值为128")
    private Integer tabType;

    @NotNull(message = "tabOrder,不能为空")
    @Min(value = 0, message = "最小值为0")
    @Max(value = 128, message = "最大值为128")
    private Integer tabOrder;

    public Tab toTab() {
        Tab tab = new Tab();
        BeanUtils.copyProperties(this, tab);
        return tab;
    }
}
