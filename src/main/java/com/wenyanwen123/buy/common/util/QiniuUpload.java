package com.wenyanwen123.buy.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wenyanwen123.buy.common.response.ResultCode;
import com.wenyanwen123.buy.common.response.ResultResponse;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Description: 七牛云
 * @Author liww
 * @Date 2019/3/21
 * @Version 1.0
 */
@RestController
@RequestMapping("/qiniu")
public class QiniuUpload {

    private static final Logger logger = LoggerFactory.getLogger(QiniuUpload.class);

    // 七牛云账号的相关信息
    @Value("${qiniu.accessKey}")
    private String accessKey; //访问秘钥

    @Value("${qiniu.secretKey}")
    private String secretKey; //授权秘钥

    @Value("${qiniu.bucket}")
    private String bucket; //存储空间名称

    @Value("${qiniu.domain}")
    private String domain; //外链域名

    @GetMapping("/QiniuUpTokenWithCheck")
    public Map<String, Object> QiniuUpTokenWithCheck(@RequestParam String suffix) throws Exception{
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            //验证七牛云身份是否通过
            Auth auth = Auth.create(accessKey, secretKey);
            //生成凭证
            String upToken = auth.uploadToken(bucket);
            result.put("token", upToken);
            //存入外链默认域名，用于拼接完整的资源外链路径
            result.put("domain", domain);

            // 是否可以上传的图片格式
            /*boolean flag = false;
            String[] imgTypes = new String[]{"jpg","jpeg","bmp","gif","png"};
            for(String fileSuffix : imgTypes) {
                if(suffix.substring(suffix.lastIndexOf(".") + 1).equalsIgnoreCase(fileSuffix)) {
                    flag = true;
                    break;
                }
            }
            if(!flag) {
                throw new Exception("图片：" + suffix + " 上传格式不对！");
            }*/

            //生成实际路径名
            String randomFileName = UUID.randomUUID().toString() + suffix;
            result.put("imgUrl", randomFileName);
            result.put("success", 1);
        } catch (Exception e) {
            result.put("message", "获取凭证失败，"+e.getMessage());
            result.put("success", 0);
        } finally {
            return result;
        }
    }

    /**
     * @Desc 获取七牛云token
     * @Author liww
     * @Date 2019/3/21
     * @Param []
     * @return com.fange108.manage.common.response.ResultResponse
     */
    @GetMapping("/QiniuUpToken")
    public ResultResponse QiniuUpToken() {
        JSONObject upTokenJson = new JSONObject();
        try {
            // 验证七牛云身份是否通过
            Auth auth = Auth.create(accessKey, secretKey);
            // 生成凭证
            String upToken = auth.uploadToken(bucket);
            upTokenJson.put("upToken", upToken);

            // 存入外链默认域名，用于拼接完整的资源外链路径
            // jsonObject.put("domain", domain);
            logger.debug("返回结果，result：{}", JSON.toJSONString(upToken));

            return ResultResponse.success("操作成功", upTokenJson);
        } catch (Exception e) {
            logger.error("获取七牛云凭证异常，异常信息，e：{}", e);
        }
        return ResultResponse.fail(ResultCode.QUERY_FAIL_CODE, "获取七牛云凭证异常");
    }

}
