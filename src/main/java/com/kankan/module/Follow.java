package com.kankan.module;

import java.util.List;

import com.kankan.dao.entity.FollowEntity;
import com.kankan.service.FollowService;
import com.kankan.vo.FollowVo;

import lombok.Builder;
import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */
@Builder
@Data
public class Follow {
    private String userId;
    private String followId;

    public List<Follow> list(FollowService followService, PageQuery pageQuery) {
        return followService.findUserFollow(userId, pageQuery);
    }

    public static Follow parseEntity(FollowEntity followEntity) {
        return Follow.builder().userId(followEntity.getUserId()).followId(followEntity.getFollowId()).build();
    }

    public FollowVo toVo() {
        //TODO
        return null;
    }

    public void cancel(FollowService followService) {
       followService.cancel(this);
    }

    public void add(FollowService followService) {
      followService.createNew(this);
    }

    public Boolean exists(FollowService followService) {
          return followService.exists(this.userId,this.followId);
    }
}
