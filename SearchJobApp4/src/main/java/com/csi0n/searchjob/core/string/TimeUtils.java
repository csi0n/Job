package com.csi0n.searchjob.core.string;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chqss on 2016/5/14 0014.
 */
public class TimeUtils {
    @SuppressWarnings("deprecation")
    public static String getDistanceTime(long time2) {
        Date now = new Date();
        long day = 0;//天数
        long hour = 0;//小时
        long min = 0;//分钟
        long sec = 0;//秒
        try {
            long time1 = now.getTime();
            time2 = time2*1000l;
            long diff ;
            if(time1<time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000));
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff/1000-day*24*60*60-hour*60*60-min*60);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String rs="";
        if (hour==0) {
            rs=min+"分钟前";
            return rs;
        }
        if (day==0&&hour<=4) {
            rs=hour+"小时前";
            return rs;
        }
        SimpleDateFormat format =   new SimpleDateFormat( "MM-dd HH:mm" );//
        String d = format.format(time2);
        Date date = null;
        try {
            date = format.parse(d);//把字符类型的转换成日期类型的！
        } catch (ParseException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        if (now.getDate()-date.getDate()==0) {//当前时间和时间戳转换来的时间的天数对比
            DateFormat df2 = new SimpleDateFormat("HH:mm");
            rs="今天  "+df2.format(time2);
            return rs;
        } else if (now.getDate()-date.getDate()==1) {
            DateFormat df2 = new SimpleDateFormat("HH:mm");
            rs="昨天  "+df2.format(time2);
            return rs;
        } else {
            DateFormat df2 = new SimpleDateFormat("MM-dd HH:mm");
            rs=df2.format(time2);
            return rs;
        }
    }
}
