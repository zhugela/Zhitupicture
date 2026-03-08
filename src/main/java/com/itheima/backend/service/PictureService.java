package com.itheima.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.backend.api.aliyunai.model.CreateOutPaintingTaskResponse;
import com.itheima.backend.model.dto.picture.*;
import com.itheima.backend.model.entity.Picture;
import com.itheima.backend.model.entity.User;
import com.itheima.backend.model.vo.PictureVO;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public interface PictureService extends IService<Picture> {

    /**
     * 上传图片
     *
     * @param inputSource 输入源（MultipartFile 或 String URL）
     * @param pictureUploadRequest 上传请求参数
     * @param loginUser 登录用户
     * @return 图片 VO 对象
     */
    PictureVO uploadPicture(Object inputSource, PictureUploadRequest pictureUploadRequest, User loginUser);

    /**
     * 获取查询条件包装器
     *
     * @param pictureQueryRequest 图片查询请求
     * @return QueryWrapper 查询条件
     */
    QueryWrapper<Picture> getQueryWrapper(PictureQueryRequest pictureQueryRequest);

    /**
     * 获取图片 VO 对象
     *
     * @param picture 图片实体
     * @param request HTTP 请求
     * @return 图片 VO 对象
     */
    PictureVO getPictureVO(Picture picture, HttpServletRequest request);

    /**
     * 分页获取图片 VO 列表
     *
     * @param picturePage 图片分页对象
     * @param request HTTP 请求
     * @return 图片 VO 分页结果
     */
    Page<PictureVO> getPictureVOPage(Page<Picture> picturePage, HttpServletRequest request);

    /**
     * 校验图片数据
     *
     * @param picture 图片实体
     */
    void validPicture(Picture picture);
    /**
     * 图片审核
     *
     * @param pictureReviewRequest 审核请求参数
     * @param loginUser 登录用户
     */
    void doPictureReview(PictureReviewRequest pictureReviewRequest, User loginUser);

    /**
     * 填充审核参数
     *
     * @param picture 图片实体
     * @param loginUser 登录用户
     */
    void fillReviewParams(Picture picture, User loginUser);
    /**
     * 批量抓取和创建图片
     *
     * @param pictureUploadByBatchRequest
     * @param loginUser
     * @return 成功创建的图片数
     */
    Integer uploadPictureByBatch(
            PictureUploadByBatchRequest pictureUploadByBatchRequest,
            User loginUser
    );

    @Async
    void clearPictureFile(Picture oldPicture);

    void checkPictureAuth(User loginUser, Picture picture);

    void deletePicture(long pictureId, User loginUser);

    void editPicture(PictureEditRequest pictureEditRequest, User loginUser);

    List<PictureVO> searchPictureByColor(Long spaceId, String picColor, User loginUser);

    @Transactional(rollbackFor = Exception.class)
    void editPictureByBatch(PictureEditByBatchRequest pictureEditByBatchRequest, User loginUser);

    CreateOutPaintingTaskResponse createPictureOutPaintingTask(CreatePictureOutPaintingTaskRequest createPictureOutPaintingTaskRequest, User loginUser);
}
