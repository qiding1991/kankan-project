package com.kankan.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.kankan.util.GsonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.kankan.dao.entity.WorkEntity;
import com.kankan.dao.mapper.WorkMapper;
import com.kankan.module.KankanWork;
import com.kankan.param.tab.TabPageInfo;
import org.springframework.util.CollectionUtils;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-03
 */
@Service
public class KankanWorkService {


  @Resource
  private WorkMapper workMapper;

  public void addWork(KankanWork kankanWork) {
    WorkEntity workEntity = new WorkEntity();
    BeanUtils.copyProperties(kankanWork, workEntity);
    workMapper.insert(workEntity);
    kankanWork.setId(workEntity.getId());
  }

  public List<KankanWork> findArticle(TabPageInfo pageInfo) {
    List<WorkEntity> workEntityList = workMapper.findArticle(pageInfo.getOffset(), pageInfo.getSize());
    return workEntityList.stream().map(KankanWork::parseEntity).collect(Collectors.toList());
  }

  public List<KankanWork> findVideo(TabPageInfo pageInfo) {
    List<WorkEntity> workEntityList = workMapper.findVideo(pageInfo.getOffset(), pageInfo.getSize());
    return workEntityList.stream().map(KankanWork::parseEntity).collect(Collectors.toList());
  }

  public KankanWork findVideo(String itemId) {
    WorkEntity entity = workMapper.findById(itemId);
    return KankanWork.parseEntity(entity);
  }

  public KankanWork findArticle(String itemId) {
    WorkEntity entity = workMapper.findById(itemId);
    return KankanWork.parseEntity(entity);
  }

  public List<KankanWork> findUserWork(String userId) {
    List<WorkEntity> infoList = workMapper.findUserWork(userId);
    return infoList.stream().map(WorkEntity::parse).collect(Collectors.toList());
  }

  public List<KankanWork> findUserWork(String userId, Integer workType) {
    List<WorkEntity> infoList = workMapper.findUserWorkByType(userId, workType);
    return infoList.stream().map(WorkEntity::parse).collect(Collectors.toList());
  }


  public List<KankanWork> findAllWork() {
    List<WorkEntity> infoList = workMapper.findAllWork();
    return infoList.stream().map(WorkEntity::parse).collect(Collectors.toList());
  }

  public KankanWork resourceWork(String resourceId) {
    WorkEntity workEntity = workMapper.findByResourceId(resourceId);
    return workEntity.parse();
  }

  public KankanWork findByResourceId(String resourceId) {
    WorkEntity workEntity = workMapper.findByResourceId(resourceId);
    return KankanWork.parseEntity(workEntity);
  }

  public void auditWork(String id, Integer status) {
    workMapper.updateWork(id, status);
  }

  public void setHot(String id, Integer hotStatus) {
    workMapper.setHot(id, hotStatus);
  }


  public void updateHeaderStatus(String id, Integer headerStatus) {
    workMapper.updateWork(id, headerStatus);
  }

  public List<WorkEntity> findArticleTitle(List<String> articleIdList) {
    if (CollectionUtils.isEmpty(articleIdList)) {
      return new ArrayList<>();
    }
    String articleIds = GsonUtil.toGson(articleIdList);
    articleIds = articleIds.substring(1, articleIds.length() - 1);
    List<WorkEntity> titleList = workMapper.findWorkTitle(articleIds);
    return titleList;
  }

  public List<KankanWork> findArticle(String offset, Integer size, String keyword) {
    List<WorkEntity> infoList = workMapper.findArticleByKeyword(offset, size, keyword);
    return infoList.stream().map(WorkEntity::parse).collect(Collectors.toList());
  }

  public void delete(String id) {
      workMapper.delete(id);
  }

  public void updateWork(WorkEntity updateInfo) {
      workMapper.save(updateInfo);
  }

  public WorkEntity findById(String id) {
    return  workMapper.findById(id);
  }
}
