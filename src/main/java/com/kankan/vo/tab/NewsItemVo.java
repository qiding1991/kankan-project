package com.kankan.vo.tab;

import com.kankan.constant.EnumItemType;
import com.kankan.module.MediaResource;
import com.kankan.module.News;
import com.kankan.module.Tab;

import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-04
 */
@Data
public class NewsItemVo extends TabItemVo {
    /**
     * 图片
     */
    private String picture;

    /**
     * 标题
     */
    private String title;

    /**
     * 新闻地点
     */
    private String subTitle;


    private Integer readCount;


    public NewsItemVo(Tab tab, News news, MediaResource resource) {
        this.picture=news.getPicture();
        this.title=news.getTitle();
        this.setItemType(EnumItemType.NEWS.getCode());
        this.subTitle=tab.getTabName();
        this.setResourceId(news.getResourceId());
        this.setItemId(news.getId());
        this.readCount= ObjectUtils.defaultIfNull(resource.getReadCount(),0);
        this.setPublishTime(news.getCreateTime());
    }
}
