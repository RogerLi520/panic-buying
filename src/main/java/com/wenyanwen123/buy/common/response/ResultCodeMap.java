package com.wenyanwen123.buy.common.response;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Desc 结果码对应的返回信息
 * @Author liww
 * @Date 2019/4/10
 * @Version 1.0
 */
public class ResultCodeMap {

    private static final Map<String, String> codeAndMsg = new HashMap<>();

    private ResultCodeMap() { }

    static {
        codeAndMsg.put(ResultCode.DEFAULT_SUCCESS_CODE, "操作成功");
        codeAndMsg.put(ResultCode.DEFAULT_FAIL_CODE, "操作失败");
        codeAndMsg.put(ResultCode.ILLEGAL_PARAMETER_CODE, "非法参数");
        codeAndMsg.put(ResultCode.ADD_FAIL_CODE, "添加失败");
        codeAndMsg.put(ResultCode.MODIFY_FAIL_CODE, "修改失败");
        codeAndMsg.put(ResultCode.QUERY_FAIL_CODE, "查询失败");
        codeAndMsg.put(ResultCode.LOGIN_FAIL, "登录失败");
        codeAndMsg.put(ResultCode.NOT_LOGIN, "请先登录");
        codeAndMsg.put(ResultCode.LOGIN_OUT_FAIL, "登出失败");
        codeAndMsg.put(ResultCode.GLOBAL_EXCEPTION, "服务器异常");
        codeAndMsg.put(ResultCode.FREQUENT_VISITS_EXCEPTION, "访问过于频繁，请稍后重试！");
        codeAndMsg.put(ResultCode.LOGIN_USER_PROHIBIT, "账号被禁用，请联系管理员");
        codeAndMsg.put(ResultCode.VERIFICATION_CODE_ERROR, "验证码校验失败");
        codeAndMsg.put(ResultCode.USER_UNAUTHORIZED, "用户未授权");
        codeAndMsg.put(ResultCode.CREATE_ORDER_FAIL, "创建订单失败");
        codeAndMsg.put(ResultCode.FAILURE_TO_PAY, "支付失败");
        codeAndMsg.put(ResultCode.FAILURE_OF_PRICE_CHANGE, "改价失败");
        codeAndMsg.put(ResultCode.CANCEL_ORDER_FAIL, "取消失败");
        codeAndMsg.put(ResultCode.SING_IN_FAIL, "签到失败");
        codeAndMsg.put(ResultCode.SING_OUT_FAIL, "签退失败");
    }

    /**
     * @Desc 根据结果码获取相应的信息
     * @Author liww
     * @Date 2019/6/18
     * @Version 1.0
     */
    public static String getMsgByCode(@ResultCode String code) {
        String msg = codeAndMsg.get(code);
        if (StringUtils.isEmpty(code)) {
            return "";
        }
        return msg;
    }

}
