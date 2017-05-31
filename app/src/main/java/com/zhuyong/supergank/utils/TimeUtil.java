package com.zhuyong.supergank.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhuyong on 2016/1/29.
 */
public class TimeUtil {
    public static String getFormatTime(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        try {
            long millisecond = sdf.parse(time).getTime();
            long difference = new Date().getTime() - millisecond;
            int minutes = (int) (difference / (1000 * 60));

            if (minutes < 5){
                return "刚刚";
            }else if (minutes < 60){
                return minutes + "分钟前";
            }else if (minutes < 60 * 24){
                return minutes / 60 + "小时前";
            }else if (minutes < 60 * 24 * 30){
                return minutes / (60 * 24) + "天前";
            }else {
                return "很久了";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "...";
    }

    /**
     * 判断当前时间是否是白天
     * @return true
     */
    public static boolean getIsDayHour() {
        SimpleDateFormat format = new SimpleDateFormat("HH");
        String time = format.format(new Date().getTime());
        int hour = Integer.parseInt(time);
        if (hour >= 6 && hour <= 18) {
            return true;
        }
        return false;
    }
}
