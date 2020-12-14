package com.kankan.vo;

import java.util.ArrayList;
import java.util.List;

import com.kankan.module.KankanComment;
import com.kankan.module.KankanUser;
import com.kankan.service.KankanUserService;
import lombok.Data;
import org.springframework.beans.BeanUtils;

/**
 * @author <qiding@kuaishou.com>
 * Created on 2020-12-12
 */
@Data
public class KankanCommentVo {
      private Long id;
      private Long parentId;
      private String resourceId;
      private Integer thumpCount;
      private String commentText;
      private Long userId;
      private String userName;
      private Long creatTime;
      private List<KankanCommentVo>  children=new ArrayList<>();



      public KankanCommentVo(KankanComment kankanComment, KankanUserService userService) {
            BeanUtils.copyProperties(kankanComment,this);
            KankanUser user= userService.findUser(userId);
            this.userName=user.getUserName();
      }
}
