package com.kankan.constant;

/**
 * @author <qiding@qiding.com>
 * Created on 2020-12-02
 */
public enum EnumItemType {
    UNKNOW(0, "未知"),
    NEWS(1, "新闻"),
    AD(2, "广告"),
    ARTICLE(3, "专栏"),
    VIDEO(4, "视频"),
    HEADER_LINE_ITEM(5, "头条-item"),
    HEADER_LINE_GROUP(6, "头条组"),
    KAN_KAN_USER(7,"看看号");

    EnumItemType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    private int code;
    private String message;

    public static EnumItemType getItem(Integer itemType) {
        for (EnumItemType type : EnumItemType.values()) {
            if (type.code == itemType) {
                return type;
            }
        }
        return UNKNOW;
    }
}
