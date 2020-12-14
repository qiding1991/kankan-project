package com.kankan.vo.detail;
import lombok.Builder;
import lombok.Data;

@Data
public class BaseDetailVo {
    private String resourceId;
    private Integer itemType;
    private String content;
}
