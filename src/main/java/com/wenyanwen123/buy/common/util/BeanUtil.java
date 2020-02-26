package com.wenyanwen123.buy.common.util;

import com.alibaba.fastjson.JSON;

/**
 * @Description: 实体工具类
 * @Author liww
 * @Date 2020/2/25
 * @Version 1.0
 */
public class BeanUtil {

    /**
     * @Desc 类型转换
     * @Author liww
     * @Date 2020/2/20
     * @Param [str, clazz]
     * @return T
     */
    public static <T> T stringToBean(String str, Class<T> clazz) {
        if(str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if(clazz == int.class || clazz == Integer.class) {
            return (T)Integer.valueOf(str);
        }else if(clazz == String.class) {
            return (T)str;
        }else if(clazz == long.class || clazz == Long.class) {
            return  (T)Long.valueOf(str);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    /**
     * @Desc 类型转换
     * @Author liww
     * @Date 2020/2/25
     * @Param [value]
     * @return java.lang.String
     */
    public static <T> String beanToString(T value) {
        if(value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class) {
            return ""+value;
        }else if(clazz == String.class) {
            return (String)value;
        }else if(clazz == long.class || clazz == Long.class) {
            return ""+value;
        }else {
            return JSON.toJSONString(value);
        }
    }

}
