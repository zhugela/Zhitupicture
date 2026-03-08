package com.itheima.backend.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itheima.backend.exception.BusinessException;
import com.itheima.backend.exception.ErrorCode;
import com.itheima.backend.exception.ThrowUtils;
import com.itheima.backend.mapper.SpaceUserMapper;
import com.itheima.backend.model.dto.spaceuser.SpaceUserAddRequest;
import com.itheima.backend.model.dto.spaceuser.SpaceUserQueryRequest;
import com.itheima.backend.model.entity.Space;
import com.itheima.backend.model.entity.SpaceUser;
import com.itheima.backend.model.entity.User;
import com.itheima.backend.model.enums.SpaceRoleEnum;
import com.itheima.backend.model.vo.SpaceUserVO;
import com.itheima.backend.model.vo.SpaceVO;
import com.itheima.backend.model.vo.UserVO;
import com.itheima.backend.service.SpaceService;
import com.itheima.backend.service.SpaceUserService;
import com.itheima.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
@Slf4j
@Service
public class SpaceUserServiceImpl extends ServiceImpl<SpaceUserMapper, SpaceUser> implements SpaceUserService {

    @Resource
    private SpaceService spaceService;

    @Resource
    private UserService userService;

    @Override
    public long addSpaceUser(SpaceUserAddRequest spaceUserAddRequest) {
        log.info("[添加空间用户] 收到请求：{}", spaceUserAddRequest);
        log.info("[添加空间用户] spaceId={}, userId={}, spaceRole={}", 
                spaceUserAddRequest != null ? spaceUserAddRequest.getSpaceId() : "null",
                spaceUserAddRequest != null ? spaceUserAddRequest.getUserId() : "null",
                spaceUserAddRequest != null ? spaceUserAddRequest.getSpaceRole() : "null");
        
        ThrowUtils.throwIf(spaceUserAddRequest == null, ErrorCode.PARAM_ERROR, "请求参数不能为空");

        // 先校验请求对象本身，报错更明确
        ThrowUtils.throwIf(spaceUserAddRequest.getSpaceId() == null, ErrorCode.PARAM_ERROR, "空间 ID（spaceId）不能为空");
        ThrowUtils.throwIf(spaceUserAddRequest.getUserId() == null, ErrorCode.PARAM_ERROR, "用户 ID（userId）不能为空");
        ThrowUtils.throwIf(StrUtil.isBlank(spaceUserAddRequest.getSpaceRole()), ErrorCode.PARAM_ERROR, "空间角色（spaceRole）不能为空");

        log.info("[添加空间用户] 参数校验通过，准备保存：spaceId={}, userId={}, spaceRole={}", 
                spaceUserAddRequest.getSpaceId(), 
                spaceUserAddRequest.getUserId(), 
                spaceUserAddRequest.getSpaceRole());

        // 检查该用户在该空间是否已经存在
        Long spaceId = spaceUserAddRequest.getSpaceId();
        Long userId = spaceUserAddRequest.getUserId();
        QueryWrapper<SpaceUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("spaceId", spaceId).eq("userId", userId);
        SpaceUser existingSpaceUser = this.getOne(queryWrapper);
        if (existingSpaceUser != null) {
            log.warn("[添加空间用户] 用户已在空间中，spaceId={}, userId={}, 现有 id={}, spaceRole={}", 
                    spaceId, userId, existingSpaceUser.getId(), existingSpaceUser.getSpaceRole());
            throw new BusinessException(ErrorCode.PARAM_ERROR, "该用户已在空间中");
        }

        SpaceUser spaceUser = new SpaceUser();
        BeanUtils.copyProperties(spaceUserAddRequest, spaceUser);

        validSpaceUser(spaceUser, true);

        boolean result = this.save(spaceUser);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR, "保存空间成员记录失败");
        log.info("[添加空间用户] 保存成功，id={}", spaceUser.getId());
        return spaceUser.getId();
    }

    @Override
    public void validSpaceUser(SpaceUser spaceUser, boolean add) {
        ThrowUtils.throwIf(spaceUser == null, ErrorCode.PARAM_ERROR, "空间成员对象不能为空");

        Long spaceId = spaceUser.getSpaceId();
        Long userId = spaceUser.getUserId();
        String spaceRole = spaceUser.getSpaceRole();

        if (add) {
            ThrowUtils.throwIf(spaceId == null, ErrorCode.PARAM_ERROR, "spaceId 不能为空");
            ThrowUtils.throwIf(userId == null, ErrorCode.PARAM_ERROR, "userId 不能为空");

            User user = userService.getById(userId);
            ThrowUtils.throwIf(user == null, ErrorCode.NOT_FOUND_ERROR, "用户不存在");

            Space space = spaceService.getById(spaceId);
            ThrowUtils.throwIf(space == null, ErrorCode.NOT_FOUND_ERROR, "空间不存在");
        }

        ThrowUtils.throwIf(StrUtil.isBlank(spaceRole), ErrorCode.PARAM_ERROR, "空间角色不能为空");

        SpaceRoleEnum spaceRoleEnum = SpaceRoleEnum.getEnumByValue(spaceRole);
        if (spaceRoleEnum == null) {
            throw new BusinessException(ErrorCode.PARAM_ERROR, "空间角色不存在");
        }
    }

    @Override
    public QueryWrapper<SpaceUser> getQueryWrapper(SpaceUserQueryRequest spaceUserQueryRequest) {
        QueryWrapper<SpaceUser> queryWrapper = new QueryWrapper<>();
        if (spaceUserQueryRequest == null) {
            return queryWrapper;
        }

        Long id = spaceUserQueryRequest.getId();
        Long spaceId = spaceUserQueryRequest.getSpaceId();
        Long userId = spaceUserQueryRequest.getUserId();
        String spaceRole = spaceUserQueryRequest.getSpaceRole();

        queryWrapper.eq(ObjUtil.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjUtil.isNotEmpty(spaceId), "spaceId", spaceId);
        queryWrapper.eq(ObjUtil.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq(ObjUtil.isNotEmpty(spaceRole), "spaceRole", spaceRole);

        return queryWrapper;
    }

    @Override
    public SpaceUserVO getSpaceUserVO(SpaceUser spaceUser, HttpServletRequest request) {
        if (spaceUser == null) {
            return null;
        }

        SpaceUserVO spaceUserVO = SpaceUserVO.objToVo(spaceUser);

        Long userId = spaceUser.getUserId();
        if (userId != null && userId > 0) {
            User user = userService.getById(userId);
            UserVO userVO = userService.getUserVO(user);
            spaceUserVO.setUser(userVO);
        }

        Long spaceId = spaceUser.getSpaceId();
        if (spaceId != null && spaceId > 0) {
            Space space = spaceService.getById(spaceId);
            SpaceVO spaceVO = spaceService.getSpaceVO(space, request);
            spaceUserVO.setSpace(spaceVO);
        }

        return spaceUserVO;
    }

    @Override
    public List<SpaceUserVO> getSpaceUserVOList(List<SpaceUser> spaceUserList) {
        if (CollUtil.isEmpty(spaceUserList)) {
            return Collections.emptyList();
        }

        List<SpaceUserVO> spaceUserVOList = spaceUserList.stream()
                .map(SpaceUserVO::objToVo)
                .collect(Collectors.toList());

        Set<Long> userIdSet = spaceUserList.stream().map(SpaceUser::getUserId).collect(Collectors.toSet());
        Set<Long> spaceIdSet = spaceUserList.stream().map(SpaceUser::getSpaceId).collect(Collectors.toSet());

        Map<Long, List<User>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(User::getId));

        Map<Long, List<Space>> spaceIdSpaceListMap = spaceService.listByIds(spaceIdSet).stream()
                .collect(Collectors.groupingBy(Space::getId));

        spaceUserVOList.forEach(spaceUserVO -> {
            Long userId = spaceUserVO.getUserId();
            Long spaceId = spaceUserVO.getSpaceId();

            User user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            spaceUserVO.setUser(userService.getUserVO(user));

            Space space = null;
            if (spaceIdSpaceListMap.containsKey(spaceId)) {
                space = spaceIdSpaceListMap.get(spaceId).get(0);
            }
            spaceUserVO.setSpace(SpaceVO.objToVo(space));
        });

        return spaceUserVOList;
    }
}