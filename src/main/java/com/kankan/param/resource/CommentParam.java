package com.kankan.param.resource;

import com.kankan.module.MediaResource;
import com.kankan.module.KankanComment;

import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-06
 */

@Data
public class CommentParam {
    private Long userId;
    private String resourceId;
    private String commentText;

    public KankanComment toComment() {
        return toComment(0L);
    }

    public KankanComment toComment(Long parentId) {
        return KankanComment.builder().commentText(commentText).userId(userId).parentId(parentId)
                .resourceId(resourceId).build();
    }

    public MediaResource toResource() {
        return MediaResource.builder().resourceId(resourceId).build();
    }
}
