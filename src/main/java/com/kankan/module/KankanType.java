package com.kankan.module;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.kankan.dao.entity.KankanTypeEntity;
import com.kankan.service.KankanTypeService;
import com.kankan.vo.KankanTypeVo;

import lombok.Builder;
import lombok.Data;
import org.checkerframework.checker.units.qual.K;
import org.springframework.beans.BeanUtils;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */
@Builder
@Data
public class KankanType {

    private Long id;
    private String typeDesc;
    private Integer typeOrder;

    public void addType(KankanTypeService typeService) {
        typeService.addType(this);
    }

    public void updateType(KankanTypeService typeService) {
        typeService.updateType(this);
    }

    public List<KankanType> findAll(KankanTypeService typeService) {
        List<KankanTypeEntity> entityList= typeService.findAll();
        return entityList.stream()
                .map(KankanTypeEntity::parse)
                .sorted(Comparator.comparing(KankanType::getTypeOrder))
                .collect(Collectors.toList());
    }

    public KankanTypeVo toVo() {
        KankanTypeVo kankanTypeVo=new KankanTypeVo();
        BeanUtils.copyProperties(this,kankanTypeVo);
        return kankanTypeVo;
    }

    public void remove(KankanTypeService typeService) {
          typeService.delById(this.id);
    }
}
