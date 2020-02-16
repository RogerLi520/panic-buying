package com.wenyanwen123.buy.commons.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;

/**
 * @Description: TODO
 * @Author liww
 * @Date 2019/3/8
 * @Version 1.0
 */
public class ResultResponse<T> extends BaseResponse {

    // data为null的情况下，也参与序列化
    @JsonInclude(JsonInclude.Include.ALWAYS)
    private T data;

    private Integer totalNum;

    private Integer listSize;

    public ResultResponse() {
        super();
    }

    public ResultResponse(String resultCode, String resultMsg, T data) {
        this(resultCode, resultMsg);
        this.data = data;
    }

    public ResultResponse(String resultCode, String resultMsg) {
        super(resultCode, resultMsg);
    }

    public ResultResponse(T data) {
        super();
        this.data = data;
    }

    public static <T> ResultResponse<T> success(T data) {
        ResultResponse<T> response = new ResultResponse<>();
        response.setData(data);
        return response;
    }

    public static <T> ResultResponse<T> success(String resultMsg) {
        return new ResultResponse(ResultCode.DEFAULT_SUCCESS_CODE, resultMsg);
    }

    public static <T> ResultResponse<T> success(String resultMsg, T data) {
        return new ResultResponse(ResultCode.DEFAULT_SUCCESS_CODE, resultMsg, data);
    }

    public static <T> ResultResponse<T> fail(String resultCode) {
        String msg = ResultCodeMap.getMsgByCode(resultCode);
        if (StringUtils.isEmpty(msg)) {
            msg = "操作失败";
        }
        ResultResponse<T> response = new ResultResponse<>(resultCode, msg);
        response.setData(null);
        return response;
    }

    public static <T> ResultResponse<T> fail(String code, String msg) {
        ResultResponse<T> response = new ResultResponse<>(code, msg);
        response.setData(null);
        return response;
    }

    public static <T> ResultResponse<T> fail() {
        ResultResponse<T> response = new ResultResponse<>(ResultCode.DEFAULT_FAIL_CODE, "系统错误");
        response.setData(null);
        return response;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getListSize() {
        return listSize;
    }

    public void setListSize(Integer listSize) {
        this.listSize = listSize;
    }

}
