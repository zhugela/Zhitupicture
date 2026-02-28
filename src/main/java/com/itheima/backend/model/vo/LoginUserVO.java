package com.itheima.backend.model.vo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
;

@Data
public class LoginUserVO {

    private Long id;


    private String userAccount;



    private String userName;


    private String userRole;

    private static final long serialVersionUID = 3191241716373120793L;


}