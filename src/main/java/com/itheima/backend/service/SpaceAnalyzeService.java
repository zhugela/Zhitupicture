package com.itheima.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.backend.model.dto.space.analyze.SpaceUsageAnalyzeRequest;
import com.itheima.backend.model.entity.Space;
import com.itheima.backend.model.entity.User;
import com.itheima.backend.model.vo.space.analyze.SpaceUsageAnalyzeResponse;

public interface SpaceAnalyzeService extends IService<Space> {
    /**
     * 获取空间使用分析
     *
     * @param spaceUsageAnalyzeRequest 空间使用分析请求
     * @param loginUser 当前登录用户
     * @return 空间使用分析响应
     */
    SpaceUsageAnalyzeResponse getSpaceUsageAnalyze(SpaceUsageAnalyzeRequest spaceUsageAnalyzeRequest, User loginUser);
}
