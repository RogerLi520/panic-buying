package com.wenyanwen123.buy.common.core.exception;

import com.wenyanwen123.buy.common.response.ResultCode;
import com.wenyanwen123.buy.common.response.ResultCodeMap;

/**
 * @Description: 访问过于频繁异常
 * @Author liww
 * @Date 2020/3/4
 * @Version 1.0
 */
public class FrequentVisitsException extends CustomRuntimeException {

    public FrequentVisitsException(String message) {
        super(ResultCode.TOKEN_EXCEPTION, message);
    }

    public FrequentVisitsException() {
        super(ResultCode.TOKEN_EXCEPTION, ResultCodeMap.getMsgByCode(ResultCode.TOKEN_EXCEPTION));
    }

}
