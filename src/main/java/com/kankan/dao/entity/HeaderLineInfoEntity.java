package com.kankan.dao.entity;

import com.kankan.module.HeaderLine;

import com.kankan.param.headline.HeaderLineInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.beans.BeanUtils;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HeaderLineInfoEntity extends BaseEntity {
    private Long tabId;
    private String title;
    private String picture;

    public HeaderLineInfoEntity(HeaderLine headerLine) {
        BeanUtils.copyProperties(headerLine, this);
    }

    public HeaderLine parse() {
        HeaderLine headerLine = HeaderLine.builder()
                .id(getId()).title(title).tabId(tabId)
                .picture(picture).build();
        return headerLine;
    }
}
