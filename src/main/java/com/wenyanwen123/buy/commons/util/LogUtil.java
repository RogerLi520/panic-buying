package com.wenyanwen123.buy.commons.util;

import com.alibaba.fastjson.JSON;
import com.wenyanwen123.buy.commons.response.ResultResponse;
import org.slf4j.Logger;

/**
 * @Description: 日志工具类
 * @Author liww
 * @Date 2019/3/14
 * @Version 1.0
 */
public class LogUtil {

    /**
     * @Desc 输出结果
     * @Author liww
     * @Date 2019/3/14
     * @Param [logger, Object]
     * @return void
     */
    public static void outputResult(Logger logger, ResultResponse resultResponse) {
        logger.debug("结果，result：{}", JSON.toJSONString(resultResponse));
    }

    /**
     * @Desc 接口调用开始日志
     * @Author liww
     * @Date 2019/6/21
     * @Param [logger, targetInfo]
     * @return void
     */
    public static void callStart(Logger logger, String targetInfo, Long userId) {
        logger.debug(targetInfo + "接口调用开始，" + userId);
    }

    /**
     * @Desc service层开始执行
     * @Author liww
     * @Date 2019/6/21
     * @Param [logger, targetInfo]
     * @return void
     */
    public static void serviceStart(Logger logger, String targetInfo) {
        logger.debug(targetInfo + "service开始");
    }

    /**
     * @Desc 操作成功
     * @Author liww
     * @Date 2019/6/24
     * @Param [logger, targetInfo]
     * @return void
     */
    public static void success(Logger logger, String targetInfo) {
        logger.info(targetInfo + "成功");
    }

    /**
     * @Desc 操作失败
     * @Author liww
     * @Date 2019/6/24
     * @Param [logger, targetInfo]
     * @return void
     */
    public static void fail(Logger logger, String targetInfo) {
        logger.info(targetInfo + "失败");
    }

    /**
     * @Desc 查询结果
     * @Author liww
     * @Date 2019/6/25
     * @Param [logger, targetInfo, result]
     * @return void
     */
    public static void selectResult(Logger logger, String targetName, Object result) {
        logger.info("查询结果，" + targetName + "：{}", JSON.toJSONString(result));
    }

}
