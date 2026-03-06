package com.itheima.backend.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("user")
public class User implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("userAccount")
    private String userAccount;

    @TableField("userPassword")
    private String userPassword;

    @TableField("userName")
    private String userName;

    @TableField("userAvatar")
    private String userAvatar;

    @TableField("userProfile")
    private String userProfile;

    @TableField("userRole")
    private String userRole;

    @TableField("createTime")
    private Date createTime;

    @TableField("editTime")
    private Date editTime;

    @TableField("updateTime")
    private Date updateTime;

    @TableField("isDelete")
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}