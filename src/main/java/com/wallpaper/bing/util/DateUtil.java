package com.wallpaper.bing.util;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * author GaoPC
 * date 2017-12-12 14:34
 * description  日期格式转化类
 */

@SuppressWarnings("unused")
public class DateUtil {

    @StringDef({DatePattern.YYYY_MM_DD,DatePattern.YYYYMMDD,DatePattern.YYYYSMMSDD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface DatePattern{
        String YYYY_MM_DD="yyyy-MM-dd";
        String YYYYMMDD="yyyyMMdd";
        String YYYYSMMSDD="yyyySMMSdd";
    }


    /**
     * 得到格式为20171216的当前日期的String字符串
     */
    public static String getCurrentDate() {
        Date d = new Date();
        SimpleDateFormat sf = new SimpleDateFormat(DatePattern.YYYYMMDD, Locale.CHINA);
        return sf.format(d);
    }

    /**
     * 得到传入日期的下一天
     *
     * @param date 格式yyyyMMdd
     * @return 返回下一天日期 格式为yyyyMMdd
     */
    public static String getNextDate(String date) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        Date d = null;
        try {
            d = sf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(d);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        return sf.format(calendar.getTime());
    }

    /**
     * 得到传入日期的上一天
     *
     * @param date 格式yyyyMMdd
     * @return 返回上一天日期 格式为yyyyMMdd
     */
    public static String getPreviousDate(String date) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        Date d = null;
        try {
            d = sf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(d);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        return sf.format(calendar.getTime());
    }


    /**
     * 将时间戳转化为格式为pattern的string字符串
     *
     * @param time    时间戳
     * @param pattern 要转成的时间格式
     * @return string
     * @see DatePattern
     */
    public static String getDateToString(long time, @DatePattern String pattern) {
        Date d = new Date(time);
        SimpleDateFormat sf = new SimpleDateFormat(pattern, Locale.CHINA);
        return sf.format(d);
    }

    /**
     * 将 yyyyMMdd 转成时间戳
     *
     * @param date    yyyyMMdd
     * @param pattern 要转成的格式
     * @return 时间戳
     * @see DatePattern
     */
    public static Timestamp stringToDate(String date,@DatePattern String pattern) {
        SimpleDateFormat sf = new SimpleDateFormat(pattern, Locale.CHINA);
        Date d = null;
        try {
            d = sf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Timestamp(d != null ? d.getTime() : 0);
    }


    /**
     * 将 pattern1 格式的日期 转成 pattern2 格式的日期
     *
     * @param date     要转换的日期
     * @param pattern1 date的格式
     * @param pattern2 要转成的格式
     * @return 转换后的格式
     * @see DatePattern
     */
    public static String stringToString(String date,@DatePattern String pattern1,@DatePattern String pattern2) {
        SimpleDateFormat sf = new SimpleDateFormat(pattern1, Locale.CHINA);
        Date d;
        try {
            d = sf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        return new SimpleDateFormat(pattern2, Locale.CHINA).format(d);
    }

}
