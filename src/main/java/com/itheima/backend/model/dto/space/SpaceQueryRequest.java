package com.itheima.backend.model.dto.space;

import com.baomidou.mybatisplus.annotation.TableField;
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
     /*
     * 空间类型：0-私有 1-团队
     */
    private Integer spaceType;

    @TableField(exist = false)
        private static final long serialVersionUID = 1L;
}
