package com.itheima.backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itheima.backend.model.dto.picture.PictureQueryRequest;
import com.itheima.backend.model.dto.picture.PictureUploadRequest;
import com.itheima.backend.model.entity.Picture;
import com.itheima.backend.model.entity.User;
import com.itheima.backend.model.vo.PictureVO;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Service
public interface PictureService extends IService<Picture> {
    /**
     * 上传图片
     *
     * @param multipartFile 文件
     * @param pictureUploadRequest 上传请求
     * @param loginUser 登录用户
     * @return 上传结果
     */
    PictureVO uploadPicture(MultipartFile multipartFile, PictureUploadRequest pictureUploadRequest, User loginUser);

    QueryWrapper<Picture> getQueryWrapper(PictureQueryRequest pictureQueryRequest);

    PictureVO getPictureVO(Picture picture, HttpServletRequest request);

    Page<PictureVO> getPictureVOPage(Page<Picture> picturePage, HttpServletRequest request);

    void validPicture(Picture picture);
}
