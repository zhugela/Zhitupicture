package com.itheima.backend.model.dto.space.analyze;

import lombok.Data;

import java.io.Serializable;

@Data
public class SpaceAnalyzeRequest implements Serializable {

    /**
     * 空间 id
     */
    private Long spaceId;

    /**
     * 是否查询全部空间
     */
    private boolean queryAll;

    /**
     * 是否查询公共图库
     */
    private boolean queryPublic;

    private static final long serialVersionUID = 1L;
}