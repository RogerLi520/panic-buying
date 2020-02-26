package com.wenyanwen123.buy.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @Desc json工具类
 * @Author liww
 * @Date 2019/6/10
 * @Version 1.0
 */
public class JsonUtil {

    /**
     * Json字符串序列化为Object
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T json2Object(String json, Class<T> clazz) {
        try {
            return JSON.parseObject(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Json字符串序列化为Object
     *
     * @param json
     * @return
     */
    public static JSONObject json2Object(String json) {
        try {
            return JSON.parseObject(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Json字符串序列化为Map<String, Object>
     *
     * @param json
     * @return
     */
    public static Map<String, Object> json2Map(String json) {
        try {
            return JSON.parseObject(json, new TypeReference<Map<String, Object>>() {});
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Json字符串序列化为Object
     *
     * @param json
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> json2List(String json, Class<T> clazz) {
        try {
            return JSON.parseArray(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将对象转为json字符串
     */
    public static String object2Json(Object object) {
        try {
            return JSON.toJSONString(object, SerializerFeature.WriteDateUseDateFormat);
//            return gson.toJson(object);
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return "";
    }


    /**
     * 类型转换，失败则为默认值
     */
    public static <T> T Convert(Object obj, T defaultValue) {
        try {
            if (obj == null)
                return defaultValue;
            if (defaultValue instanceof String) {
                return (T) obj.toString();
            } else if (defaultValue instanceof BigDecimal) {
                return (T) new BigDecimal(obj.toString());
            } else if (defaultValue instanceof Integer) {
                return (T) (Object) Integer.parseInt(obj.toString());
            } else if (defaultValue instanceof Boolean) {
                return (T) (Object) Boolean.parseBoolean(obj.toString());
            } else if (defaultValue instanceof Float) {
                return (T) (Object) Float.parseFloat(obj.toString());
            } else if (defaultValue instanceof Long) {
                return (T) (Object) Long.parseLong(obj.toString());
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
        return defaultValue;
    }

    /**
     * 日期格式转换
     * 将 yyyyMMdd的整数日期转换为yyyy-MM-dd的字符串日期
     * 转换错误则返回当前日期
     */
    public static String DateConvert(Integer data) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (data != null) {
            try {
                return sdf.format(new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(data)));
            } catch (ParseException e) {
//            e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 左补充
     */
    public static String PadLeft(String str, int length, char pad) {
        while (str.length() < length)
            str = pad + str;
        return str;
    }

    /**
     * 右补充
     */
    public static String PadRight(String str, int length, char pad) {
        while (str.length() < length)
            str = str + pad;
        return str;
    }

    /**
     * 判断字符串是否是空或空格
     *
     * @param str 需要判断的String
     */
    public static boolean IsNullOrEmptyOrSpace(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 判断字符串是否是空
     *
     * @param str 需要判断的String
     */
    public static boolean IsNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    /**
     * 如果是空则返回默认值
     *
     * @param obj
     * @param defaultValue
     * @param <T>
     * @return
     */
    public static <T> T IfNull(T obj, T defaultValue) {
        if (obj == null)
            return defaultValue;
        return obj;
    }

    /**
     * BigDecimal 四舍五入保留小数位数
     *
     * @param num   值
     * @param scale 小数位数
     * @return
     */
    public static BigDecimal FixDecimal(BigDecimal num, int scale) {
        return num.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

}
