package com.wenyanwen123.buy.common.core.exception;

/**
 * @Desc 参数异常
 * @Author liww
 * @Date 2019/6/11
 * @Version 1.0
 */
public class MyParamException extends RuntimeException {

    private final String message;

    public MyParamException(String message) {
        super("");
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
