package com.itheima.backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.backend.model.entity.User;
import com.itheima.backend.service.UserService;
import com.itheima.backend.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 26228
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2026-02-27 16:35:10
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




