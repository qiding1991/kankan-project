package com.kankan.dao.entity;

import com.kankan.module.KankanComment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CommentEntity extends BaseEntity {
    private String resourceId;
    private Long parentId = 0L;
    private String commentText;
    private Long userId;
    private Integer thumpCount=0;

    public KankanComment parse() {
        return KankanComment.builder()
                .userId(userId)
                .id(this.getId())
                .createTime(this.getCreateTime())
                .thumpCount(ObjectUtils.defaultIfNull(this.getThumpCount(),0))
                .commentText(commentText).resourceId(resourceId).parentId(parentId).build();
    }
}

