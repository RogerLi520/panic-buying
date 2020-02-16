package com.wenyanwen123.buy.commons.core.annotation;

import java.lang.annotation.*;

/**
 * @Desc 不需要登录注解
 * @Author liww
 * @Date 2019/6/10
 * @Param
 * @return
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreLogin {

    boolean ignore() default true;

}
