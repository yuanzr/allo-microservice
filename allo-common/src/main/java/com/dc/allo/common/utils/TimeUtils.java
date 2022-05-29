package com.dc.allo.common.utils;

import org.apache.commons.lang3.StringUtils;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zhangzhenjun on 2020/4/8.
 */
public class TimeUtils {

    public static String YEAR2MONTH4SQL = "yyyy_MM";
    public static String PATTERN_FORMAT_DATE = "yyyy-MM-dd";
    public static String PATTERN_FORMAT_TIME = "yyyy-MM-dd HH:mm:ss";
    public static String YEAR2SECOND12 = "yyyy-MM-dd hh:mm:ss";
    public static String YEAR2SECOND24 = "yyyy-MM-dd HH:mm:ss";
    public static String YEAR2DAY_NOLINE = "yyyyMMdd";
    public static String YEAR2SECOND24_NOLINE = "yyyyMMddHHmmss";
    public static String YEAR2MILLION_NOLINE = "yyyyMMddHHmmssSSS";
    public static String YEAR2SECOND24ONLYHOUR = "yyyy-MM-dd HH:00:00";
    public static String YEAR2MINUTE24 = "yyyy-MM-dd HH:mm:00";
    public static String YEAR2HOUR_NOLINE = "yyyyMMddHH";
    public static String HHMM = "HH:mm";
    public static String HHMM_NOLINE = "HHmm";
    public static String HHMMSS_NOLINE = "hhmmss";
    public static String MMdd = "MMdd";

    public static Date fromStrDate(String str) throws ParseException {
        return fromStr(str, PATTERN_FORMAT_DATE);
    }
    public static Date fromStrTime(String str) throws ParseException {
        return fromStr(str, PATTERN_FORMAT_TIME);
    }
    public static Date fromStr(String str, String pattern) throws ParseException {
        DateFormat format = new SimpleDateFormat(pattern);
        return format.parse(str);
    }

    public static String toStr(Date date) {
        return toStr(date, PATTERN_FORMAT_TIME);
    }
    public static String toStr(Date date, String pattern) {
        DateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    public static int getCurHour(){
        Calendar today = Calendar.getInstance();
        return today.get(Calendar.HOUR_OF_DAY);
    }

    public static int getPreHour(){
        Calendar now = Calendar.getInstance();
        //获取上一小时
        now.set(Calendar.HOUR_OF_DAY, now.get(Calendar.HOUR_OF_DAY) - 1);
        int preHour = now.get(Calendar.HOUR_OF_DAY);
        return preHour;
    }


    /** 时间偏移(天数)，负数:往前，正数:往后 */
    public static Date offsetDay(Date date, int offset) {
        return offset(date, Calendar.DAY_OF_YEAR, offset);
    }
    /** 时间偏移(小时)，负数:往前，正数:往后 */
    public static Date offsetHour(Date date, int offset) {
        return offset(date, Calendar.HOUR_OF_DAY, offset);
    }
    /** 时间偏移(分钟)，负数:往前，正数:往后 */
    public static Date offsetMinute(Date date, int offset) {
        return offset(date, Calendar.MINUTE, offset);
    }
    /** 时间偏移(秒)，负数:往前，正数:往后 */
    public static Date offsetSecond(Date date, int offset) {
        return offset(date, Calendar.SECOND, offset);
    }
    /**
     * 时间偏移
     * @param date 参数date,如果为空，默认为当前
     * @param field 如Calendar.HOUR_OF_DAY
     * @param offset 偏移量，正数:往后，负数:往前
     * @return 偏移后的date
     */
    public static Date offset(Date date, int field, int offset) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(field, offset);
        return calendar.getTime();
    }

    //获取当天（按当前传入的时区）00:00:00所对应时刻的long型值
    public static long getStartTimeOfDay(long now, String timeZone) {
        String tz = StringUtils.isEmpty(timeZone) ? "GMT+8" : timeZone;
        TimeZone curTimeZone = TimeZone.getTimeZone(tz);
        Calendar calendar = Calendar.getInstance(curTimeZone);
        calendar.setTimeInMillis(now);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTimeInMillis();
    }

    public static int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static int getDayOfMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getDayOfWeekInMonth() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);
    }

    public static int getDayOfYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    public static int getCurMonth(){
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 两个日期相差多少天
     * @param one
     * @param two
     * @return
     */
    public static long daysBetween(Date one, Date two) {
        long difference = (one.getTime() - two.getTime()) / 86400000;
        return Math.abs(difference);
    }

    /**
     * 是否两个时间之间
     * @param sDate
     * @param eDate
     * @return
     */
    public static boolean isBetween(Date sDate, Date eDate){
        long now = new Date().getTime();
        long stime = sDate.getTime();
        long etime = eDate.getTime();
        if(now>=stime && now<=etime){
            return true;
        }
        return false;
    }

    /**
     *
     * @param startStr  "06:00"
     * @param endStr    "10:00"
     * @param isInterDay 是否跨天
     * @throws ParseException
     */
    public static boolean isBelong(String startStr,String endStr,boolean isInterDay) throws ParseException{

        SimpleDateFormat df = new SimpleDateFormat("HH:mm");//设置日期格式
        Date now =null;
        Date beginTime = null;
        Date endTime = null;

        now = df.parse(df.format(new Date()));
        beginTime = df.parse(startStr);
        endTime = df.parse(endStr);


        Boolean flag = belongCalendar(now, beginTime, endTime,isInterDay);
        return flag;
    }

    /**
     * 判断时间是否在时间段内
     * @param nowTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime,boolean isInterDay) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        if(isInterDay){
            end.add(Calendar.DATE, 1);
        }
        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 向前取整，30分钟
     * @param time  "01:25"
     * @return
     */
    public static String getPreRoundTime(String time){
        String hour="00";//小时
        String minutes="00";//分钟
        String outTime="0000";
        StringTokenizer st = new StringTokenizer(time, ":");
        List<String> inTime = new ArrayList<String>();
        while (st.hasMoreElements()) {
            inTime.add(st.nextToken());
        }
        hour=inTime.get(0).toString();
        minutes=inTime.get(1).toString();
        if(Integer.parseInt(minutes)>30){
            outTime=hour+"30";
        }else{
            outTime=hour+"00";
        }
        return outTime;
    }

    public static void main(String[] args) throws ParseException {
        Date date = TimeUtils.fromStrDate("2020-04-12");
        Date date1 = TimeUtils.fromStrDate("2020-04-13");
        System.out.println(TimeUtils.isBetween(date,date1));
    }
}
