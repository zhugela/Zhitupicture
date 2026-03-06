package com.itheima.backend.model.enums;

import lombok.Getter;

@Getter
public enum SpaceLEvelEnum {
    COMMON("普通版", 0,100,100L*1024*1024),
    PRO("专业版", 1,1000,1000L*1024*1024),
    ENTERPRISE("企业版", 2,10000,10000L*1024*1024);

    private final String text;

    private final int value;

    private final Integer maxCount;

    private final Long maxSize;


    /*
        * 构造函数
     */
    SpaceLEvelEnum(String text, int value, Integer maxCount, Long maxSize) {
        this.text = text;
        this.value = value;
        this.maxCount = maxCount;
        this.maxSize = maxSize;
}

    /**
     * 根据 value 获取枚举
     */
    public static SpaceLEvelEnum getEnumByValue(Integer value) {
        if (value == null) {
            return null;
        }
        for (SpaceLEvelEnum spaceLEvelEnum : SpaceLEvelEnum.values()) {
            if (spaceLEvelEnum.value == value) {
                return spaceLEvelEnum;
            }
        }
        return null;
    }
}