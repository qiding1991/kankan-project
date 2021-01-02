package com.kankan.service;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.kankan.dao.entity.ThumpEntity;
import com.kankan.dao.mapper.ThumpMapper;
import com.kankan.module.resouce.ResourceThump;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
@Service
public class ThumpService {

    @Resource
    private ThumpMapper thumpMapper;

    public void saveThump(ResourceThump resourceThump) {
        ThumpEntity entity = new ThumpEntity();
        BeanUtils.copyProperties(resourceThump, entity);
        thumpMapper.insert(entity);
    }

    public void cancelThump(ResourceThump resourceThump) {
      ThumpEntity entity = new ThumpEntity();
      BeanUtils.copyProperties(resourceThump, entity);
      thumpMapper.remove(entity);
    }
}
