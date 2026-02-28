package com.itheima.backend.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("user")
public class User {
    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("userAccount")
    private String userAccount;

    @TableField("userPassword")
    private String userPassword;

    @TableField("userName")
    private String userName;

    @TableField("userRole")
    private String userRole;   // ✅ 新增

    @TableField("isDelete")
    @TableLogic
    private Integer isDelete;
}