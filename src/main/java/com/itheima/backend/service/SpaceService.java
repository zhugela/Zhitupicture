package com.itheima.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itheima.backend.model.dto.space.SpaceAddRequest;
import com.itheima.backend.model.dto.space.SpaceQueryRequest;
import com.itheima.backend.model.dto.space.analyze.SpaceUsageAnalyzeRequest;
import com.itheima.backend.model.entity.Picture;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.backend.model.entity.Space;
import com.itheima.backend.model.entity.User;
import com.itheima.backend.model.vo.PictureVO;
import com.itheima.backend.model.vo.SpaceVO;
import com.itheima.backend.model.vo.space.analyze.SpaceUsageAnalyzeResponse;

import javax.servlet.http.HttpServletRequest;

/**
* @author 26228
* @description 针对表【space(图片空间)】的数据库操作Service
* @createDate 2026-03-06 14:24:04
*/
public interface SpaceService extends IService<Space> {
    /**
     * 校验空间数据
     *
     * @param space 空间实体
     */
    void validSpace(Space space, boolean add);

    /**
     * 获取查询条件包装器
     *
     * @param spaceQueryRequest 空间查询请求
     * @return QueryWrapper 查询条件
     */
    QueryWrapper<Space> getQueryWrapper(SpaceQueryRequest spaceQueryRequest);

    /**
     * 获取空间 VO 对象
     *
     * @param space 空间实体
     * @param request HTTP 请求
     * @return 空间 VO 对象
     */
    SpaceVO getSpaceVO(Space space, HttpServletRequest request);

    /**
     * 分页获取空间 VO 列表
     *
     * @param spacePage 空间分页对象
     * @param request HTTP 请求
     * @return 空间 VO 分页结果
     */
    Page<SpaceVO> getSpaceVOPage(Page<Space> spacePage, HttpServletRequest request);

    long addSpace(SpaceAddRequest spaceAddRequest, User loginUser);

    /**
     * 根据空间等级填充空间限额
     *
     * @param space 空间实体
     */
    void fillSpaceBySpaceLevel(Space space);
    /**
     * 校验空间权限
     *
     * @param loginUser
     * @param space
     */
    void checkSpaceAuth(User loginUser, Space space);

}
