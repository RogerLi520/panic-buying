package com.wenyanwen123.buy.commons.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Desc 异常工具类
 * @Author liww
 * @Date 2019/8/28
 * @Version 1.0
 */
public class ExceptionUtil {

    private static final Logger log = LoggerFactory.getLogger(ExceptionUtil.class);

    /**
     * 异常信息打印
     *
     * @param e
     */
    public static void printfExceptionInfo(Exception e) {
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
