package com.itheima.backend.controller;

import com.itheima.backend.common.BaseResponse;
import com.itheima.backend.common.ResultUtils;
import com.itheima.backend.exception.ErrorCode;
import com.itheima.backend.exception.ThrowUtils;
import com.itheima.backend.model.dto.space.analyze.SpaceUsageAnalyzeRequest;
import com.itheima.backend.model.entity.User;
import com.itheima.backend.model.vo.space.analyze.SpaceUsageAnalyzeResponse;
import com.itheima.backend.service.SpaceAnalyzeService;
import com.itheima.backend.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/space/analyze")
public class SpaceAnalyzeController {

    @Resource
    private SpaceAnalyzeService spaceAnalyzeService;

    @Resource
    private UserService userService;

    /**
     * 获取空间使用状态
     */
    @PostMapping("/usage")
    public BaseResponse<SpaceUsageAnalyzeResponse> getSpaceUsageAnalyze(
            @RequestBody SpaceUsageAnalyzeRequest spaceUsageAnalyzeRequest,
            HttpServletRequest request
    ) {
        ThrowUtils.throwIf(spaceUsageAnalyzeRequest == null, ErrorCode.PARAM_ERROR);
        User loginUser = userService.getLoginUser(request);
        SpaceUsageAnalyzeResponse spaceUsageAnalyze = spaceAnalyzeService.getSpaceUsageAnalyze(spaceUsageAnalyzeRequest, loginUser);
        return ResultUtils.success(spaceUsageAnalyze);
    }
}
