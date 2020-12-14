package com.kankan.service;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.kankan.dao.entity.KankanTypeEntity;
import com.kankan.dao.mapper.KankanTypeMapper;
import com.kankan.module.KankanType;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */
@Service
public class KankanTypeService {

    @Resource
    private KankanTypeMapper typeMapper;

    public void addType(KankanType kankanType) {
        typeEntity(kankanType, typeMapper::insert);
    }

    public void updateType(KankanType kankanType) {
        typeEntity(kankanType, typeMapper::update);
    }

    public void typeEntity(KankanType kankanType, Consumer<KankanTypeEntity> consumer) {
        KankanTypeEntity entity = new KankanTypeEntity();
        BeanUtils.copyProperties(kankanType, entity);
        consumer.accept(entity);
    }


    public List<KankanTypeEntity> findAll() {
        List<KankanTypeEntity> entityList = typeMapper.findAll();
        return entityList;
    }

    public void delById(Long id) {
        typeMapper.delById(id);
    }
}
