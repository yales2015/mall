package com.yy.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Created by yy on 2018/1/19.
 */
public class DateTimeUtil {

    public static final String STANDARD_FORMAT="yy-MM-dd HH:mm:ss";

    public static Date strToDate(String dataTimeStr,String formatStr){
        DateTimeFormatter dateTimeFormatter= DateTimeFormat.forPattern(formatStr);
        DateTime dateTime=dateTimeFormatter.parseDateTime(dataTimeStr);
        return dateTime.toDate();
    }

    public static String dateToStr(Date date,String formatStr){
        if(date==null){
            return null;
        }
        DateTime dateTime=new DateTime(date);
        return dateTime.toString(formatStr);
    }

    public static Date strToDate(String dataTimeStr){
        DateTimeFormatter dateTimeFormatter= DateTimeFormat.forPattern(STANDARD_FORMAT);
        DateTime dateTime=dateTimeFormatter.parseDateTime(dataTimeStr);
        return dateTime.toDate();
    }

    public static String dateToStr(Date date){
        if(date==null){
            return null;
        }
        DateTime dateTime=new DateTime(date);
        return dateTime.toString(STANDARD_FORMAT);
    }
}
