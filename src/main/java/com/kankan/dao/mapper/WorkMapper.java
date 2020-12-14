package com.kankan.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kankan.dao.entity.KankanTypeEntity;
import com.kankan.dao.entity.WorkEntity;
import org.apache.ibatis.annotations.Select;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-03
 */
@Mapper
public interface WorkMapper {
    void insert(WorkEntity workEntity);

    /**
     * 获取所有文章
     * @param offset
     * @param size
     */
    List<WorkEntity> findArticle(Long offset, Integer size);

    List<WorkEntity> findVideo(Long offset, Integer size);

    @Select("select * from work_info where id=#{itemId}")
    WorkEntity findById(Long itemId);

    @Select("select * from work_info where user_id=#{userId} ")
    List<WorkEntity> findUserWork(Long userId);

    @Select("select * from work_info")
    List<WorkEntity> findAllWork();

    @Select("select * from work_info where resource_id=#{resourceId}")
    WorkEntity findByResourceId(String resourceId);


    @Select("select * from work_info where user_id=#{userId}  and type=#{workType}")
    List<WorkEntity> findUserWorkByType(Long userId, Integer workType);
}
