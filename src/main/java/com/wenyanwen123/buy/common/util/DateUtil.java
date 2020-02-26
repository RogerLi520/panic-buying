package com.wenyanwen123.buy.common.util;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @Description: 日期工具类
 * @Author liww
 * @Date 2019/3/13
 * @Version 1.0
 */
public final class DateUtil {

    private DateUtil() { }

    public static final int DAY = 1;
    public static final int HOUR = 2;
    public static final int MINITE = 3;
    public static final int SECOND = 4;
    public static final int MONTH = 5;

    public static final int DAY_SECOND = 24 * 60 * 60;

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT_YMD = "yyyy-MM-dd";
    public static final String DEFAULT_DATE_FORMAT1 = "yyyyMMddHHmmssSSSS";
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    public static final String DATE_FORMAT_CHINA = "yyyy年MM月dd日";
    public static final String DATE_FORMAT_CHINA24 = "yyyy年MM月dd日 HH:mm:ss";
    public static final String DATE_FORMAT_MMDD="MM.dd ";
    public static final String DATE_YYMMDD_HHMM = "yyyy-MM-dd HH:mm";

    private static final DateFormat date_format = new SimpleDateFormat("yyyy/M/d", Locale.ENGLISH);
    private static final DateFormat time_format = new SimpleDateFormat("yyyy/M/d hh:mm:ss", Locale.ENGLISH);
    private static final DateFormat time_format1 = new SimpleDateFormat(DEFAULT_DATE_FORMAT1, Locale.ENGLISH);
    private static final DateFormat date_format1 = new SimpleDateFormat("yyMMdd", Locale.ENGLISH);
    private static final DateFormat date_format2 = new SimpleDateFormat(DATE_FORMAT_YYYYMMDD, Locale.ENGLISH);

    public static String defFormatTime1(Date date) {
        return time_format1.format(date);
    }

    /**
     * @Desc 获取yyMMdd格式的时间字符串
     * @Author liww
     * @Date 2019/7/23
     * @Param [date]
     * @return java.lang.String
     */
    public static String getyyMMdd(Date date) {
        return date_format1.format(date);
    }

    public static String defFormatDate(Date date) {
        return date_format.format(date);
    }

    public static String defFormatTime(Date date) {
        return time_format.format(date);
    }

    public static String dateFormat(Date date) {
        return dateFormat(date, DEFAULT_DATE_FORMAT);
    }

    /**
     * @Desc 与今天相差多少天
     * @Author liww
     * @Date 2019/9/11
     * @Param [timeStamp]
     * @return boolean
     */
    public static boolean isTheDayNum(Integer timeStamp, int dayNum) {
        Integer today = Integer.parseInt(getyyMMdd(new Date()));
        Integer oldDate = Integer.parseInt(date_format1.format(new Date(Long.valueOf(timeStamp.toString() + "000"))));
        if (today - oldDate == dayNum) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @Desc 与今天相差多少天以上
     * @Author liww
     * @Date 2019/10/17
     * @Param [timeStamp, dayNum]
     * @return boolean
     */
    public static boolean isGreaterThanTheDayNum(Integer timeStamp, int dayNum) {
        Integer beginDate = DateUtil.getTimeStamp();
        Integer endDate = timeStamp;
        Integer result = severalDaysApart(beginDate, endDate);
        if (result.intValue() > dayNum) {
            return true;
        }
        return false;
    }

    /**
     * @Desc 两个日期相差几天(八号算一天,八号九号算两天)
     * @Author liww
     * @Date 2019/10/29
     * @Param [beginDate, endDate]
     * @return boolean
     */
    public static Integer severalDaysApart(Integer beginDate, Integer endDate) {
        // 参数校验
        if (beginDate == null || endDate == null) {
            throw new IllegalArgumentException("date is null, check it again");
        }
        int tempBegin = Integer.parseInt(date_format2.format(new Date(Long.valueOf(beginDate.toString() + "000"))));
        int tempEnd = Integer.parseInt(date_format2.format(new Date(Long.valueOf(endDate.toString() + "000"))));

        int beginInt = DateUtil.getTimeStampByInt(tempBegin).intValue();
        int endInt = DateUtil.getTimeStampByInt(tempEnd).intValue();
        int gaps = endInt - beginInt;
        if (beginInt == endInt) {
            return 1;
        }
        // 根据相差的毫秒数计算
        int temp = 24 * 3600;
        double days = (double) gaps / temp;
        int result = (int) Math.ceil(days);
        return result + 1;
    }

    /**
     * @Desc 获取距离今天指定天数的日期时间戳
     * @Author liww
     * @Date 2019/10/29
     * @Param [day]
     * @return java.lang.Integer
     */
    public static Integer getLaterDay(Integer day) {
        Calendar calendar1 = Calendar.getInstance();
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
        calendar1.add(Calendar.DATE, day);
        String daysLater = sdf1.format(calendar1.getTime());
        return getTimeStampByInt(Integer.parseInt(daysLater));
    }

    public static Date dealDateFormat(String date) {
        try {
            date = date.replace("Z", " UTC");//注意是空格+UTC
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");//注意格式化的表达式
            return format.parse(date);
        } catch (ParseException e) {
//            e.printStackTrace();
        }
        return new Date();
    }

    /**
     * 返回格式为yyyy-MM-dd的时间字符
     * @param date
     * @return
     */
    public static String dateFormatYMD(Date date) {
        return dateFormat(date, DEFAULT_DATE_FORMAT_YMD);
    }

    public static String getYestdayYYYYMMDD() {
        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();
        Calendar cal = getInstance();
        cal.set(year, month - 1, day - 1);// 昨天，月是从0开始，所以要-1
        return dateFormat(cal.getTime(), DATE_FORMAT_YYYYMMDD);
    }

    public static String dateFormat(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String str = sdf.format(date);
        return str;
    }

    public static Date dateFormat(String dateStr) {
        if(StringUtils.isBlank(dateStr)) {
            return null;
        }
        return dateFormat(dateStr, DEFAULT_DATE_FORMAT);
    }

    public static Date dateFormat(String dateStr, String format) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        DateFormat dateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }

        return date;
    }

    /**
     * @Desc 获取当前时间，格式yyyy-MM-dd HH:mm:ss
     * @Author liww
     * @Date 2019/3/13
     * @Param []
     * @return java.util.Date
     */
    public static Date getCurrentDate() {
        String dateStr = dateFormat(new Date(), DEFAULT_DATE_FORMAT);
        Date date = dateFormat(dateStr);
        return date;
    }

    /**
     * 获取延迟时间
     *
     * @return
     */
    public static long getDelaytime(int delayhour) {
        Calendar cal = getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        int millsecond = cal.get(Calendar.MILLISECOND);

        long delta = (delayhour - hour) * 3600000 - minute * 60000 - second * 1000 - millsecond;
        if (delta < 0) {
            delta += 86400000;
        }
        return delta;
    }

    /**
     * 获取延迟时间
     *
     * @return
     */
    public static long getWeekDelaytime(int delayhour) {
        Calendar cal = getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        int millsecond = cal.get(Calendar.MILLISECOND);

        long delta = (delayhour - hour) * 3600000 - minute * 60000 - second * 1000 - millsecond;
        int weekDay = getWeekDay();

        int day = 7 - weekDay + 1;

        if (delta < 0) {
            delta += 86400000 * day;
        }
        return delta;
    }

    /**
     * 获取下次刷新时间
     *
     * @return
     */
    public static long getNextTime(int delayhour) {
        Calendar cal = getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        int millsecond = cal.get(Calendar.MILLISECOND);

        long delta = (delayhour - hour) * 3600000 - minute * 60000 - second * 1000 - millsecond;
        if (delta < 0) {
            delta += 86400000;
        }
        return getMillis() + delta;
    }

    // 是否同一天
    public static boolean isTheSameDay(Date d1, Date d2) {
        Calendar c1 = getInstance();
        Calendar c2 = getInstance();
        c1.setTime(d1);
        c2.setTime(d2);

        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
                && (c1.get(Calendar.DATE) == c2.get(Calendar.DATE));
    }

    public static boolean isToday(Date lastDate) {
        Calendar c1 = getInstance();
        Calendar c2 = getInstance();
        c1.setTime(lastDate);
        return (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)) && (c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH))
                && (c1.get(Calendar.DATE) == c2.get(Calendar.DATE));
    }

    public static boolean isYesterday(Date lastDate) {
        return isYesterday(lastDate, currentDate());
    }

    public static boolean isYesterday(Date lastDate, Date date) {
        Calendar last = getInstance();
        Calendar now = getInstance();
        last.setTime(lastDate);
        now.setTime(date);
        last.add(Calendar.DAY_OF_MONTH, 1);
        return (last.get(Calendar.YEAR) == now.get(Calendar.YEAR)) && (last.get(Calendar.MONTH) == now.get(Calendar.MONTH))
                && (last.get(Calendar.DATE) == now.get(Calendar.DATE));
    }

    public static Date getZeroTime() {
        return getBeginTime(0);
    }

    public static Date getZeroTime(Date date) {
        return getBeginTime(date, 0);
    }

    public static Date getBeginTime(int hour) {
        Calendar begin = getInstance();

        begin.set(Calendar.HOUR_OF_DAY, hour);
        begin.set(Calendar.MINUTE, 0);
        begin.set(Calendar.SECOND, 0);
        begin.set(Calendar.MILLISECOND, 0);
        return begin.getTime();
    }

    public static Date getBeginTime(Date date, int hour) {
        Calendar begin = getInstance();
        begin.setTime(date);
        begin.set(Calendar.HOUR_OF_DAY, hour);
        begin.set(Calendar.MINUTE, 0);
        begin.set(Calendar.SECOND, 0);
        begin.set(Calendar.MILLISECOND, 0);
        return begin.getTime();
    }

    public static Date getEndTime() {
        Calendar begin = getInstance();
        begin.set(Calendar.HOUR_OF_DAY, 23);
        begin.set(Calendar.MINUTE, 59);
        begin.set(Calendar.SECOND, 59);
        begin.set(Calendar.MILLISECOND, 999);
        return begin.getTime();
    }

    public static Date getEndTime(Date date) {
        Calendar begin = getInstance();
        begin.setTime(date);
        begin.set(Calendar.HOUR_OF_DAY, 23);
        begin.set(Calendar.MINUTE, 59);
        begin.set(Calendar.SECOND, 59);
        begin.set(Calendar.MILLISECOND, 999);
        return begin.getTime();
    }

    public static boolean isExpired(Date last, int coolSecond) {
        if (null == last || coolSecond <= 0) {
            return true;
        }
        Date coolPassDate = addTime(SECOND, coolSecond, last);

        return coolPassDate.before(currentDate());
    }

    public static boolean isExpired(Date last) {
        if (null == last) {
            return true;
        }
        Date now = currentDate();

        return last.before(now);
    }

    public static Date addTime(int param, int add) {
        return addTime(param, add, currentDate());
    }

    public static Date addTime(int param, int add, Date date) {
        if (null == date) {
            return null;
        }
        Calendar begin = getInstance();
        begin.setTime(date);

        if (DAY == param)
            begin.add(Calendar.DATE, add);
        if (HOUR == param)
            begin.add(Calendar.HOUR_OF_DAY, add);
        if (MINITE == param)
            begin.add(Calendar.MINUTE, add);
        if (SECOND == param)
            begin.add(Calendar.SECOND, add);
        if (MONTH == param) {
            begin.add(Calendar.MONTH, add);
        }

        return begin.getTime();
    }

    public static long getSecondsToGoFromNow(Date fromDate) {
        if (null == fromDate) {
            return -1L;
        }
        Date now = currentDate();
        if (now.before(fromDate)) {
            return 0L;
        }
        return (now.getTime() - fromDate.getTime()) / 1000L;
    }

    public static long getSecondsToGo(Date fromDate, Date toDate) {
        if ((null == fromDate) || (null == toDate)) {
            return -1L;
        }
        if (toDate.before(fromDate)) {
            return 0L;
        }
        return (toDate.getTime() - fromDate.getTime()) / 1000L;
    }

    /**
     * 得到两个时间相差的毫秒数
     * @param fromDate
     * @return
     */
    public static long getMilliSecondToGoFromNow(Date fromDate) {
        if (null == fromDate) {
            return -1L;
        }
        Date now = currentDate();
        return Math.abs(now.getTime() - fromDate.getTime());
    }

    public static int getSeconds() {
        return (int) (getMillis() / 1000L);
    }

    public static long getMillis() {
        return currentDate().getTime();
    }

    public static int getWeekDay() {
        Calendar cal = getInstance();

        int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week == 0)
            week = 7;
        return week;
    }

    public static int getHourOfDay() {
        Calendar cal = getInstance();
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    public static int getMonthDay() {
        Calendar cal = getInstance();

        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     *
     * @Title: currentDate
     * @Description: 获得当前时间
     * @param @return 参数
     * @return Date 返回类型
     * @throws
     */
    public static Date currentDate() {
        Calendar cal = getInstance();
        return cal.getTime();
    }

    public static Calendar getInstance() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        return cal;
    }

    /**
     * 将date转为系统默认时区LocalDatetime
     * @param date
     * @return
     */
    public static LocalDateTime uDateToLocalDateTime(Date date){
        Instant instant=date.toInstant();
        ZoneId zoneId=ZoneId.systemDefault();
        LocalDateTime localDateTime=LocalDateTime.ofInstant(instant,zoneId);
        return localDateTime;
    }



    // 新增方法
    /**
     * 日期增加天数
     *
     * @param date 日期
     * @param day  增加天数
     * @return
     * @throws ParseException
     */
    public static Date addDays(Date date, long day) {
        long time = date.getTime(); // 得到指定日期的毫秒数
        day = day * 24 * 60 * 60 * 1000; // 要加上的天数转换成毫秒数
        time += day; // 相加得到新的毫秒数
        return new Date(time); // 将毫秒数转换成日期
    }

    /**
     * @Desc 获取当前时区的时间戳（1979-1-1至今的毫秒数）
     * @Author liww
     * @Date 2019/6/17
     * @Param []
     * @return long
     */
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * @Desc 获取秒级的时间戳
     * @Author liww
     * @Date 2019/8/26
     * @Param []
     * @return java.lang.Integer
     */
    public static Integer getTimeStamp() {
        Integer currTime = new Long(System.currentTimeMillis() / 1000).intValue();
        return currTime;
    }

    /**
     * @Desc 根据日期获取秒级的时间戳
     * @Author liww
     * @Date 2019/8/26
     * @Param [date]
     * @return java.lang.Integer
     */
    public static Integer getTimeStampByDate(Date date) {
        long timeStamp = date.getTime() / 1000;
        return new Long(timeStamp).intValue();
    }

    /**
     * @Desc 根据int类型的日期获取秒级的时间戳
     * @Author liww
     * @Date 2019/8/26
     * @Param [date]
     * @return java.lang.Integer
     */
    public static Integer getTimeStampByInt(Integer date) {
        return getTimeStampByDate(getDateByInt(date));
    }

    /**
     * @Desc 根据int类型的日期获取时间
     * @Author liww
     * @Date 2019/8/26
     * @Param [dateInt]
     * @return java.util.Date
     */
    public static Date getDateByInt(Integer dateInt) {
        String dateString = dateInt.toString();
        DateFormat format = new SimpleDateFormat(DATE_FORMAT_YYYYMMDD);
        Date date = null;
        try {
            date = format.parse(dateString);
        } catch (Exception e) {

        }
        return date;
    }

    public static Integer getDateInt() {
        return Integer.parseInt(dateFormat2(getDate(), "yyyyMMdd"));
    }

    public static Date getDate() {
        return new Date();
    }

    /**
     * 格式化日期
     */
    public static String dateFormat2(Date dt, String format) {
        return new SimpleDateFormat(format).format(dt);
    }

    /**
     * date2比date1多的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int differentDays(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);
        int day1 = cal1.get(Calendar.DAY_OF_YEAR);
        int day2 = cal2.get(Calendar.DAY_OF_YEAR);

        int year1 = cal1.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        if (year1 != year2)   //同一年
        {
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0)    //闰年
                {
                    timeDistance += 366;
                } else    //不是闰年
                {
                    timeDistance += 365;
                }
            }

            return timeDistance + (day2 - day1);
        } else    //不同年
        {
//            System.out.println("判断day2 - day1 : " + (day2-day1));
            return day2 - day1;
        }
    }

    /**
     * @Desc 将Int类型的时间转为Data类型
     * @Author liww
     * @Date 2019/7/10
     * @Param [dateNum]
     * @return java.util.Date
     */
    public static Date getIntToDate(int dateNum) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        try {
            return formatter.parse(dateNum + "");
        } catch (ParseException e) {
            //e.printStackTrace();
        }
        return new Date();
    }

}
