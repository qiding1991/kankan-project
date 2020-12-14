package com.kankan.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kankan.dao.entity.FollowEntity;
import org.apache.ibatis.annotations.Select;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */
@Mapper
public interface FollowMapper {
    List<FollowEntity> findUserFollow(Long userId, Long offset, Integer size);

    @Select("select count(1) where user_id=#{userId} and follow_id=#{followId}")
    Integer findCount(Long userId, Long followId);
}
