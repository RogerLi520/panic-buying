package com.wenyanwen123.buy.common.util.security;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;


public class SecuritySignUtil {

    public static String GetSign(Map<String, String> parameters, String signKey) {
        Map<String, String> newMap = new HashMap<String, String>();
        if (parameters == null || parameters.size() == 0)
            return "";
        for (String key : parameters.keySet()) {
            String value = parameters.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")) {
                continue;
            }
            newMap.put(key, value);
        }

        //2.排序
        List<String> keys = new ArrayList<String>(newMap.keySet());
        Collections.sort(keys);

        //3.拼接加密字符串，把所有参数名和参数值串在一起
        String query = "";
        for (String key : keys) {
            query = query + key + "=" + newMap.get(key) + "&";
        }
        //去掉最后一个&符号
        query = query.substring(0, query.length() - 1);

        //拼接MD5密钥
        query += signKey;

        //4.MD5加密
        String sign = MD5Util.ToMD5(query);

        return sign;
    }

    /**
     * 加签
     *
     * @param parameters 需要加签的参数
     * @param signKey    加签密钥
     * @return 带签名的请求参数
     */
    public static String GetRequestSignParam(Map<String, String> parameters, String signKey) {
        Map<String, String> newMap = new HashMap<String, String>();
        //1.筛选
        if (parameters == null || parameters.size() == 0)
            return "";

        for (String key : parameters.keySet()) {
            String value = parameters.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")) {
                continue;
            }
            newMap.put(key, value);
        }

        //2.排序
        List<String> keys = new ArrayList<String>(newMap.keySet());
        Collections.sort(keys);

        //3.拼接加密字符串，把所有参数名和参数值串在一起
        String query = "";
        for (String key : keys) {
            query = query + key + "=" + newMap.get(key) + "&";
        }
        //去掉最后一个&符号
        query = query.substring(0, query.length() - 1);

        //拼接MD5密钥
        query += signKey;

        //4.MD5加密
        String sign = MD5Util.ToMD5(query);

        //5.拼接请求参数
        String reqParams = "";
        for (String key : newMap.keySet()) {
            try {
                reqParams = reqParams + key + "=" + URLEncoder.encode(newMap.get(key), "UTF-8") + "&";
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        //拼接签名字符串
        reqParams += ("sign" + "=" + sign);

        return reqParams;
    }

    /**
     * 验签
     *
     * @param parameters 待验签参数
     * @param signKey    MD5签名密钥
     * @param signStr    签名字符串
     * @return 是否验签成功
     */
    public static boolean VerifySign(Map<String, String> parameters, String signKey, String signStr) {
        if (parameters == null || signKey == null || signStr == null || signStr.equals(""))
            return false;
        Map<String, String> newMap = new HashMap<String, String>();
        //1.筛选
        for (String key : parameters.keySet()) {
            String value = parameters.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")) {
                continue;
            }
            try {
                newMap.put(key, URLDecoder.decode(value, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        //2.排序
        List<String> keys = new ArrayList<String>(newMap.keySet());
        Collections.sort(keys);

        //3.拼接加密字符串，把所有参数名和参数值串在一起
        String query = "";
        for (String key : keys) {
            query = query + key + "=" + newMap.get(key) + "&";
        }
        //去掉最后一个&符号
        query = query.substring(0, query.length() - 1);

        //拼接MD5密钥
        query += signKey;

        //4.MD5加密
        String sign = MD5Util.ToMD5(query);

        //5.校验签名是否“相等”
        return sign.equals(signStr);
    }

}
