package com.itheima.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itheima.backend.model.dto.spaceuser.SpaceUserAddRequest;
import com.itheima.backend.model.dto.spaceuser.SpaceUserQueryRequest;
import com.itheima.backend.model.entity.SpaceUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.backend.model.vo.SpaceUserVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author 26228
* @description 针对表【space_user(空间用户关联)】的数据库操作Service
* @createDate 2026-03-08 18:21:50
*/
public interface SpaceUserService extends IService<SpaceUser> {

    long addSpaceUser(SpaceUserAddRequest spaceUserAddRequest);

    void validSpaceUser(SpaceUser spaceUser, boolean add);

    QueryWrapper<SpaceUser> getQueryWrapper(SpaceUserQueryRequest spaceUserQueryRequest);

    SpaceUserVO getSpaceUserVO(SpaceUser spaceUser, HttpServletRequest request);

    java.util.List<SpaceUserVO> getSpaceUserVOList(java.util.List<SpaceUser> spaceUserList);
}
