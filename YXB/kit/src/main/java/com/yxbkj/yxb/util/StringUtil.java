package com.yxbkj.yxb.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public class StringUtil {


    /**
     * 产生UUID
     * @return
     */
    public  static String getUuid(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    /**
     * 产生TOKEN
     * @return
     */
    public  static String getTokenById(String id){
        String token  = id;//+System.currentTimeMillis();
        return MD5Util.encryption(token);
    }

    public static String getCurrentDateStr() {
        SimpleDateFormat aDate=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String format = aDate.format(new Date());
        return format;
    }
    public static String getCurrentDateStr(Long addTime) {
        SimpleDateFormat aDate=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date date = new Date();
        date.setTime(date.getTime()+addTime);
        String format = aDate.format(date);
        return format;
    }
    public static String getAddDateStr(int addtime) {
        SimpleDateFormat aDate=new SimpleDateFormat("yyyyMMddHHmmssSSS");
        Date date = new Date();
        date.setTime(date.getTime()+addtime*24L*60*60*1000);
        String format = aDate.format(date);
        return format;
    }

    /**
     *判断字符串
     * @return
     */
    public  static boolean isEmpty(String str){
       if(str==null || "".equals(str)){
           return true;
       }
        return false;
    }
    /**
     *日期格式转字符串
     * @return
     * @author zy
     */
    public static String dateCastToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(date);
    }
    /**
     *日期格式转字符串 转换成精确到秒字符串
     * @return
     * @author zy
     */
    public static String dateCastToStringS(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return dateFormat.format(date);
    }
    public static String dateNow() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(new Date());
        return format;
    }
    /**
     * 字符串转日期
     * @author zy
     * @desc
     * @since
     */
    public static Date stringCastToDate(String dateTime){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date parseTime = dateFormat.parse(dateTime);
            return parseTime;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
     * @return
     * @author zy
     */
    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime()
                || nowTime.getTime() == endTime.getTime()) {
            return true;
        }
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 获取当前月第一天
     * @return
     * @author zy
     */
    public static String getFirstDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();

        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first).append(" 00:00:00");
        return str.toString();
    }
    /**
     * 获取当前月最后一天
     * @return
     * @author zy
     */
    public static String getLastDay() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();
        String s = df.format(theDate);
        calendar.add(Calendar.MONTH, 1);//加一个月
        calendar.set(Calendar.DATE, 1);//设置为该月第一天
        calendar.add(Calendar.DATE, -1);//再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last);
        day_last = endStr.toString();
        StringBuffer str = new StringBuffer().append(day_last).append(" 23:59:59");
        return str.toString();
    }
    /**
     * 获取当前月
     * @return
     * @author zy
     */
    public static String dateNowMonth() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = dateFormat.format(new Date());
        return format;
    }
    /**
     * 字符串转日期
     * @author zy
     * @desc
     * @since
     */
    public static Date stringCastToDate1(String dateTime){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parseTime = dateFormat.parse(dateTime);
            return parseTime;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 字符串转日期
     * @author zy
     * @desc
     * @since
     */
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
}
