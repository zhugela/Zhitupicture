package com.itheima.backend.api.imagesearch.sub;

import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.itheima.backend.exception.BusinessException;
import com.itheima.backend.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class GetImagePageUrlApi {

    /**
     * 获取图片页面地址
     *
     * @param imageUrl
     * @return
     */
    public static String getImagePageUrl(String imageUrl) {
        Map<String, Object> formData = new HashMap<>();
        formData.put("image", imageUrl);
        formData.put("tn", "pc");
        formData.put("from", "pc");
        formData.put("image_source", "PC_UPLOAD_URL");

        long uptime = System.currentTimeMillis();
        String url = "https://graph.baidu.com/upload?uptime=" + uptime;

        try {
            HttpResponse response = HttpRequest.post(url)
                    .header("acs-token", "1") // 先简单试，很多示例用随机 1 位字符串
                    .header("User-Agent", "Mozilla/5.0")
                    .header("Referer", "https://graph.baidu.com/")
                    .form(formData)
                    .timeout(5000)
                    .execute();

            if (HttpStatus.HTTP_OK != response.getStatus()) {
                log.error("接口调用失败，HTTP 状态码：{}", response.getStatus());
                throw new BusinessException(ErrorCode.OPERATION_ERROR,
                        "接口调用失败，状态码：" + response.getStatus());
            }

            String responseBody = response.body();
            log.info("请求 imageUrl={}", imageUrl);
            log.info("百度以图搜图响应：{}", responseBody);

            Map<String, Object> result = JSONUtil.toBean(responseBody, Map.class);
            if (result == null) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "响应结果为空");
            }

            String status = String.valueOf(result.get("status"));
            if (!"0".equals(status)) {
                String errorMsg = result.get("msg") != null ? result.get("msg").toString() : "未知错误";
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "接口调用失败：" + errorMsg);
            }

            Object dataObj = result.get("data");
            if (!(dataObj instanceof Map)) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "未返回有效数据");
            }

            Map<String, Object> data = (Map<String, Object>) dataObj;
            String rawUrl = String.valueOf(data.get("url"));
            if (rawUrl == null || rawUrl.trim().isEmpty() || "null".equals(rawUrl)) {
                throw new BusinessException(ErrorCode.OPERATION_ERROR, "未找到相似图片");
            }

            return URLUtil.decode(rawUrl, StandardCharsets.UTF_8);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("搜索失败，图片 URL: {}", imageUrl, e);
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "搜索失败：" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // 测试以图搜图功能
        String imageUrl = "https://www.codefather.cn/logo.png";
        String result = getImagePageUrl(imageUrl);
        System.out.println("搜索成功，结果 URL：" + result);
    }
}
