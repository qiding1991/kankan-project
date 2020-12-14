package com.kankan.vo.tab;

import com.kankan.module.KankanAd;

import lombok.Data;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-04
 */
@Data
public class AdItemVo extends TabItemVo {
    private String title;
    private String picture;
    private String subTitle="推广";

    public AdItemVo(KankanAd ad) {
        this.title=ad.getTitle();
        this.picture=ad.getPicture();
    }
}
