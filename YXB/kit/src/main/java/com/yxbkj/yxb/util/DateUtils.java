package com.yxbkj.yxb.util;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * ClassName：DateUtils
 * Description：日期工具类
 * Author：李明
 * Created：2017/07/28
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils{

	public static final String TIME_WITH_MINUTE_PATTERN = "HH:mm";
	public static final long DAY_MILLI = 24 * 60 * 60 * 1000; // 一天的MilliSecond
	public final static int LEFT_OPEN_RIGHT_OPEN = 1;
	public final static int LEFT_CLOSE_RIGHT_OPEN = 2;
	public final static int LEFT_OPEN_RIGHT_CLOSE = 3;
	public final static int LEFT_CLOSE_RIGHT_CLOSE = 4;

    /**
     * 获取当前时间
     * @return
     */
    public static String getSysDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

	/**
	 * 比较日期的模式 --只比较日期，不比较时间
	 */
	public final static int COMP_MODEL_DATE = 1;
	/**
	 * 比较日期的模式 --只比较时间，不比较日期
	 */
	public final static int COMP_MODEL_TIME = 2;
	/**
	 * 比较日期的模式 --比较日期，也比较时间
	 */
	public final static int COMP_MODEL_DATETIME = 3;

	/**
	 * 要用到的DATE Format的定义
	 */
	public static final String DATE_FORMAT_DATEYEAR = "yyyy"; // 年
	public static final String DATE_FORMAT_DATEMONTN = "yyyy-MM"; // 年-月
	public static final String DATE_FORMAT_CREDIT = "MMyy"; // 信用卡有效期
	public static final String DATE_FORMAT_CHANGE_CREDIT = "yyMM"; // 信用卡有效期
	public static final String DATE_FORMAT_DATEDAY = "yyyy-MM-dd"; // 年-月-日
	public static final String DATE_FORMAT_DATESHORTDAY = "yyyyMMdd"; // 年月日
	public static final String DATE_FORMAT_TIME = "HH:mm:ss"; // 时:分:秒
	public static final String DATE_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss"; // 年-月-日 时:分:秒
	public static final String DATE_FORMAT_DATETIMEMS = "yyyyMMddHHmmss"; // 年月日时分秒
	public static final SimpleDateFormat sdfDateYear = new SimpleDateFormat(DATE_FORMAT_DATEYEAR);
	public static final SimpleDateFormat sdfDateMonth = new SimpleDateFormat(DATE_FORMAT_DATEMONTN);
	public static final SimpleDateFormat sdfDateDay = new SimpleDateFormat(DATE_FORMAT_DATEDAY);
	public static final SimpleDateFormat sdfDateShortDay = new SimpleDateFormat(DATE_FORMAT_DATESHORTDAY);
	public static final SimpleDateFormat sdfTime = new SimpleDateFormat(DATE_FORMAT_TIME);
	public static final SimpleDateFormat sdfDateTime = new SimpleDateFormat(DATE_FORMAT_DATETIME);
	public static final SimpleDateFormat sdfDateTimeMS = new SimpleDateFormat(DATE_FORMAT_DATETIMEMS);

	/**
	 * 字符转换日期
	 * @param str
	 * @param pattern
	 * @return
	 */
	public static Date strToDate(String str, String pattern){
		Date parseDate = null;
		try {
			parseDate = parseDate(str, new String[] {pattern});
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return parseDate;
	}

	/**
	 * 日期转换字符
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String dateToStr(Date date, String pattern){
		String dateStr = null;
		if(DATE_FORMAT_DATEYEAR.equals(pattern)){
			dateStr = sdfDateYear.format(date);
		}else if(DATE_FORMAT_DATEMONTN.equals(pattern)){
			dateStr = sdfDateMonth.format(date);
		}else if(DATE_FORMAT_DATEDAY.equals(pattern)){
			dateStr = sdfDateDay.format(date);
		}else if(DATE_FORMAT_DATESHORTDAY.equals(pattern)){
			dateStr = sdfDateShortDay.format(date);
		}else if(DATE_FORMAT_TIME.equals(pattern)){
			dateStr = sdfTime.format(date);
		}else if(DATE_FORMAT_DATETIME.equals(pattern)){
			dateStr = sdfDateTime.format(date);
		}else if(DATE_FORMAT_DATETIMEMS.equals(pattern)){
			dateStr = sdfDateTimeMS.format(date);
		}else{
			dateStr = "";
		}
		return dateStr;
	}

	/**
	 * 字符转日期
	 * @param dateStr
	 * @return
	 */
	public static Date getDateByStr(String dateStr) {
		SimpleDateFormat formatter = null;
		if (dateStr == null) {
			return null;
		} else if (dateStr.length() == 10) {
			formatter = new SimpleDateFormat("yyyy-MM-dd");
		} else if (dateStr.length() == 13) {
			formatter = new SimpleDateFormat("yyyy-MM-dd HH");
		} else if (dateStr.length() == 16) {
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		} else if (dateStr.length() == 19) {
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else if (dateStr.length() > 19) {
			dateStr = dateStr.substring(0, 19);
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else {
			return null;
		}
		try {
			return formatter.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 日期格式化
	 * @param date
	 * @param format
	 * @return
	 */
	public static Date dateFormat(Date date, String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date value = new Date();
		try {
			value = sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * 获得当前的时间戳
	 * @return
	 */
	public static Timestamp getCurrentTimeStamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 获得当前日期
	 * @return
	 */
	public static Date getCurrentDate() {
		return new Date();
	}

	/**
	 * 获得当前日期
	 * @return
	 */
	public static String getCurrentDateStr() {
		return dateToStr(new Date(),DATE_FORMAT_DATEDAY);
	}

	/**
	 * 获得当前日期
	 * @return
	 */
	public static String getCurrentDateStrs() {
		return dateToStr(new Date(),DATE_FORMAT_DATETIME);
	}
	
	/**
	 * 获得小时数
	 * @param date
	 * @return
	 */
	public static int getHour(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获得分钟数
	 * @param date
	 * @return
	 */
	public static int getMinute(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MINUTE);
	}

	/**
	 * 获得秒数
	 * @param date
	 * @return
	 */
	public static int getSecond(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.SECOND);
	}

	/**
	 * 获得毫秒数
	 * @param date
	 * @return
	 */
	public static long getMillis(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getTimeInMillis();
	}

	/**
	 * 获取昨日的日期格式串
	 * @return Date
	 */
	public static String getYesterday() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		return dateToStr(calendar.getTime(), "yyyy-MM-dd");
	}

	/**
	 * 获取昨日的日期
	 * @return Date
	 */
	public static Date getYesterdayDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		String yesterday = dateToStr(calendar.getTime(), "yyyy-MM-dd");
		try {
			return sdfDateDay.parse(yesterday);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 得到当前日期的星期
	 * @return
	 */
	public static int getWeek() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		int w = cal.get(Calendar.DAY_OF_WEEK);
		return w;
	}

	/**
	 * 计算second秒后的时间
	 * @param date
	 * @param second
	 * @return
	 */
	public static Date addSecond(Date date, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, second);
		return calendar.getTime();
	}

	/**
	 * 计算minute分钟后的时间
	 * @param date
	 * @param minute
	 * @return
	 */
	public static Date addMinute(Date date, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minute);
		return calendar.getTime();
	}

	/**
	 * 计算hour小时后的时间
	 * @param date
	 * @param hour
	 * @return
	 */
	public static Date addHour(Date date, int hour) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.HOUR, hour);
		return calendar.getTime();
	}

	/**
	 * 计算day天后的时间
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDay(Date date, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}
	/**
	 * 计算month月后的时间
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date addMonth(Date date, int month){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		return calendar.getTime();
	}

	/**
	 * 计算year年后的时间
	 * @param date
	 * @param year
	 * @return
	 */
	public static Date addYear(Date date, int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, 365 * year);
		return calendar.getTime();
	}

	/**
	 * 得到当前天的起始时间点
	 * @param date
	 * @return
	 */
	public static Date getDayStart(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 得到当前天的截止时间
	 * @param date
	 * @return
	 */
	public static Date getDayEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		return calendar.getTime();
	}
	
	public static void main(String[] args) {
		//Tue Jan 30 23:59:59 CST 2018
//		System.out.println(getDayEnd());
//		System.out.println(isEqualDay(getFirstDateOfMonth(new Date())));
		System.out.println(isSameDay(addDay(new Date(), 0), new Date()));
	}

	/**
	 * 
	 * 作者:    马行空
	 * 创建时间: 2018年3月29日上午9:59:58
	 * 描述:    判断是否为同一天
	 * 备注: 
	 * @param date
	 * @return
	 */
	public static boolean isEqualDay(Date date) {
		
		long days = getDateDiff(date,new Date());
		
		return days== 0? true:false;
	}
	
	
	/**
	 * 得到当前周起始时间
	 * @param date
	 * @return
	 */
	public static Date getWeekStart(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.get(Calendar.WEEK_OF_YEAR);
		int firstDay = calendar.getFirstDayOfWeek();
		calendar.set(Calendar.DAY_OF_WEEK, firstDay);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 得到当前周截止时间
	 * @param date
	 * @return
	 */
	public static Date getWeekEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.get(Calendar.WEEK_OF_YEAR);
		int firstDay = calendar.getFirstDayOfWeek();
		calendar.set(Calendar.DAY_OF_WEEK, 8 - firstDay);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 得到当月起始时间
	 * @param date
	 * @return
	 */
	public static Date getMonthStart(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 得到当月的截止时间
	 * @param date
	 * @return
	 */
	public static Date getMonthEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.MONTH, 1);
		calendar.add(Calendar.MILLISECOND, -1);
		return calendar.getTime();
	}

	/**
	 * 得到当前年起始时间
	 * @param date
	 * @return
	 */
	public static Date getYearStart(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
		calendar.set(Calendar.MONTH, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 得到当前年的截止时间
	 * @param date
	 * @return
	 */
	public static Date getYearEnd(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
		calendar.set(Calendar.MONTH, 11);
		calendar.set(Calendar.DAY_OF_MONTH, 31);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 取得月第一天
	 * @param date
	 * @return
	 */
	public static Date getFirstDateOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}

	/**
	 * 取得月最后一天
	 * @param date
	 * @return
	 */
	public static Date getLastDateOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return c.getTime();
	}

	/**
	 * 取得季度第一天
	 * @param date
	 * @return
	 */
	public static Date getSeasonStart(Date date) {
		return getDayStart(getFirstDateOfMonth(getSeasonDate(date)[0]));
	}

	/**
	 * 取得季度最后一天
	 * @param date
	 * @return
	 */
	public static Date getSeasonEnd(Date date) {
		return getDayEnd(getLastDateOfMonth(getSeasonDate(date)[2]));
	}

	/**
	 * 取得季度月
	 * @param date
	 * @return
	 */
	public static Date[] getSeasonDate(Date date) {
		Date[] season = new Date[3];
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int nSeason = getSeason(date);
		if (nSeason == 1) {// 第一季度
			c.set(Calendar.MONTH, Calendar.JANUARY);
			season[0] = c.getTime();
			c.set(Calendar.MONTH, Calendar.FEBRUARY);
			season[1] = c.getTime();
			c.set(Calendar.MONTH, Calendar.MARCH);
			season[2] = c.getTime();
		} else if (nSeason == 2) {// 第二季度
			c.set(Calendar.MONTH, Calendar.APRIL);
			season[0] = c.getTime();
			c.set(Calendar.MONTH, Calendar.MAY);
			season[1] = c.getTime();
			c.set(Calendar.MONTH, Calendar.JUNE);
			season[2] = c.getTime();
		} else if (nSeason == 3) {// 第三季度
			c.set(Calendar.MONTH, Calendar.JULY);
			season[0] = c.getTime();
			c.set(Calendar.MONTH, Calendar.AUGUST);
			season[1] = c.getTime();
			c.set(Calendar.MONTH, Calendar.SEPTEMBER);
			season[2] = c.getTime();
		} else if (nSeason == 4) {// 第四季度
			c.set(Calendar.MONTH, Calendar.OCTOBER);
			season[0] = c.getTime();
			c.set(Calendar.MONTH, Calendar.NOVEMBER);
			season[1] = c.getTime();
			c.set(Calendar.MONTH, Calendar.DECEMBER);
			season[2] = c.getTime();
		}
		return season;
	}

	/**
	 * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
	 * @param date
	 * @return
	 */
	public static int getSeason(Date date) {
		int season = 0;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int month = c.get(Calendar.MONTH);
		switch (month) {
			case Calendar.JANUARY:
			case Calendar.FEBRUARY:
			case Calendar.MARCH:
				season = 1;
				break;
			case Calendar.APRIL:
			case Calendar.MAY:
			case Calendar.JUNE:
				season = 2;
				break;
			case Calendar.JULY:
			case Calendar.AUGUST:
			case Calendar.SEPTEMBER:
				season = 3;
				break;
			case Calendar.OCTOBER:
			case Calendar.NOVEMBER:
			case Calendar.DECEMBER:
				season = 4;
				break;
			default:
				break;
		}
		return season;
	}

	/**
	 * 日期最大时间
	 * @param dt
	 * @return
	 */
	public static Date getMaxTime(Date dt) {
		Date dt1 = null;
		Calendar ca = Calendar.getInstance();
		ca.setTime(dt);
		ca.add(Calendar.DAY_OF_MONTH, 1);
		dt1 = ca.getTime();
		dt1 = DateUtils.getMinTime(dt1);
		ca.setTime(dt1);
		ca.add(Calendar.SECOND, -1);
		dt1 = ca.getTime();
		return dt1;
	}

	/**
	 * 日期最小时间
	 * @param dt
	 * @return
	 */
	public static Date getMinTime(Date dt) {
		Date dt1 = null;
		dt1 = getDateByStr(dateToStr(dt, "yyyy-MM-dd"));
		return dt1;
	}

	/**
	 * 本月的最后一天
	 * @param date
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Date getLastDayOfMonth(Date date) {
		Calendar cDay1 = Calendar.getInstance();
		cDay1.setTime(date);
		int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
		Date lastDate = cDay1.getTime();
		lastDate.setDate(lastDay);
		return lastDate;
	}

	/**
	 * 本月的第一天
	 * @param date
	 * @return
	 */
	public static Date getFirstDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
		return calendar.getTime();
	}

	/**
	 * 本月第几天
	 * @return
	 */
	public static int getDayOfMonth(){
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取指定日期的月份天数
	 * @param date
	 * @return
	 */
	public static int getMonthDays(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		return days;
	}

	/**
	 * 上月第一天
	 * @return
	 */
	public static Date getPreviousMonthFirstDay() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, -1);// 减一个月，变为上个月的1号
		return getMinTime(lastDate.getTime());
	}

	/**
	 * 上月最后一天
	 * @return
	 */
	public static Date getPreviousMonthLastDay() {
		Calendar lastDate = Calendar.getInstance();
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.DATE, -1);//减一天，变为上个月的最后一天
		return getMinTime(lastDate.getTime());
	}

	/**
	 * 下月第一天
	 * @return
	 */
	public static Date getNextMonthFirstDay(Date date) {
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTime(getFirstDayOfMonth(date));// 设为当前月的1号
		lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下个月的1号
		return getMinTime(lastDate.getTime());
	}

	/**
	 * 下月最后一天
	 * @return
	 */
	public static Date getNextMonthLastDay(Date date) {
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTime(getFirstDayOfMonth(date));// 设为当前月的1号
		lastDate.add(Calendar.MONTH, 2);//加2月
		lastDate.add(Calendar.DATE, -1);//减1天
		return getMinTime(lastDate.getTime());
	}

	/**
	 * 上个月月份
	 * @return
	 */
	public static int getLastMoth(){
		//取得系统当前时间
		Calendar cal = Calendar.getInstance();
		//日期减一,取得上月最后一天时间对象
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.get(Calendar.MONTH);
	}

	/**
	 * 上个月日期格式串：YYYY-MM
	 * @return
	 */
	public static String getLastMothStr(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);// 设为当前月的1号
		cal.add(Calendar.DATE, -1);//减1天变为上个月
		return sdfDateMonth.format(cal.getTime());
	}

	/**
	 * 根据传入的日期减去1个月得到上个月
	 * 日期格式串：YYYY-MM
	 * @param date
	 * @return
	 */
	public static String getLastMonthStrFromDate(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DATE, 1);// 设为当前月的1号
		cal.add(Calendar.DATE, -1);//减1天变为上个月
		return sdfDateMonth.format(cal.getTime());
	}

	/**
	 * 取得两个日期之间的日数
	 * @return t1到t2间的日数，如果t2 在 t1之后，返回正数，否则返回负数
	 */
	public static long daysBetween(Timestamp t1, Timestamp t2) {
		return (t2.getTime() - t1.getTime()) / DAY_MILLI;
	}

	/**
	 * 利用缺省的Date格式(YYYY/MM/DD)转换String到java.sql.Timestamp
	 * @param sDate
	 * @return
	 * @history
	 */
	public static Timestamp toSqlTimestamp(String sDate) {
		if (sDate == null) {
			return null;
		}
		if (sDate.length() != DateUtils.DATE_FORMAT_DATEDAY.length()
				&&sDate.length() != DateUtils.DATE_FORMAT_DATETIME.length()) {
			return null;
		}
		return toSqlTimestamp(sDate,
				sDate.length() == DateUtils.DATE_FORMAT_DATEDAY.length()
				?DateUtils.DATE_FORMAT_DATEDAY
				:DateUtils.DATE_FORMAT_DATETIME);
	}

	/**
	 * 利用缺省的Date格式(YYYY/MM/DD hh:mm:ss)转化String到java.sql.Timestamp
	 * @param sDate
	 * @param sFmt
	 * @return
	 * @history
	 */
	public static Timestamp toSqlTimestamp(String sDate, String sFmt) {
		String temp = null;
		if (sDate == null || sFmt == null) {
			return null;
		}
		if (sDate.length() != sFmt.length()) {
			return null;
		}
		if (sFmt.equals(DateUtils.DATE_FORMAT_DATETIME)) {
			temp = sDate.replace('/', '-');
			temp = temp + ".000000000";
		} else if (sFmt.equals(DateUtils.DATE_FORMAT_DATEDAY)) {
			temp = sDate.replace('/', '-');
			temp = temp + " 00:00:00.000000000";
		} else {
			return null;
		}
		return Timestamp.valueOf(temp);
	}

	/**
	 * 根据单位字段比较两个日期
	 * @param date
	 * @param otherDate
	 * @param withUnit 单位字段，从Calendar field取值
	 * @return 等于返回0值, 大于返回大于0的值 小于返回小于0的值
	 */
	public static int compareDate(Date date, Date otherDate, int withUnit) {
		Calendar dateCal = Calendar.getInstance();
		dateCal.setTime(date);
		Calendar otherDateCal = Calendar.getInstance();
		otherDateCal.setTime(otherDate);
		switch (withUnit) {
			case Calendar.YEAR:
				dateCal.clear(Calendar.MONTH);
				otherDateCal.clear(Calendar.MONTH);
			case Calendar.MONTH:
				dateCal.set(Calendar.DATE, 1);
				otherDateCal.set(Calendar.DATE, 1);
			case Calendar.DATE:
				dateCal.set(Calendar.HOUR_OF_DAY, 0);
				otherDateCal.set(Calendar.HOUR_OF_DAY, 0);
			case Calendar.HOUR:
				dateCal.clear(Calendar.MINUTE);
				otherDateCal.clear(Calendar.MINUTE);
			case Calendar.MINUTE:
				dateCal.clear(Calendar.SECOND);
				otherDateCal.clear(Calendar.SECOND);
			case Calendar.SECOND:
				dateCal.clear(Calendar.MILLISECOND);
				otherDateCal.clear(Calendar.MILLISECOND);
			case Calendar.MILLISECOND:
				break;
			default:
				throw new IllegalArgumentException("withUnit 单位字段 " + withUnit + " 不合法！！");
		}
		return dateCal.compareTo(otherDateCal);
	}

	public static boolean isSameDay(Date date1, Date date2) {
        if(date1 != null && date2 != null) {
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date2);
            return isSameDay(cal1, cal2);
        } else {
            throw new IllegalArgumentException("The date must not be null");
        }
    }
	
	
	/**
	 * 比较两个日期相差多少天
	 * @param fDate 小的日期
	 * @param oDate 大的日期
	 * @return
	 */
	public static int daysOfTwo(Date fDate, Date oDate) {
//		Calendar aCalendar = Calendar.getInstance();
//		aCalendar.setTime(fDate);
//		int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
//		aCalendar.setTime(oDate);
//		int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
//		return day2 - day1;
		LocalDate day1 = getLocalDateFromDate(fDate);
		LocalDate day2 = getLocalDateFromDate(oDate);
		return (int) (day2.toEpochDay() - day1.toEpochDay());
	}

	/**时间差天
	 *DiffOfDay: bigDate - lessDate
	 * @param bigDate
	 * @param lessDate
	 * @return days
	 * @author jf
	 */
	public static int DiffOfDay(Date bigDate, Date lessDate) {
		LocalDate dayBig = getLocalDateFromDate(bigDate);
		LocalDate dayLess = getLocalDateFromDate(lessDate);
		return (int) (dayBig.toEpochDay() - dayLess.toEpochDay());
	}


	/**
	 * Date 转换为 LocalDate
	 * @param date
	 * @return
	 */
	private static LocalDate getLocalDateFromDate(Date date) {
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		return instant.atZone(zoneId).toLocalDate();
	}

	/**
	 * 根据单位字段比较两个时间
	 * @param date 时间1
	 * @param otherDate 时间2
	 * @param withUnit 单位字段，从Calendar field取值
	 * @return 等于返回0值, 大于返回大于0的值 小于返回小于0的值
	 */
	public static int compareTime(Date date, Date otherDate, int withUnit) {
		Calendar dateCal = Calendar.getInstance();
		dateCal.setTime(date);
		Calendar otherDateCal = Calendar.getInstance();
		otherDateCal.setTime(otherDate);
		dateCal.clear(Calendar.YEAR);
		dateCal.clear(Calendar.MONTH);
		dateCal.set(Calendar.DATE, 1);
		otherDateCal.clear(Calendar.YEAR);
		otherDateCal.clear(Calendar.MONTH);
		otherDateCal.set(Calendar.DATE, 1);
		switch (withUnit) {
			case Calendar.HOUR:
				dateCal.clear(Calendar.MINUTE);
				otherDateCal.clear(Calendar.MINUTE);
			case Calendar.MINUTE:
				dateCal.clear(Calendar.SECOND);
				otherDateCal.clear(Calendar.SECOND);
			case Calendar.SECOND:
				dateCal.clear(Calendar.MILLISECOND);
				otherDateCal.clear(Calendar.MILLISECOND);
			case Calendar.MILLISECOND:
				break;
			default:
				throw new IllegalArgumentException("withUnit 单位字段 " + withUnit + " 不合法！！");
		}
		return dateCal.compareTo(otherDateCal);
	}


	/**
	 * 判断时间是否在制定的时间段之类
	 * @param date 需要判断的时间
	 * @param start 时间段的起始时间
	 * @param end 时间段的截止时间
	 * @param interModel 区间的模式
	 *  <pre>
	 * 		取值：
	 * 			LEFT_OPEN_RIGHT_OPEN
	 * 			LEFT_CLOSE_RIGHT_OPEN
	 * 			LEFT_OPEN_RIGHT_CLOSE
	 * 			LEFT_CLOSE_RIGHT_CLOSE
	 * </pre>
	 * @param compModel 比较的模式
	 * <pre>
	 * 		取值：
	 * 			COMP_MODEL_DATE		只比较日期，不比较时间
	 * 			COMP_MODEL_TIME		只比较时间，不比较日期
	 * 			COMP_MODEL_DATETIME 比较日期，也比较时间
	 * </pre>
	 * @return
	 */
	public static boolean isBetween(Date date, Date start, Date end, int interModel, int compModel) {
		if (date == null || start == null || end == null) {
			throw new IllegalArgumentException("日期不能为空");
		}
		SimpleDateFormat format = null;
		switch (compModel) {
		case COMP_MODEL_DATE: {
			format = new SimpleDateFormat("yyyyMMdd");
			break;
		}
		case COMP_MODEL_TIME: {
			format = new SimpleDateFormat("HHmmss");
			break;
		}
		case COMP_MODEL_DATETIME: {
			format = new SimpleDateFormat("yyyyMMddHHmmss");
			break;
		}
		default: {
			throw new IllegalArgumentException(String.format("日期的比较模式[%d]有误", compModel));
		}
		}
		long dateNumber = Long.parseLong(format.format(date));
		long startNumber = Long.parseLong(format.format(start));
		long endNumber = Long.parseLong(format.format(end));
		switch (interModel) {
		case LEFT_OPEN_RIGHT_OPEN: {
            return !(dateNumber <= startNumber || dateNumber >= endNumber);
		}
		case LEFT_CLOSE_RIGHT_OPEN: {
            return !(dateNumber < startNumber || dateNumber >= endNumber);
		}
		case LEFT_OPEN_RIGHT_CLOSE: {
            return !(dateNumber <= startNumber || dateNumber > endNumber);
		}
		case LEFT_CLOSE_RIGHT_CLOSE: {
            return !(dateNumber < startNumber || dateNumber > endNumber);
		}
		default: {
			throw new IllegalArgumentException(String.format("日期的区间模式[%d]有误", interModel));
		}
		}
	}

	/**
	 * 判断输入日期是一个星期中的第几天(星期天为一个星期第一天)
	 * @param date
	 * @return
	 */
	public static int getWeekIndex(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 判断开始时间和结束时间，是否超出了当前时间的一定的间隔数限制 如：开始时间和结束时间，不能超出距离当前时间90天
	 * @param startDate 开始时间
	 * @param endDate 结束时间按
	 * @param interval 间隔数
	 * @param dateUnit 单位(如：月，日),参照Calendar的时间单位
	 * @return
	 */
	public static boolean isOverIntervalLimit(Date startDate, Date endDate, int interval, int dateUnit) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(dateUnit, interval * (-1));
		Date curDate = getDayStart(cal.getTime());
        return getDayStart(startDate).compareTo(curDate) < 0 || getDayStart(endDate).compareTo(curDate) < 0;
    }

	/**
	 * 判断开始时间和结束时间，是否超出了当前时间的一定的间隔数限制, 时间单位默认为天数 如：开始时间和结束时间，不能超出距离当前时间90天
	 * @param startDate 开始时间
	 * @param endDate 结束时间按
	 * @param interval 间隔数
	 * @return
	 */
	public static boolean isOverIntervalLimit(Date startDate, Date endDate, int interval) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, interval * (-1));
		Date curDate = getDayStart(cal.getTime());
        return getDayStart(startDate).compareTo(curDate) < 0 || getDayStart(endDate).compareTo(curDate) < 0;
    }

	/**
	 * 判断开始时间和结束时间，是否超出了当前时间的一定的间隔数限制, 时间单位默认为天数 如：开始时间和结束时间，不能超出距离当前时间90天
	 * @param startDateStr 开始时间
	 * @param endDateStr 结束时间按
	 * @param interval 间隔数
	 * @return
	 */
	public static boolean isOverIntervalLimit(String startDateStr, String endDateStr, int interval) {
		Date startDate = strToDate(startDateStr, DATE_FORMAT_DATEDAY);
		Date endDate = strToDate(endDateStr, DATE_FORMAT_DATEDAY);
		return isOverIntervalLimit(startDate, endDate, interval);
	}

	/**
	 * 传入时间字符串及时间格式，返回对应的Date对象
	 * @param src 时间字符串
	 * @param pattern 时间格式
	 * @return Date
	 */
	public static Date getDateFromString(String src, String pattern) {
		SimpleDateFormat f = new SimpleDateFormat(pattern);
		try {
			return f.parse(src);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 判断当前时间是否在一定的时间范围内
	 * @param startTime
	 * @param endTime
	 * @return boolean
	 */
	public static boolean isInBetweenTimes(String startTime, String endTime) {
		Date nowTime = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		String time = sdf.format(nowTime);
        return time.compareTo(startTime) >= 0 && time.compareTo(endTime) <= 0;
	}

	/**
	 * 两个日期之间相差的天数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long getDateDiff(String startDate, String endDate) {
		long diff = 0;
		try {
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
			Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);

			diff = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000) > 0 ? (date1.getTime() - date2.getTime())
					/ (24 * 60 * 60 * 1000)
					: (date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000);
		} catch (ParseException e) {
		}
		return diff;
	}

	/**
	 * 两个日期之间相差的天数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static long getDateDiff(Date startDate, Date endDate) {
		long diff = (startDate.getTime() - endDate.getTime()) / (24 * 60 * 60 * 1000) > 0 ? (startDate.getTime() - endDate
				.getTime()) / (24 * 60 * 60 * 1000) : (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
		return diff;
	}

	/**
	 * 取两个日期之间相差的年数
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getYearDiff(Date startDate, Date endDate) {
		int diff = 0;
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		if (startDate != null) {
			end.setTime(endDate);
			start.setTime(startDate);
			diff = end.get(Calendar.YEAR) - start.get(Calendar.YEAR);
			int endDayOfYear = end.get(Calendar.DAY_OF_YEAR);
			int startDayOfYear = start.get(Calendar.DAY_OF_YEAR);
			if (endDayOfYear < startDayOfYear) {
				diff -= 1; // 比较完年之后再比较天，如果天小了，年数-1
			}else{
				//diff = endDayOfYear - startDayOfYear;// 如果天也大，就是正常的
			}
		}
		return diff;
	}

	/**
	 * 判断两个时间是不是在一个周中
	 * @return
	 */
	public static boolean isSameWeekWithToday(Date date) {
		if (date == null) {
			return false;
		}
		//先把Date类型的对象转换Calendar类型的对象
		Calendar todayCal = Calendar.getInstance();
		Calendar dateCal = Calendar.getInstance();
		todayCal.setTime(new Date());
		dateCal.setTime(date);
		int subYear = todayCal.get(Calendar.YEAR) - dateCal.get(Calendar.YEAR);
		//subYear==0,说明是同一年
		if (subYear == 0) {
			if (todayCal.get(Calendar.WEEK_OF_YEAR) == dateCal.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (subYear == 1 && dateCal.get(Calendar.MONTH) == 11 && todayCal.get(Calendar.MONTH) == 0) {
			if (todayCal.get(Calendar.WEEK_OF_YEAR) == dateCal.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (subYear == -1 && todayCal.get(Calendar.MONTH) == 11 && dateCal.get(Calendar.MONTH) == 0) {
			if (todayCal.get(Calendar.WEEK_OF_YEAR) == dateCal.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		return false;
	}

	/**
	 * MethodName: calcTimeDifferenceStr
	 * description: 计算时间差
	 * @author yangyonghao
	 * @param time1 减数
	 * @param time2 被减数
	 * @return “刚刚”、“1~59分钟前”、“1~23小时前”、“1~30天前”、“1~11月前”、“N年前”
	 */
	public static String calcTimeDifferenceStr(Long time1, Long time2) {
		Long timeDifference = (time1 - time2) / 1000;
		if(timeDifference < 60){
			return "刚刚";
		}else if(timeDifference >= 60 && timeDifference < 60 * 60){
			return timeDifference / 60 + "分钟前";
		}else if(timeDifference >= 60 * 60 &&  timeDifference < 60 * 60 * 24){
			return timeDifference / (60 * 60) + "小时前";
		}else if(timeDifference >= 60 * 60 * 24 &&  timeDifference < 60 * 60 * 24 * 31){
			return timeDifference / (60 * 60 * 24) + "天前";
		}else if(timeDifference >= 60 * 60 * 24 * 31 &&  timeDifference < 60 * 60 * 24 * 31 * 365){
			return timeDifference / (60 * 60 * 24 * 31) + "个月前";
		}else{
			return timeDifference / (60 * 60 * 24 * 31 * 365) + "年前";
		}
	}

	public static String formatStrDate(String yearstr, String monthstr,String daystr){
		if(StrUtils.notEmpty(yearstr) && StrUtils.notEmpty(monthstr) && StrUtils.notEmpty(daystr)){
			return yearstr+"-"+monthstr+"-"+daystr;
		}
		if(StrUtils.notEmpty(yearstr) && StrUtils.notEmpty(monthstr) && StrUtils.isEmpty(daystr)){
			return yearstr+"-"+monthstr;
		}
		if(StrUtils.notEmpty(yearstr) && StrUtils.isEmpty(monthstr) && StrUtils.isEmpty(daystr)){
			return yearstr;
		}
		return "";
	}

	
	/**
	 * 
	 * 作者:    马行空
	 * 创建时间: 2017年12月28日下午5:04:17
	 * 描述:    信用卡有效期格式转换
	 * 备注: 
	 * @param exp_date
	 * @return
	 */
	public static String changeCreditCardValidity(String exp_date) {
		if(StringUtils.isEmpty(exp_date)) {
			return "";
		}
		try {
			new  SimpleDateFormat(DATE_FORMAT_CREDIT).parse(exp_date);
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
		
		String new_exp_date=exp_date.substring(exp_date.length()-2, exp_date.length())+exp_date.substring(0, exp_date.length()-2);
		
		return new_exp_date;
	}

	public static String changeCreditCardValiditys(String exp_date) {
		if(StringUtils.isEmpty(exp_date)) {
			return "";
		}
		try {
			new  SimpleDateFormat(DATE_FORMAT_CREDIT).parse(exp_date);
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
		String new_exp_date=exp_date.substring(0, exp_date.length()-2)+"-"+exp_date.substring(exp_date.length()-2, exp_date.length());
		
		return new_exp_date;
	}
	
	/**
	 * 获取开始时间和结束时间之间的日期列表，格式为yyyy-MM-dd
	 * @param startDate	开始时间
	 * @param endDate	结束时间
	 * @return	列表结果
	 */
	public static List<String> getDateStrList(LocalDate startDate, LocalDate endDate){
		if(startDate == null || endDate == null){
			return null;
		}
		List<String> dateList = new ArrayList<>();
		if(startDate.isAfter(endDate)){
			LocalDate tempDate = startDate;
			startDate = endDate;
			endDate = tempDate;
		}
		//开始时间早于或等于结束时间
		while (!startDate.isAfter(endDate)){
			dateList.add(startDate.toString());
			startDate = startDate.plusDays(1);
		}
		return dateList;
	}

	/**
	 * 字符串转换为LocalDateTime,格式为yyyy-MM-dd HH:mm:ss
	 * @param str 时间字符串，格式为yyyy-MM-dd HH:mm:ss
	 * @return 转换后的LocalDateTime，null-字符串为空或格式不正确
	 */
	public static LocalDateTime strToLocalDateTime(String str){
		if(StringUtils.isEmpty(str)){
			return null;
		}
		try{
			return LocalDateTime.parse(str, DateTimeFormatter.ofPattern(DATE_FORMAT_DATETIME));
		}catch (Exception e){
			return null;
		}
	}
}
