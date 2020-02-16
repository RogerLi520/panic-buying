package com.wenyanwen123.buy.commons.core.exception;

import com.wenyanwen123.buy.commons.response.ResultCode;
import com.wenyanwen123.buy.commons.response.ResultCodeMap;
import com.wenyanwen123.buy.commons.response.ResultResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Desc 全局异常处理
 * @Author liww
 * @Date 2019/6/10
 * @Version 1.0
 */
@ControllerAdvice(basePackages = "com.wenyanwen123.buy.commons.exception")
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 全局异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ResultResponse errorResult(Exception e) {
        printfExceptionInfo(e);
        return ResultResponse.fail(ResultCode.GLOBAL_EXCEPTION, ResultCodeMap.getMsgByCode(ResultCode.GLOBAL_EXCEPTION));
    }

    /**
     * Token异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MyTokenException.class)
    @ResponseBody
    public ResultResponse exception(MyTokenException e) {
        return ResultResponse.fail(ResultCode.TOKEN_EXCEPTION);
    }

    /**
     * 参数验证异常
     *
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(value = MyParamException.class)
    @ResponseBody
    public ResultResponse paramErrorHandler(MyParamException e) {
        printfExceptionInfo(e);
        return new ResultResponse(ResultCode.ILLEGAL_PARAMETER_CODE, ResultCodeMap.getMsgByCode((ResultCode.ILLEGAL_PARAMETER_CODE)));
    }

    /**
     * 异常信息打印
     *
     * @param e
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
        logger.error(strBuf.toString());
    }

}
