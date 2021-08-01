package com.kankan.dao.mapper;

import com.kankan.dao.entity.KankanUserEntity;
//import com.kankan.module.KankanUser;

//import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Select;
//import org.apache.ibatis.annotations.Update;

import java.util.List;

//@Mapper
public interface KankanUserMapper {
  void insert(KankanUserEntity entity);

  KankanUserEntity findByUserId(String userId);

//  @Select("select * from kankan_user")
  List<KankanUserEntity> findAll();

  List<KankanUserEntity> findAllOrderByTime(Boolean desc);

//  @Select("select * from kankan_user where id < #{offset} limit #{size}")
  List<KankanUserEntity> findByPage(String offset, Integer size);


//  @Select("select * from kankan_user where user_type=#{userType}")
  List<KankanUserEntity> findByType(String userType);

//  @Update("update kankan_user set user_type=#{userType} where user_id=#{userId}")
  void updateUserType(String userId, String userType);

//  @Update("update kankan_user set recommend_status=#{recommendStatus} where user_id=#{userId}")
  void updateUserRecommendStatus(String userId, Integer recommendStatus);

//  @Update("update kankan_user set follow_count=follow_count+${followCount} where user_id=#{userId}")
  void updateFollowCount(String userId, Integer followCount);

//  @Update("update kankan_user set fans_count=fans_count+${fansCount} where user_id=#{userId}")
  void updateFansCount(String userId, Integer fansCount);

//  @Update("update kankan_user set read_count=read_count+${readCount} where user_id=#{userId}")
  void updateReadCount(String userId, Integer readCount);

//  @Update("update kankan_user set white_status=#{whiteStatus}   where user_id=#{userId}")
  void updateWhiteStatus(String userId, Integer whiteStatus);

//  @Select("select * from kankan_user order by  #{orderField} desc limit #{limit}")
  List<KankanUserEntity> findHotUser(String orderField, Integer limit);

//  @Select("select * from  kankan_user where user_name like concat('%',#{keyword},'%')  limit #{offset},#{size}")
  List<KankanUserEntity> findByKeyword(String offset, Integer size, String keyword);
}
