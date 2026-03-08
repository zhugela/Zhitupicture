package com.itheima.backend.model.dto.space;

import lombok.Data;

@Data
public class SpaceAddRequest {
    /**
     * 空间名称
     *  */
    private String spaceName;

    /*
    空间级别 0普通版 1专业版 2旗舰版
     */
    private Integer spaceLevel;
    /*
    序列 号
     */
    private Integer spaceType;
    private static final long serialVersionUID = 1L;
}
