package com.wenyanwen123.buy.common.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Desc 参数校验注解
 * @Author liww
 * @Date 2019/6/12
 * @Version 1.0
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamCheck {

    /**
     * 是否非空
     */
    boolean notNull() default false;

    /**
     * 是否为数值
     */
    boolean numeric() default false;

    /**
     * 最大长度
     */
    int maxLen() default -1;

    /**
     * 最小长度
     */
    int minLen() default -1;

    /**
     * 最小数值
     */
    long minNum() default -999999;

    String message() default "参数校验失败";

}
