package com.itheima.backend.model.enums;

import lombok.Getter;

@Getter
public enum SpaceLevelEnum {
    COMMON("普通版", 0,100,100L*1024*1024),
    PRO("专业版", 1,1000,1000L*1024*1024),
    ENTERPRISE("企业版", 2,10000,10000L*1024*1024),
    FLAGSHIP("旗舰版", 3,100000,100000L*1024*1024);

    private final String text;

    private final int value;

    private final int maxCount;

    private final Long maxSize;


    /*
        * 构造函数
     */
    SpaceLevelEnum(String text, int value, int maxCount, Long maxSize) {
        this.text = text;
        this.value = value;
        this.maxCount = maxCount;
        this.maxSize = maxSize;
}

    /**
     * 根据 value 获取枚举
     */
    public static SpaceLevelEnum getEnumByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (SpaceLevelEnum spaceLEvelEnum : SpaceLevelEnum.values()) {
            if (spaceLEvelEnum.value == value) {
                return spaceLEvelEnum;
            }
        }
        return null;
    }
}