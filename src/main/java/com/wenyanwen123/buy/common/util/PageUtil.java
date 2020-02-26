package com.wenyanwen123.buy.common.util;

import com.wenyanwen123.buy.common.model.rp.BaseRequestParam;
import com.wenyanwen123.buy.common.response.ResultResponse;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @Desc 分页工具类
 * @Author liww
 * @Date 2019/6/25
 * @Version 1.0
 */
public class PageUtil {

    /**
     * @Desc 获取分页大小
     * @Author liww
     * @Date 2019/6/25
     * @Param [baseRequestParam]
     * @return int
     */
    public static int getPageSize(BaseRequestParam baseRequestParam) {
        if (baseRequestParam.getPageSize() == null) {
            return 10;
        } else {
            return baseRequestParam.getPageSize();
        }
    }

    /**
     * @Desc 获取当前页
     * @Author liww
     * @Date 2019/6/25
     * @Param [baseRequestParam]
     * @return int
     */
    public static int getCurrPage(BaseRequestParam baseRequestParam) {
        if (baseRequestParam.getCurrPage() == null) {
            return 1;
        } else {
            return baseRequestParam.getCurrPage();
        }
    }

    /**
     * @Desc 默认分页大小
     * @Author liww
     * @Date 2019/6/28
     * @Param []
     * @return int
     */
    public static int defaultPageSize() {
        return 10;
    }

    /**
     * @Desc 默认当前页
     * @Author liww
     * @Date 2019/6/28
     * @Param []
     * @return int
     */
    public static int defaultCurrPage() {
        return 1;
    }

    /**
     * @Desc 获取分页后的结果
     * @Author liww
     * @Date 2019/7/4
     * @Param [pageInfo, list]
     * @return com.wenyanwen123.buy.common.response.ResultResponse
     */
    public static ResultResponse pageResult(PageInfo pageInfo, List list) {
        // 取总记录数
        long total = pageInfo.getTotal();
        ResultResponse resultResponse = new ResultResponse();
        resultResponse.setTotalNum((int)total);
        resultResponse.setListSize(list.size());
        resultResponse.setData(list);
        return resultResponse;
    }

}
