package com.wenyanwen123.buy.common.core.exception;

import com.wenyanwen123.buy.common.response.ResultCode;
import com.wenyanwen123.buy.common.response.ResultCodeMap;

/**
 * @Description: TODO
 * @Author liww
 * @Date 2020/3/4
 * @Version 1.0
 */
public class ApiTokenException extends CustomRuntimeException {

    public ApiTokenException(String message) {
        super(ResultCode.TOKEN_EXCEPTION, message);
    }

    public ApiTokenException() {
        super(ResultCode.TOKEN_EXCEPTION, ResultCodeMap.getMsgByCode(ResultCode.TOKEN_EXCEPTION));
    }

}
