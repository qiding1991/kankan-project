package com.kankan.vo.tab;

import com.kankan.dao.entity.WorkEntity;
import org.apache.commons.lang3.ObjectUtils;

import com.kankan.constant.EnumItemType;
import com.kankan.module.KankanUser;
import com.kankan.module.KankanWork;
import com.kankan.module.MediaResource;

import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-04
 */
@Data
public class ArticleItemVo extends TabItemVo {

    //标题
    private String title;

    // 作者名称 | 文章
    private String subTitle;

    //图片地址
    private String picture;

    //评论总数
    private Integer readCount;

  private Integer auditStatus;


  public ArticleItemVo(KankanWork article, KankanUser writer, MediaResource resource) {
          this.title=article.getTitle();
          this.subTitle=writer.getUserName()+"|"+"专栏";
          this.picture=article.getPicture();
          this.readCount= ObjectUtils.defaultIfNull(resource.getReadCount(),0);
          this.setResourceId(article.getResourceId());
          this.setItemId(article.getId());
          this.setItemType(EnumItemType.ARTICLE.getCode());
          this.setPublishTime(article.getPublishTime());

    }

  public ArticleItemVo(WorkEntity workEntity) {
    this.title=workEntity.getTitle();
    this.setItemId(workEntity.getId());
    this.setItemType(EnumItemType.ARTICLE.getCode());
    this.setResourceId(workEntity.getResourceId());
  }
}

