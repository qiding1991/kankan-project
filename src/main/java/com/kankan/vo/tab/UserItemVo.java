package com.kankan.vo.tab;

import com.kankan.module.KankanUser;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
public class UserItemVo  extends TabItemVo{
    private String userName;
    private Long userId;
    private String remark;
    private String picture;
    private Long fansCount;
    private Long followCount;
    private Long readCount;

    public UserItemVo(KankanUser user){
        BeanUtils.copyProperties(user,this);
    }
}
