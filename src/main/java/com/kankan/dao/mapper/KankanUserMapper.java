package com.kankan.dao.mapper;

import com.kankan.dao.entity.KankanUserEntity;
import com.kankan.module.KankanUser;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface KankanUserMapper {
     void insert(KankanUserEntity entity);

    KankanUserEntity findByUserId(Long userId);

    @Select("select * from kankan_user")
    List<KankanUserEntity> findAll();

    @Select("select * from kankan_user where id < #{offset} limit #{size}")
    List<KankanUserEntity> findByPage(Long offset, Integer size);


    @Select("select * from kankan_user where user_type=#{userType}")
    List<KankanUserEntity> findByType(Long userType);

    @Update("update kankan_user set user_type=#{userType} where user_id=#{userId}")
    void updateUserType(Long userId, Long userType);
    @Update("update kankan_user set recommend_status=#{recommendStatus} where user_id=#{userId}")
    void updateUserRecommendStatus(Long userId, Integer recommendStatus);

    @Update("update kankan_user set follow_count=follow_count+${followCount}")
    void updateFollowCount(Long userId, Integer followCount);
}
