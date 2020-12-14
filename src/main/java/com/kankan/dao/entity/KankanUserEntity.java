package com.kankan.dao.entity;

import com.kankan.module.KankanUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.units.qual.K;
import org.springframework.beans.BeanUtils;

@NoArgsConstructor
@Data
public class KankanUserEntity extends BaseEntity {
    private String userName;
    private Long userId;
    private Long userType;
    private String remark;
    private Long fansCount;
    private Long followCount;
    private Long readCount;

    public KankanUserEntity(KankanUser kankanUser) {
        BeanUtils.copyProperties(kankanUser, this);
    }

    public KankanUser parse() {
        KankanUser kankanUser = KankanUser.builder().build();
        BeanUtils.copyProperties(this, kankanUser);
        return kankanUser;
    }
}
