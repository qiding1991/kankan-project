package com.kankan.service;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.kankan.dao.entity.AdEntity;
import com.kankan.dao.mapper.KankanAdMapper;
import com.kankan.module.KankanAd;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-03
 */
@Service
public class KankanAdService {

    @Resource
    KankanAdMapper kankanAdMapper;

    public void createAd(KankanAd kankanAd) {
        AdEntity adEntity = new AdEntity();
        BeanUtils.copyProperties(kankanAd, adEntity);
        kankanAdMapper.insert(adEntity);
        kankanAd.setId(adEntity.getId());
    }

    public KankanAd findAd(Long itemId) {
        AdEntity entity = kankanAdMapper.findById(itemId);
        return entity.parse();
    }

    public List<KankanAd> findAll() {
        List<AdEntity> infoList = kankanAdMapper.findAll();
        return infoList.stream().map(AdEntity::parse).collect(Collectors.toList());
    }
}
