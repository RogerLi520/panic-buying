package com.wenyanwen123.buy.common.core.annotation;

import java.lang.annotation.*;

/**
 * @Desc 无需登陆
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
