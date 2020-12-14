package com.kankan.constant;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-04
 */
public enum EnumTabType {

    UNKNOW(0,"未知类型"),
    HOT(1, "热点"),
    NEWS(2, "新闻"),
    ARTICLE(3, "专栏"),
    VIDEO(4, "视频"),
    KANKAN_USER(5, "看看号");

    public static EnumTabType get(Integer code){
        for (EnumTabType tabType:EnumTabType.values()){
            if(tabType.code==code){
                return tabType;
            }
        }
        return UNKNOW;
    }


    EnumTabType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    private Integer code;
    private String name;
}
