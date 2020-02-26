package com.wenyanwen123.buy.common.core;

import com.wenyanwen123.buy.common.core.exception.MyTokenException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Desc 基础控制类
 * @Author liww
 * @Date 2019/6/17
 * @Version 1.0
 */
@Controller
public class BaseController {

    private static ThreadLocal<ServletRequest> currentRequest = new ThreadLocal<ServletRequest>();
    private static ThreadLocal<ServletResponse> currentResponse = new ThreadLocal<ServletResponse>();

    /**
     * 线程安全初始化reque，respose对象
     * 每个Action执行前都会执行该方法
     * ModelAttribute的作用
     * 1)放置在方法的形参上：表示引用Model中的数据
     * 2)放置在方法上面：表示请求该类的每个Action前都会首先执行它，也可以将一些准备数据的操作放置在该方法里面。
     *
     * @param request
     * @param response
     */
    @ModelAttribute
    public void initReqAndRep(HttpServletRequest request, HttpServletResponse response) throws MyTokenException {
        currentRequest.set(request);
        currentResponse.set(response);
    }

    public HttpServletRequest request() {
        return (HttpServletRequest) currentRequest.get();
    }

    public HttpServletResponse response() {
        return (HttpServletResponse) currentResponse.get();
    }

    /**
     * @Desc 测试临时用
     * @Author liww
     * @Date 2019/7/18
     * @Param []
     * @return com.wenyanwen123.buy.common.auth.TokenUser
     */
    /*public TokenUser getTokenUser() {
        TokenUser tokenUser = new TokenUser();
        tokenUser.setUserId(10000040); // 123456 10000042 10000377 10000040
        return tokenUser;
    }*/

    /**
     * @Desc 获取请求平台编码
     * @Author liww
     * @Date 2019/9/4
     * @Param []
     * @return byte
     */
    public byte getPlatformCode() {
        String platformCode = request().getHeader("Platform-Code");  // 从http请求头中取出平台编码
        if (StringUtils.isEmpty(platformCode)) {
            return 0;
        }
        return new Byte(platformCode);
    }

}
