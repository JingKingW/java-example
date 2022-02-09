package com.xunmall.example.java.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * 功能描述: 日期工具类
 *
 * @Author: wangyanjing
 * @Date: 2019/1/25 14:01
 **/
public class DateUtils {


    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

    private static final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //把日期转为字符串
    public static String format(Date _date, String _format) {
        if (_date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setLenient(false);
        sdf.applyPattern(_format);
        try {
            return sdf.format(_date);
        } catch (Exception ex) {
            return null;
        }
    }

    // 把字符串转为日期
    public static Date parse(String _str, String _format) {
        Date dt = null;
        if (_str != null && _format != null) {
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.setLenient(false);
            sdf.applyPattern(_format);
            ParsePosition pos = new ParsePosition(0);
            dt = sdf.parse(_str, pos);
        }
        return dt;
    }

    /**
     * 字符串转时间戳
     *
     * @param date
     * @return
     */
    public static Timestamp getTime(String date) {
        return Timestamp.valueOf(date);
    }

    /**
     * 根据日期相减多少天
     *
     * @param day 天数
     * @return
     */
    public static Date daysLose(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DATE, cal.get(Calendar.DATE) - day);
        return cal.getTime();
    }

    /**
     * 根据日期相加多少天
     *
     * @param date 相加前日期
     * @param day  天数
     * @return
     */
    public static Date daysPlusOfDate(Date date, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, day);
        return cal.getTime();
    }


    public static Date now() {
        return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    }

    public String getNow() {
        return format(now(), DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
    }

    public static Date getDateYMD(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取当前月第一天
     *
     * @return
     */
    public static Date getMonthFirstDay() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    /**
     * 获取当前月最后一天
     *
     * @return
     */
    public static Date getMonthLastDay() {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        return ca.getTime();
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
        if (year1 != year2) {  //同一年
            int timeDistance = 0;
            for (int i = year1; i < year2; i++) {
                if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) {   //闰年
                    timeDistance += 366;
                } else {  //不是闰年
                    timeDistance += 365;
                }
            }
            return timeDistance + (day2 - day1);
        } else {  //不同年
            return day2 - day1;
        }
    }

    public static int differentHours(Date beginTime, Date endTime) {
        int hours = 0;
        if (beginTime == null || endTime == null) {
            return hours;
        }
        long fromTime = beginTime.getTime();
        long toTime = endTime.getTime();
        long differSecond;
        if (fromTime >= toTime) {
            differSecond = fromTime - toTime;
        } else {
            differSecond = toTime - fromTime;
        }
        BigDecimal v1 = new BigDecimal(differSecond);
        BigDecimal v2 = new BigDecimal(1000 * 60 * 60);
        hours = (v1.divide(v2, 0, RoundingMode.UP)).intValue();
        return hours;
    }

    /**
     * 把传入的时间加（减）小时
     *
     * @param time
     * @param hour
     * @return
     */
    public static Date getBackUpHours(Date time, int hour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.add(Calendar.HOUR, hour);
        return cal.getTime();
    }

    public static int getTimeStamp(Date dateTime) {
        Long dateTimeStamp = dateTime.getTime();
        String timeStamp = String.valueOf(dateTimeStamp).substring(0, 10);
        return Integer.valueOf(timeStamp);
    }

    /**
     * 凌晨
     *
     * @param date
     * @return
     * @flag 0 返回yyyy-MM-dd 00:00:00日期<br>
     *       1 返回yyyy-MM-dd 23:59:59日期
     */
    public static Date weeHours(Date date, int flag) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        int second = cal.get(Calendar.SECOND);
        //时分秒（毫秒数）
        long millisecond = hour * 60 * 60 * 1000 + minute * 60 * 1000 + second * 1000;
        //凌晨00:00:00
        cal.setTimeInMillis(cal.getTimeInMillis() - millisecond);

        if (flag == 0) {
            return cal.getTime();
        } else if (flag == 1) {
            //凌晨23:59:59
            cal.setTimeInMillis(cal.getTimeInMillis() + 23 * 60 * 60 * 1000 + 59 * 60 * 1000 + 59 * 1000);
        }
        return cal.getTime();
    }

    public static Date addMilliSecond(Date date, long timestamps) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(date.getTime() + timestamps);
        return calendar.getTime();
    }

    public static int differentMinutes(Date beginTime, Date endTime) {
        int minutes = 0;
        if (beginTime == null || endTime == null) {
            return minutes;
        }
        long fromTime = beginTime.getTime();
        long toTime = endTime.getTime();
        long differSecond;
        if (fromTime >= toTime) {
            differSecond = fromTime - toTime;
        } else {
            differSecond = toTime - fromTime;
        }
        BigDecimal v1 = new BigDecimal(differSecond);
        BigDecimal v2 = new BigDecimal(1000 * 60 );
        minutes = (v1.divide(v2, 0, RoundingMode.UP)).intValue();
        return minutes;
    }

}
