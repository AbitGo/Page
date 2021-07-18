package com.example.reapp.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 时间工具类
 */
public class DateUtil {
    /**
     * 获得US星期
     * @return
     */
    public static String getWeekDayUS(){
        return new SimpleDateFormat("EEEE").format(new Date());
    }

    /**
     * 获得CN星期
     * @return
     */
    public static String getWeekDayCN(){
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        return weekDays[Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1];
    }

    /**
     * 将时间转换为Linux时间戳
     * @param timer 格式化时间字符串
     * @return
     */
    public static long toTimeStamp(String timer){
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timer);
            return date.getTime()/1000;
        } catch (ParseException e) {
            e.printStackTrace();
            System.out.println("转换失败");
        }
        return 0;
    }

    /**
     * 将Linux时间戳转为格式化时间
     * @param unixTime  Linux时世时间戳
     * @return
     */
    public static String toFormatTime(long unixTime){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(unixTime * 1000);
    }

    public static void compareDate(String date1,String date2){
        if (date1.compareTo(date2) > 0){
            System.out.println("大于");
        }else if (date1.compareTo(date2) < 0){
            System.out.println("小于");
        }else {
            System.out.println("等于");
        }
    }

}
