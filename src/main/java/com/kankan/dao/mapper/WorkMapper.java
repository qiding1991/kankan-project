package com.kankan.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.kankan.dao.entity.KankanTypeEntity;
import com.kankan.dao.entity.WorkEntity;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-03
 */
@Mapper
public interface WorkMapper {
  void insert(WorkEntity workEntity);

  /**
   * 获取所有文章
   *
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

  @Update("update work_info set audit_status=#{auditStatus} where id =#{id}")
  void updateWork(Long id, Integer auditStatus);


  @Update("update work_info set hot_status=#{hotStatus} where id=#{workId}")
  void setHot(Long workId, Integer hotStatus);

  @Update("update work_info set head_status=#{headStatus} where id=#{workId}")
  void setHeader(Long workId, Integer headStatus);


}
