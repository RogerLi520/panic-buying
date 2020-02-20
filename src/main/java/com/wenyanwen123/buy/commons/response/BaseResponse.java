package com.wenyanwen123.buy.commons.response;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @Description: 封装返回值类
 * @Author liww
 * @Date 2019/3/8
 * @Version 1.0
 */
public class BaseResponse {

    @JsonIgnore
    private Boolean isSuccess;

    @ResultCode
    private String resultCode;

    private String resultMsg;

    public BaseResponse(String resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
        this.isSuccess = ResultCode.DEFAULT_SUCCESS_CODE.equals(resultCode);
    }

    public BaseResponse() {
        this(ResultCode.DEFAULT_SUCCESS_CODE, "操作成功");
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(@ResultCode String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public Boolean isSuccess() {
        return isSuccess;
    }

}
