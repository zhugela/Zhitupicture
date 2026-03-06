package com.itheima.backend.manager;

import cn.hutool.core.io.FileUtil;
import com.itheima.backend.config.CosClientConfig;
import com.qcloud.cos.COSClient;

import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.model.COSObject;
import com.qcloud.cos.model.GetObjectRequest;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.ciModel.persistence.PicOperations;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class CosManager {

    @Resource
    private  CosClientConfig cosClientConfig;

    @Resource
    private  COSClient cosClient;

    public PutObjectResult putObject(String key, File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosClientConfig.getBucket(), key,
                file);
        return cosClient.putObject(putObjectRequest);
    }

    /**
     * 下载对象
     *
     * @param key 唯一键
     */
    public COSObject getObject(String key) {
        GetObjectRequest getObjectRequest = new GetObjectRequest(cosClientConfig.getBucket(), key);
        return cosClient.getObject(getObjectRequest);
    }
    /*
    上传对象（附带图片信息）
     */
    public PutObjectResult putPictureObject(String key, File file){
        PutObjectRequest putObjectRequest = new PutObjectRequest(cosClientConfig.getBucket(), key,
                file);
        //对图片进行处理（获得基本信息被视作一种图片的处理）
        PicOperations picOperations = new PicOperations();
    
        //1.表示返回原图信息
        picOperations.setIsPicInfo(1);
        List<PicOperations.Rule> rules = new ArrayList<>();
        //图片压缩 (转成 webp 格式）
        String webpkey  = FileUtil.mainName(key) + ".webp";
        PicOperations.Rule rule = new PicOperations.Rule();
        rule.setRule("imageMogr2/format/webp");
        rule.setBucket(cosClientConfig.getBucket());
        rule.setFileId(webpkey);
        rules.add(rule);
            
        // 缩略图处理，仅对 > 20 KB 的图片生成缩略图
        if (file.length() > 2 * 1024) {
            PicOperations.Rule thumbnailRule = new PicOperations.Rule();
            thumbnailRule.setBucket(cosClientConfig.getBucket());
            String thumbnailKey = FileUtil.mainName(key) + "_thumbnail." + FileUtil.getSuffix(key);
            thumbnailRule.setFileId(thumbnailKey);
            // 缩放规则/thumbnail/<Width>x<Height>>（如果大于原图宽高，则不处理）
            thumbnailRule.setRule(String.format("imageMogr2/thumbnail/%sx%s>", 128, 128));
            rules.add(thumbnailRule);
        }
            
        //将 PicOperations 设置到请求中
        picOperations.setRules(rules);
        putObjectRequest.setPicOperations(picOperations);
            
        return cosClient.putObject(putObjectRequest);
    
    }
    /**
     * 删除对象
     *
     * @param key 文件 key
     */
    public void deleteObject(String key) throws CosClientException {
        cosClient.deleteObject(cosClientConfig.getBucket(), key);
    }

}
