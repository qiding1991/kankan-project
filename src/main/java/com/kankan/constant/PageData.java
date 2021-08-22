package com.kankan.constant;

import java.util.List;

import lombok.Data;
import org.springframework.cglib.core.CollectionUtils;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-08
 */
@Data
public class PageData <T>{
    private List<T>  infoList;
    private Boolean hasMore;
    private Long offset;

    public  static <T>  PageData<T> pageData(List<T> infoList,Integer size){
        PageData pageData=new PageData();
        if(infoList.size()<size){
            pageData.hasMore=false;
        }
        pageData.infoList=infoList;

        if(infoList.size()!=0){

        }


        return pageData;
    }



}
