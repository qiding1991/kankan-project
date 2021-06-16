package com.kankan.param;

import com.kankan.module.KankanUser;
import lombok.Data;

@Data
public class KankanUserParam {
    private String userId;
    private String userType;
    private String remark;
    private String userName;

    public KankanUser toUser() {
        return KankanUser.builder().userId(userId).userName(userName).userType(userType).remark(remark).build();
    }
}
