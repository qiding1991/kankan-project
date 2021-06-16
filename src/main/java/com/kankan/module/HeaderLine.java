package com.kankan.module;

import com.kankan.dao.entity.HeaderLineInfoEntity;
import com.kankan.service.HeaderLineService;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HeaderLine {

    private String picture;
    private String title;
    private String tabId;
    private String id;

    public static HeaderLine parseEntity(HeaderLineInfoEntity entity) {
        return HeaderLine.builder().id(entity.getId()).picture(entity.getPicture()).title(entity.getTitle()).tabId(entity.getTabId())
                .build();
    }

    public void creatHeaderLine(HeaderLineService headerLineService) {
        headerLineService.createHeadLine(this);
    }

    public List<HeaderLine> findHeaderLine(HeaderLineService headerLineService) {
        List<HeaderLine> infoList = headerLineService.findHeaderLine();
        return infoList;
    }
}
