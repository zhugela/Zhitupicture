package com.itheima.backend.manager;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import com.itheima.backend.config.CosClientConfig;
import com.itheima.backend.exception.BusinessException;
import com.itheima.backend.exception.ErrorCode;
import com.itheima.backend.exception.ThrowUtils;
import com.itheima.backend.model.dto.file.UploadPictureResult;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.ciModel.persistence.ImageInfo;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.bouncycastle.util.test.FixedSecureRandom;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.nio.channels.MulticastChannel;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class FileManager {
    @Resource
    private CosManager cosManager;

    @Resource
    private CosClientConfig cosClientConfig;
    /**
     * 上传图片
     *
     * @param multipartFile 文件
     * @param uploadPathPrefix 上传路径
     * @return 上传结果
     */
    public UploadPictureResult uploadPicture(MultipartFile multipartFile, String uploadPathPrefix) {
        // 校验图片
        validPicture(multipartFile);

        // 获取文件信息
        long fileSize = multipartFile.getSize();
        String originalFilename = multipartFile.getOriginalFilename();

        //图片上传地址
        String uuid = RandomUtil.randomString(16);
        String uploadFilename = String.format("%s_%s.%s", DateUtil.formatDate(new Date()), uuid,
                FileUtil.getSuffix(originalFilename));
        String uploadPath = String.format("%s/%s", uploadPathPrefix, uploadFilename);

        File file = null;
        try {
            // 上传文件到临时目录
            file = File.createTempFile(uploadPath, null);
            multipartFile.transferTo(file);
            PutObjectResult putObjectResult = cosManager.putPictureObject(uploadPath, file);
            ImageInfo imageInfo = putObjectResult.getCiUploadResult().getOriginalInfo().getImageInfo();
            int picWidth = imageInfo.getWidth();
            int picHeight = imageInfo.getHeight();
            double picScale = NumberUtil.round(picWidth / picHeight, 2).doubleValue();

           //封装返回结果
            UploadPictureResult uploadPictureResult = new UploadPictureResult();
            uploadPictureResult.setUrl(cosClientConfig.getHost()+"/" + uploadPath);
            uploadPictureResult.setPicName(FileUtil.mainName(originalFilename));
            uploadPictureResult.setPicSize(FileUtil.size(file));
            uploadPictureResult.setPicWidth(picWidth);
            uploadPictureResult.setPicHeight(picHeight);
            uploadPictureResult.setPicScale(picScale);
            uploadPictureResult.setPicFormat(imageInfo.getFormat());

            return uploadPictureResult;


        } catch (Exception e) {
            log.error("图片上传到对象存储失败",  e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "图片上传到对象存储失败");
        } finally {
            // 清理临时文件
            deleteTempFile(file);
        }
    }

    /**
     * 校验文件
     *
     * @param multipartFile 文件
     */
    private void validPicture(MultipartFile multipartFile) {
        // 校验文件是否为空
        ThrowUtils.throwIf(multipartFile == null, ErrorCode.PARAM_ERROR, "上传文件为空");
        // 校验文件大小
        long fileSize = multipartFile.getSize();
        final  long ONE_M = 1024*1024L;
        ThrowUtils.throwIf(fileSize > 2*ONE_M, ErrorCode.PARAM_ERROR, "上传文件大小不能超过2M");
        // 校验文件后缀
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        //允许上传的文件后缀列表
        final List<String> allowedFileSuffixList = Arrays.asList("png", "jpg", "jpeg", "gif","webp");
        ThrowUtils.throwIf(!allowedFileSuffixList.contains(fileSuffix), ErrorCode.PARAM_ERROR, "上传文件格式不正确");

    }

    /**
     * 清理临时文件
     *
     * @param file
     */
    public void deleteTempFile(File file) {
        if (file == null) {
            return;
        }
        // 删除临时文件
        boolean deleteResult = file.delete();
        if (!deleteResult) {
            log.error("file delete error, filepath = {}", file.getAbsolutePath());
        }
    }
}
