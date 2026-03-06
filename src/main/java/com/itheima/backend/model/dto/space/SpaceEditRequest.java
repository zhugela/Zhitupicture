package com.itheima.backend.model.dto.space;

import lombok.Data;

import java.io.Serializable;

@Data
public class SpaceEditRequest implements Serializable {
    /**
     * 空间id
     */
    private Long id;

    /**
     * 名称
     */
    private String spaceName;

     /**
      * 序列号
      *  */
     private static final long serialVersionUID = 1L;
}
