package com.kankan.service;

import com.kankan.dao.entity.KankanUserEntity;
import com.kankan.dao.mapper.KankanUserMapper;
import org.springframework.stereotype.Service;

import com.kankan.module.KankanUser;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-05
 */
@Service
public class KankanUserService {

    @Resource
    private KankanUserMapper kankanUserMapper;


    public KankanUser findUser(Long userId) {
        KankanUserEntity userEntity = kankanUserMapper.findByUserId(userId);
        final KankanUser user = new KankanUser(userEntity);
        return user;
    }

    public void createUser(KankanUser kankanUser) {
        KankanUserEntity entity = new KankanUserEntity(kankanUser);
        kankanUserMapper.insert(entity);
    }

    public List<KankanUser> findAll() {
        List<KankanUserEntity> userEntityList = kankanUserMapper.findAll();
        return userEntityList.stream().map(KankanUserEntity::parse).collect(Collectors.toList());
    }

    public  List<KankanUser>  findUserByPageInfo(Long offset, Integer size) {
        List<KankanUserEntity> userEntityList = kankanUserMapper.findByPage(offset,size);
        return userEntityList.stream().map(KankanUserEntity::parse).collect(Collectors.toList());
    }
}
