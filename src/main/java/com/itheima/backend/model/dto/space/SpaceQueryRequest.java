package com.itheima.backend.model.dto.space;

import com.itheima.backend.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
@EqualsAndHashCode(callSuper = true)
@Data
public class SpaceQueryRequest extends PageRequest implements Serializable {
    /*
     * id
     */
    private Long id;

    /*
     * 用户id
     */
    private Long userId;

    /*
     * 名称

     */
    private String spaceName;
    /*
     * 空间级别
     */
    private Integer spaceLevel;
    private static final long serialVersionUID = 1L;
}
