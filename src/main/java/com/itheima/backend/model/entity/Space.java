package com.itheima.backend.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 图片空间实体类
 */
@Data
@TableName("space")
public class Space implements Serializable {

    /**
     * 表的主键 ID，自增列
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 空间名称
     */
    @TableField("spaceName")
    private String spaceName;

    /**
     * 空间等级
     */
    @TableField("spaceLevel")
    private Integer spaceLevel;

    /**
     * 空间图片的最大总大小
     */
    @TableField("maxSize")
    private Long maxSize;

    /**
     * 空间的最大图片数量
     */
    @TableField("maxCount")
    private Integer maxCount;

    /**
     * 当前空间下的图片总大小
     */
    @TableField("totalSize")
    private Long totalSize;

    /**
     * 创建用户 Id
     */
    @TableField("userId")
    private Long userId;

    /**
     * 创建时间
     */
    @TableField("createTime")
    private Date createTime;

    /**
     * 编辑时间
     */
    @TableField("editTime")
    private Date editTime;

    /**
     * 更新时间
     */
    @TableField("updateTime")
    private Date updateTime;

    /**
     * 是否删除
     */
    @TableField("isDelete")
    @TableLogic
    private Integer isDelete;

    /**
     * 空间 id
     */
    @TableField("spaceId")
    private Long spaceId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
