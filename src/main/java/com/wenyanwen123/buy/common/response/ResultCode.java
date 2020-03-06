package com.wenyanwen123.buy.common.response;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Description: 自定义结果码
 * @Author liww
 * @Date 2019/3/21
 * @Version 1.0
 */
@Retention(RetentionPolicy.SOURCE)
public @interface ResultCode {

    String DEFAULT_SUCCESS_CODE  = "10000"; // 操作成功

    String DEFAULT_FAIL_CODE = "99999"; // 操作失败

    String ILLEGAL_PARAMETER_CODE = "80000"; // 非法参数

    // 常用操作
    String ADD_FAIL_CODE = "80001"; // 添加失败

    String DELETE_FAIL_CODE = "80002"; // 删除失败

    String MODIFY_FAIL_CODE = "80003"; // 修改失败

    String QUERY_FAIL_CODE = "80004"; // 查询失败

    // 登录相关
    String LOGIN_FAIL = "80020"; // 登录失败

    String NOT_LOGIN = "80021"; // 没有登录

    String LOGIN_OUT_FAIL = "80023"; // 登出失败

    String TOKEN_EXCEPTION = "80024"; // token异常

    String LOGIN_USER_PROHIBIT = "80025"; // 账号被禁用

    String USER_UNAUTHORIZED = "80026"; // 用户未授权

    // 异常相关
    String GLOBAL_EXCEPTION = "80040"; // 全局异常

    String FREQUENT_VISITS_EXCEPTION = "80041"; // 访问过于频繁异常

    // 验证码
    String VERIFICATION_CODE_ERROR = "80500"; // 验证码错误

    // 订单相关
    String CREATE_ORDER_FAIL = "80600"; // 创建订单失败

    String FAILURE_OF_PRICE_CHANGE = "80601"; // 改价失败

    // 支付相关
    String FAILURE_TO_PAY = "80700"; // 支付失败

    String FAILURE_OF_REFUND = "80701"; // 退款失败

    String CANCEL_ORDER_FAIL = "80702"; // 取消失败

    // 签到签退
    String SING_IN_FAIL = "80800"; // 签到失败

    String SING_OUT_FAIL = "80801"; // 签退失败

}
