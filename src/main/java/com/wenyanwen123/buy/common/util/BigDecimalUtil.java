package com.wenyanwen123.buy.common.util;

import java.math.BigDecimal;

/***
 * @Desc 金额运算
 * @Author liww
 * @Date 2019/7/8
 * @Param
 * @return
 */
public class BigDecimalUtil {

    /**
     * @Desc 是否等于0
     * @Author liww
     * @Date 2019/7/8
     * @Param [number]
     * @return boolean
     */
    public static boolean isEqualsZero(BigDecimal number) {
        if (number.compareTo(BigDecimal.ZERO) == 0) {
            return true;
        }
        return false;
    }

    /**
     * @Desc 比较a和b的大小，a比b小返回true
     * @Author liww
     * @Date 2019/7/8
     * @Param [a, b]
     * @return boolean
     */
    public static boolean compare(BigDecimal a, BigDecimal b) {
        if (a == null) {
            return true;
        }
        if (a.compareTo(b) == -1) {
            return true;
        }
        return false;
    }

    /**
     * @Desc 判断是否小于0
     * @Author liww
     * @Date 2019/7/24
     * @Param [number]
     * @return boolean
     */
    public static boolean isLessThanZero(BigDecimal number) {
        if (number.compareTo(BigDecimal.ZERO) < 0) {
            return true;
        }
        return false;
    }

    /**
     * @Desc 判断是否小于等于0
     * @Author liww
     * @Date 2019/7/24
     * @Param [number]
     * @return boolean
     */
    public static boolean isLessThanOrEqualsZero(BigDecimal number) {
        if (number.compareTo(BigDecimal.ZERO) <= 0) {
            return true;
        }
        return false;
    }

    /**
     * @Desc 除法运算，四舍五入，保留两位小数
     * @Author liww
     * @Date 2019/7/25
     * @Param [a, b]
     * @return java.math.BigDecimal
     */
    public static BigDecimal divideRoundHalfUp(BigDecimal a, BigDecimal b) {
        BigDecimal result =  a.divide(b, 2, BigDecimal.ROUND_HALF_UP);
        return result;
    }

    /**
     * @Desc 对某个数进行四舍五入，保留两位小数操作
     * @Author liww
     * @Date 2019/10/21
     * @Param [a]
     * @return java.math.BigDecimal
     */
    public static BigDecimal setScaleRoundHalfUp(BigDecimal a) {
        return a.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

}
