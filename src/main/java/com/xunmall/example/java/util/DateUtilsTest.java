package com.xunmall.example.java.util;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by owen5 on 2017/4/6.
 */
public class DateUtilsTest {

    @Test
    public void testMain() throws ParseException {
        String dateStr = "2018-04-21 21:22:15";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = dateFormat.parse(dateStr.substring(0, 10) + " 00:00:00");
        System.out.println(dateFormat.format(date));
        Date endDate = getNowDate(date, 24 * 60);
        System.out.println(dateFormat.format(endDate));
        System.out.println(System.currentTimeMillis());

        String dateStr2 = "2018-04-18 15:54:56";
        Date publicTime = dateFormat.parse(dateStr2);
        Date diff = addDays(publicTime, 7);
        if (diff.after(now())) {
            return;
        }
        Date publicTime2 = null;
        Date diff2 = addDays(publicTime2, 2);
        if (isToday(diff2.getTime())) {
            System.out.println("11111111111");
        }
    }

    @Test
    public void testDateFormate() {
        String dateStr3 = "2018-04-18 15:54:56";
        Date startDate = DateUtils.parse(dateStr3, DateUtils.DATE_FORMAT_YYYY_MM_DD);
        System.out.println(startDate);
    }


    public static Date getNowDate(Date startDate, int minute) {
        Date now = new Date();
        if (null != startDate && startDate.getTime() + minute * 60 * 1000 < now.getTime()) {
            now = new Date(startDate.getTime() + minute * 60 * 1000);
        }
        return now;
    }

    public static Date now() {
        return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    }

    // 获取两个时间的差距的天数
    public static int diffDays(Date date1, Date date2) {
        Calendar cDate1 = Calendar.getInstance();
        Calendar cDate2 = Calendar.getInstance();
        cDate1.setTime(date1);
        cDate1.set(Calendar.HOUR_OF_DAY, 0);
        cDate1.set(Calendar.MINUTE, 0);
        cDate1.set(Calendar.SECOND, 0);
        cDate1.set(Calendar.MILLISECOND, 0);

        cDate2.setTime(date2);
        cDate2.set(Calendar.HOUR_OF_DAY, 0);
        cDate2.set(Calendar.MINUTE, 0);
        cDate2.set(Calendar.SECOND, 0);
        cDate2.set(Calendar.MILLISECOND, 0);

        date1 = cDate1.getTime();
        date2 = cDate2.getTime();

        Date bigDay = null;
        Date smallDay = null;
        if (date1.after(date2)) {
            bigDay = date1;
            smallDay = date2;
        } else {
            bigDay = date2;
            smallDay = date1;
        }
        return (int) (bigDay.getTime() / 86400000 - smallDay.getTime() / 86400000);
    }

    // 获取两个时间的差距的天数
    public static int diffDays2(Date date1, Date date2) {
        Calendar cDate1 = Calendar.getInstance();
        Calendar cDate2 = Calendar.getInstance();
        cDate1.setTime(date1);
        cDate1.set(Calendar.HOUR_OF_DAY, 0);
        cDate1.set(Calendar.MINUTE, 0);
        cDate1.set(Calendar.SECOND, 0);
        cDate1.set(Calendar.MILLISECOND, 0);

        cDate2.setTime(date2);
        cDate2.set(Calendar.HOUR_OF_DAY, 0);
        cDate2.set(Calendar.MINUTE, 0);
        cDate2.set(Calendar.SECOND, 0);
        cDate2.set(Calendar.MILLISECOND, 0);

        date1 = cDate1.getTime();
        date2 = cDate2.getTime();

        // return date1.getTime() / (24*60*60*1000) - date2.getTime() /
        // (24*60*60*1000);
        return (int) (date1.getTime() / 86400000 - date2.getTime() / 86400000);// 用立即数，减少乘法计算的开销
    }

    // 加几天
    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    //判断选择的日期是否是今天
    public static boolean isToday(long time) {
        return isThisTime(time, "yyyy-MM-dd");
    }

    private static boolean isThisTime(long time, String pattern) {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        String param = sdf.format(date);//参数时间
        String now = sdf.format(new Date());//当前时间
        if (param.equals(now)) {
            return true;
        }
        return false;
    }

    @Test
    public void testZero() {
        int beginTime = DateUtils.getTimeStamp(DateUtils.weeHours(DateUtils.daysLose(DateUtils.now(), 1), 0));
        int endTime = DateUtils.getTimeStamp(DateUtils.weeHours(DateUtils.now(), 0));
        int baseTime = DateUtils.getTimeStamp(DateUtils.weeHours(DateUtils.daysLose(DateUtils.now(), 1), 1));
        System.out.println(beginTime);
        System.out.println(endTime);
        System.out.println(baseTime);
        System.out.println(DateUtils.weeHours(DateUtils.now(), 0).getTime());
    }


    @Test
    public void testDiffSecond() {
        String alarmTime = "2021-07-02 10:32:20";
        long currentTime = System.currentTimeMillis();
        long alarmTimeStamp = DateUtils.parse(alarmTime,DateUtils.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS).getTime();
        long abs = Math.abs(currentTime - alarmTimeStamp);
        if (abs < 120000){
            System.out.println(true);
        }
        System.out.println(alarmTime.substring(0,10));

        String lastPushTime = "2021-07-06 10:21:20";
        Date lastTime = DateUtils.parse(lastPushTime, DateUtils.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS);
        int differentMinutes = DateUtils.differentMinutes(lastTime, DateUtils.now());
        if (differentMinutes > 10 && differentMinutes < 11)
        System.out.println(differentMinutes);
    }
}
