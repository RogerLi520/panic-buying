package com.wenyanwen123.buy.common.core.exception;

/**
 * @Description: 自定义运行时异常
 * @Author liww
 * @Date 2020/3/4
 * @Version 1.0
 */
public class CustomRuntimeException extends RuntimeException {

    protected String errorCode;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public CustomRuntimeException(String errorCode, String message, Throwable e) {
        super(message, e);
        this.errorCode = errorCode;
    }

    public CustomRuntimeException(String errorCode, String messgae) {
        this(errorCode, messgae, null);
    }

}
