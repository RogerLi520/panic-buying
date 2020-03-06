package com.wenyanwen123.buy.common.core.exception;

import com.wenyanwen123.buy.common.response.ResultCode;
import com.wenyanwen123.buy.common.response.ResultCodeMap;
import com.wenyanwen123.buy.common.response.ResultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Desc 全局异常处理
 * @Author liww
 * @Date 2020/3/3
 * @Version 1.0
 */
@ControllerAdvice(basePackages = "com.wenyanwen123.buy.controller")
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * @Desc 全局异常
     * @Author liww
     * @Date 2020/3/3
     * @Param [e]
     * @return com.wenyanwen123.buy.common.response.ResultResponse
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultResponse errorResult(Exception e) {
        printfExceptionInfo(e);
        return ResultResponse.fail(ResultCode.GLOBAL_EXCEPTION);
    }

    /**
     * @Desc 自定义的运行时异常
     * @Author liww
     * @Date 2020/3/4
     * @Param [e]
     * @return com.wenyanwen123.buy.common.response.ResultResponse
     */
    @ExceptionHandler(value = CustomRuntimeException.class)
    @ResponseBody
    public ResultResponse customRuntimeException(CustomRuntimeException e) {
        printfExceptionInfo(e);
        return ResultResponse.fail(e.getErrorCode(), e.getMessage());
    }

    /**
     * @Desc Token异常
     * @Author liww
     * @Date 2020/3/3
     * @Param [e]
     * @return com.wenyanwen123.buy.common.response.ResultResponse
     */
    @ExceptionHandler(MyTokenException.class)
    @ResponseBody
    public ResultResponse exception(MyTokenException e) {
        printfExceptionInfo(e);
        return ResultResponse.fail(ResultCode.TOKEN_EXCEPTION);
    }

    /**
     * @Desc 参数验证异常
     * @Author liww
     * @Date 2020/3/3
     * @Param [e]
     * @return com.wenyanwen123.buy.common.response.ResultResponse
     */
    @ExceptionHandler(value = MyParamException.class)
    @ResponseBody
    public ResultResponse paramErrorHandler(MyParamException e) {
        printfExceptionInfo(e);
        return new ResultResponse(ResultCode.ILLEGAL_PARAMETER_CODE);
    }

    /**
     * @Desc 打印异常信息
     * @Author liww
     * @Date 2020/3/3
     * @Param [e]
     * @return void
     */
    private void printfExceptionInfo(Exception e) {
        StringBuffer strBuf = new StringBuffer();
        strBuf.append("\r\n**********异常信息开始**********\r\n");
        strBuf.append(" 标题:");
        strBuf.append("\r\n");
        if (e != null) {
            strBuf.append(e.getMessage());
            strBuf.append("\r\n");
            strBuf.append(e.toString());
            strBuf.append("\r\n");
            StackTraceElement[] stackTrace = e.getStackTrace();
            if (stackTrace != null) {
                for (StackTraceElement t : stackTrace) {
                    strBuf.append(t.toString());
                    strBuf.append("\r\n");
                }
            }
        }
        strBuf.append("**********异常信息结束**********");
        strBuf.append("\r\n");
        log.error(strBuf.toString());
    }

}
