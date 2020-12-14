package com.kankan.dao.entity;

import com.kankan.module.KankanComment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    public KankanComment parse() {
        return KankanComment.builder().commentText(commentText).resourceId(resourceId).parentId(parentId).build();
    }
}

