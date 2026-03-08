package com.itheima.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.backend.model.dto.space.analyze.SpaceCategoryAnalyzeRequest;
import com.itheima.backend.model.dto.space.analyze.SpaceSizeAnalyzeRequest;
import com.itheima.backend.model.dto.space.analyze.SpaceTagAnalyzeRequest;
import com.itheima.backend.model.dto.space.analyze.SpaceUsageAnalyzeRequest;
import com.itheima.backend.model.entity.Space;
import com.itheima.backend.model.entity.User;
import com.itheima.backend.model.vo.space.analyze.SpaceCategoryAnalyzeResponse;
import com.itheima.backend.model.vo.space.analyze.SpaceSizeAnalyzeResponse;
import com.itheima.backend.model.vo.space.analyze.SpaceTagAnalyzeResponse;
import com.itheima.backend.model.vo.space.analyze.SpaceUsageAnalyzeResponse;

import java.util.List;

public interface SpaceAnalyzeService extends IService<Space> {
    /**
     * 获取空间使用分析
     *
     * @param spaceUsageAnalyzeRequest 空间使用分析请求
     * @param loginUser 当前登录用户
     * @return 空间使用分析响应
     */
    SpaceUsageAnalyzeResponse getSpaceUsageAnalyze(SpaceUsageAnalyzeRequest spaceUsageAnalyzeRequest, User loginUser);

    List<SpaceCategoryAnalyzeResponse> getSpaceCategoryAnalyze(SpaceCategoryAnalyzeRequest spaceCategoryAnalyzeRequest, User loginUser);

    List<SpaceTagAnalyzeResponse> getSpaceTagAnalyze(SpaceTagAnalyzeRequest spaceTagAnalyzeRequest, User loginUser);

    List<SpaceSizeAnalyzeResponse> getSpaceSizeAnalyze(SpaceSizeAnalyzeRequest spaceSizeAnalyzeRequest, User loginUser);
}
