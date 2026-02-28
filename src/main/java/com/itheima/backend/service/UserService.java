package com.itheima.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itheima.backend.model.dto.user.UserQueryRequest;
import com.itheima.backend.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.backend.model.vo.LoginUserVO;
import com.itheima.backend.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author 26228
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2026-02-27 16:35:10
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @return 新用户 id
     */
    /**
     * 用户注册
     *
     *
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);
    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);

    String getEncryptPassword(String userPassword);

    LoginUserVO getLoginUserVO(User user);
    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);
    /**
     * 用户注销
     */
    boolean userLogout(HttpServletRequest request);
    /*
    获得脱敏后的登录用户信息
     */
    UserVO getUserVO(User user);
    /*
    获得脱敏后的用户列表
     */
    List<UserVO>  getUserVOList(List<User> userList);
    /**
     * 是否为管理员
     *
     * @param user
     * @return
     */
    boolean isAdmin(User user);

}
