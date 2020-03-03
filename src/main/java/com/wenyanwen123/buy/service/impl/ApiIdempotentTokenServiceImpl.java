package com.wenyanwen123.buy.service.impl;

import com.wenyanwen123.buy.common.response.ResultCode;
import com.wenyanwen123.buy.common.response.ResultResponse;
import com.wenyanwen123.buy.common.util.LogUtil;
import com.wenyanwen123.buy.common.util.UUIDUtil;
import com.wenyanwen123.buy.provider.redis.RedisService;
import com.wenyanwen123.buy.provider.redis.keys.ApiTokenKey;
import com.wenyanwen123.buy.service.ApiIdempotentTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 幂等接口token
 * @Author liww
 * @Date 2020/3/3
 * @Version 1.0
 */
@Slf4j
@Service
public class ApiIdempotentTokenServiceImpl implements ApiIdempotentTokenService {

    private static final String API_IDEMPOTENT_TOKEN_NAME = "apiToken";

    @Autowired
    private RedisService redisService;

    /**
     * @Desc 获取幂等接口token
     * @Author liww
     * @Date 2020/3/3
     * @Param []
     * @return com.wenyanwen123.buy.common.response.ResultResponse
     */
    @Override
    public ResultResponse getApiToken() {
        LogUtil.serviceStart(log, "获取幂等接口token");
        String apiToken = UUIDUtil.getUuid();
        redisService.set(ApiTokenKey.apiTokenKey, apiToken, apiToken);
        return ResultResponse.success(apiToken);
    }

    /**
     * @Desc 校验api token
     * @Author liww
     * @Date 2020/3/3
     * @Param [request]
     * @return com.wenyanwen123.buy.common.response.ResultResponse
     */
    @Override
    public ResultResponse checkApiToken(HttpServletRequest request) {
        LogUtil.serviceStart(log, "校验api token");
        String apiToken = request.getHeader(API_IDEMPOTENT_TOKEN_NAME);
        // header中不存在token
        if (StringUtils.isEmpty(apiToken)) {
            apiToken = request.getParameter(API_IDEMPOTENT_TOKEN_NAME);
            // parameter中也不存在token
            if (StringUtils.isEmpty(apiToken)) {
                return ResultResponse.fail(ResultCode.ILLEGAL_PARAMETER_CODE, "非法参数");
            }
        }

        if (!redisService.exists(ApiTokenKey.apiTokenKey, apiToken)) {
            return ResultResponse.fail(ResultCode.TOKEN_EXCEPTION, "参数错误");
        }

        boolean result = redisService.deleteOne(ApiTokenKey.apiTokenKey, apiToken);
        if (!result) {
            return ResultResponse.fail(ResultCode.DELETE_FAIL_CODE, "操作失败");
        }
        return ResultResponse.success("操作成功");
    }

}
