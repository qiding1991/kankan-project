package com.kankan.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kankan.dao.entity.FollowEntity;
import com.kankan.dao.mapper.FollowMapper;
import com.kankan.module.Follow;
import com.kankan.module.PageQuery;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */
@Service
public class FollowService {
    @Resource
    private FollowMapper followMapper;

    public List<Follow> findUserFollow(Long userId, PageQuery pageQuery) {
        List<FollowEntity> followEntityList = followMapper.findUserFollow(userId, pageQuery.getOffset(), pageQuery.getSize());
        return followEntityList.stream().map(Follow::parseEntity).collect(Collectors.toList());
    }

    public Boolean exists(Long userId, Long followId) {
        return 1 == followMapper.findCount(userId, followId);
    }
}
