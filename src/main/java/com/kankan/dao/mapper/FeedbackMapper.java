package com.kankan.dao.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.kankan.dao.entity.FeedbackEntity;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */
@Mapper
public interface FeedbackMapper {

     @Insert("insert into feedback(id,user_id,feedback,status,create_time,update_time) values (#{id},#{userId},#{feedback},#{status},#{createTime},#{updateTime})")
     void insert(FeedbackEntity feedbackEntity);

}
