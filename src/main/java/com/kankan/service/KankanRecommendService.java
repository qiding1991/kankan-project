package com.kankan.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kankan.dao.entity.KankanRecommendEntity;
import com.kankan.dao.entity.KankanTypeEntity;
import com.kankan.dao.mapper.KankanRecommendMapper;
import com.kankan.module.KankanRecommend;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 * 专栏 推荐列表
 */
@Service
public class KankanRecommendService {

    @Resource
    private KankanRecommendMapper recommendMapper;

    public List<KankanRecommend> findAll() {
        List<KankanRecommendEntity> infoList = recommendMapper.findAll();
        return infoList.stream().map(KankanRecommendEntity::toKankanRecommend).collect(Collectors.toList());
    }
    public KankanRecommend getMyKankanRecommend(String userId){
      KankanRecommendEntity entity= recommendMapper.findKankanRecommend(userId);
      if(entity==null){
        return null;
      }
      return entity.toKankanRecommend();
    }

    public void addRecommend(KankanRecommend kankanRecommend) {
        KankanRecommendEntity entity=new KankanRecommendEntity(kankanRecommend);
        recommendMapper.insert(entity);
        kankanRecommend.setId(entity.getId());
    }

    public void remove(KankanRecommend kankanRecommend) {
        KankanRecommendEntity entity=new KankanRecommendEntity(kankanRecommend);
        recommendMapper.deleteByUserId(entity);
    }

    public void update(KankanRecommend kankanRecommend) {
        KankanRecommendEntity entity=new KankanRecommendEntity(kankanRecommend);
        recommendMapper.updateByUserId(entity);
    }
}
