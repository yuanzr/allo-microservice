package com.dc.allo.common.utils;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;


public class DateTimeUtil {

    private static final Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);

    public static final String DEFAULT_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
    public static final String DEFAULT_DATE_PATTERN_ = "yyyyMMdd";
    public static final String DEFAULT_DATE_PATTERN__ = "yyyyMMddHHmmss";
    public static final String DEFAULT_TIME_PATTERN = "HH:mm:ss";
    public static final String DEFAULT_DATE_MINUTE_PATTERN = "yyyy-MM-dd HH:mm";
    public static final String SIMPLE_MONTH_DATE_PATTERN = "M月d日";
    public static final String DEFAULT_DATE_HOUR_PATTERN = "yyyy-MM-dd HH";
    public static final String SIMPLE_MONTH_DATE_PATTERN1 = "yyyy年MM月dd日";
    public static final String DATE_HOUR_PATTERN = "yyyyMMddHH";
    public static final String HOUR_BEFORE_DAY_ALTER = "HH:mm dd/MM";
    public static final String SIMPLE_MONTH_DAY_PATTERN = "MM-dd";
    public static final String SIMPLE_DAY_PATTERN = "d";

    //一小时的秒数
    private static final int HOUR_SECOND = 60 * 60;
    //一分钟的秒数
    private static final int MINUTE_SECOND = 60;

    private static final ZoneId zoneId = ZoneId.systemDefault();

    public static Date getLastHour(Date date, int hour) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.set(Calendar.HOUR, cl.get(Calendar.HOUR) - hour);
        return cl.getTime();
    }

    public static Date getLastDay(Date date, int day) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.DATE, -day);
        return cl.getTime();
    }

    /**
     * 获取某一周的周一时间
     * @param n n为推迟的周数，-1上周，0本周，1下周，2下下周，依次类推
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Date getMonday(Integer n,Integer hour,Integer minute,Integer second){
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, n * 7);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        return cal.getTime();
    }


    /**
     * 获取东三区(GMT)某一周的周一时间
     * @param n n为推迟的周数，-1上周，0本周，1下周，2下下周，依次类推
     * @return
     */
    public static Date getMondayGMT3(Integer n) {
        Calendar cal = Calendar.getInstance();
        //当前服务器时间为东八区北京时间
        //获得东三区的时间
        Date dateGMT3 = getLastHour(new Date(), 5);
        //由于Calendar的计算问题,会往前推一天导致周一计算错误
        Date currentTime = addDays(dateGMT3, -1);
        cal.setTime(currentTime);
        cal.add(Calendar.DATE, n * 7);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    /**
     * 获取东三区(GMT)某一周的周一时间
     * @param n n为推迟的周数，-1上周，0本周，1下周，2下下周，依次类推
     * @return
     */
    public static Date getMondayGMT3(Date date,Integer n) {
        Calendar cal = Calendar.getInstance();
        //当前服务器时间为东八区北京时间
        Date dateGMT3 = getLastHour(date, 5);
        //由于Calendar的计算问题,会往前推一天导致周一计算错误
        Date currentTime = addDays(dateGMT3, -1);
        cal.setTime(currentTime);
        cal.add(Calendar.DATE, n * 7);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    /**
     * 获取东三区上一周周一的时间
     * @param date
     * @param n
     * @return
     */
    public Date getLastMondayGMT3(Date date,Integer n){
        Date monday = getMondayGMT3(date,n);
        return  monday;
    }


    public static Date getNextMinute(Date date, int minute) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.MINUTE, minute);
        return cl.getTime();
    }
    public static Date getNextSecond(Date date, int second) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.SECOND, second);
        return cl.getTime();
    }

    public static Date getNextDay(Date date, int day) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.DATE, day);
        cl.set(Calendar.HOUR_OF_DAY, cl.getActualMinimum(Calendar.HOUR_OF_DAY));
        cl.set(Calendar.MINUTE, cl.getActualMinimum(Calendar.MINUTE));
        cl.set(Calendar.SECOND, cl.getActualMinimum(Calendar.SECOND));
        cl.set(Calendar.MILLISECOND, 0);
        return cl.getTime();
    }

    public static Date getNextDay(Date date, int day,Integer hour,Integer minute,Integer second) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.DATE, day);
        cl.set(Calendar.HOUR_OF_DAY, hour);
        cl.set(Calendar.MINUTE, minute);
        cl.set(Calendar.SECOND, second);
        cl.set(Calendar.MILLISECOND, 0);
        return cl.getTime();
    }

    public static Date getNextDay2(Date date, int day) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.DATE, day);
        cl.set(Calendar.HOUR_OF_DAY, 23);
        cl.set(Calendar.MINUTE, 59);
        cl.set(Calendar.SECOND, 57);
        cl.set(Calendar.MILLISECOND, 0);
        return cl.getTime();
    }

    public static Date getNextDay3(Date date, int day) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.DATE, day);
        return cl.getTime();
    }



    public static Date getNextHour(Date date, int hour) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.set(Calendar.HOUR, cl.get(Calendar.HOUR) + hour);
        return cl.getTime();
    }

    public static Date getDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(date);
        return convertStrToDate(dateStr, "yyyy-MM-dd");
    }

    public static int compareDay(Date date1, Date date2) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date1);
        int day1 = cl.get(Calendar.DAY_OF_YEAR);
        int year1 = cl.get(Calendar.YEAR);
        cl.setTime(date2);
        int day2 = cl.get(Calendar.DAY_OF_YEAR);
        int year2 = cl.get(Calendar.YEAR);
        if (year1 == year2) {
            return day1 - day2;
        } else {
            date1 = getDate(date1);
            date2 = getDate(date2);
            return (int) ((date1.getTime() - date2.getTime()) / (1000 * 3600 * 24));
        }

    }

    /**
     * 获取当天的字符串
     *
     * @return
     */
    public static String getTodayStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    public static String getTodayStr2() {
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
        return sdf.format(new Date());
    }

    public static String getCurrentTime() {
        return getCurrentTime(DEFAULT_TIME_PATTERN);
    }

    /**
     * 获取当前时间的字符串
     *
     * @return
     */
    public static String getCurrentDateTime() {
        return getCurrentTime(DEFAULT_DATETIME_PATTERN);
    }

    public static String getCurrentDate() {
        return getCurrentTime(DEFAULT_DATE_PATTERN);
    }

    /**
     * 获取当前时间的字符串
     *
     * @param format 字符串格式，如：yy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurrentTime(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return sdf.format(timestamp);
    }

    /**
     * 获取当前的月份
     *
     * @return
     */
    public static String getCurrentMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(new Date());
    }

    /**
     * 比较两个时间，如果返回大于0，time1大于time2,如果返回-1，time1小于time2，返回0则相等
     *
     * @param time1
     * @param time2
     * @return
     * @throws ParseException
     */
    public static int compareTime(String time1, String time2) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATETIME_PATTERN);
        Date date1 = sdf.parse(time1);
        Date date2 = sdf.parse(time2);
        long result = date1.getTime() - date2.getTime();
        if (result > 0) {
            return 1;
        } else if (result == 0) {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * 获取之前的某一天零时零分零秒
     */
    public static Date getBeforeDay(Date date,int day){
        Calendar cl = Calendar.getInstance();
        cl.setTime(new Date());
        cl.add(Calendar.DATE, day);
        cl.set(Calendar.HOUR_OF_DAY,0);
        cl.set(Calendar.MINUTE,0);
        cl.set(Calendar.SECOND,0);
        return cl.getTime();
    }

    public static int compareTime(Date date1, Date date2) {
        long result = date1.getTime() - date2.getTime();
        if (result > 0) {
            return 1;
        } else if (result == 0) {
            return 0;
        } else {
            return -1;
        }
    }

    public static Date convertStrToDate(String dateStr) {
        if (!StringUtils.isEmpty(dateStr) && !StringUtils.isEmpty(DEFAULT_DATETIME_PATTERN)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATETIME_PATTERN);
                return sdf.parse(dateStr);
            } catch (Exception e) {
                logger.warn("convertDate fail, date is " + dateStr, e);
            }
        }
        return null;
    }

    /**
     * 转换字符串成日期对象
     *
     * @param dateStr 日期字符串
     * @param format  格式，如：yy-MM-dd HH:mm:ss
     * @return
     */
    public static Date convertStrToDate(String dateStr, String format) {
        if (!StringUtils.isBlank(dateStr) && !StringUtils.isBlank(format)) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return sdf.parse(dateStr);
            } catch (Exception e) {
                logger.warn("convertDate fail, date is " + dateStr, e);
            }
        }
        return null;
    }

    /**
     * 把日期转换成另一种格式
     *
     * @param date 日期
     * @return
     */
    public static String convertDate(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATETIME_PATTERN);
            return sdf.format(date);
        } catch (Exception e) {
            logger.warn("convertDate fail, date is " + date, e);
        }
        return null;
    }

    /**
     * 把日期转换成另一种格式
     *
     * @param date   日期
     * @param format 转换日期格式
     * @return
     */
    public static String convertDate(Date date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception e) {
            logger.warn("convertDate fail, date is " + date, e);
        }
        return null;
    }

    /**
     * 日期往后推数
     *
     * @param date 日期
     * @return
     */
    public static Date addDays(Date date, int days) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days); //把日期往后增加一天,整数  往后推,负数往前移动
        Date dateRes = calendar.getTime();
        return dateRes;
    }

    /**
     * 日期往后推数
     *
     * @param date 日期
     * @return
     */
    public static Date addMinute(Date date, int min) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, min);
        Date dateRes = calendar.getTime();
        return dateRes;
    }

    /**
     * 把字符串日期转换成另一种格式
     *
     * @param dateStr     字符串日期
     * @param format      转换日期格式
     * @param otherFormat 转换日期格式
     * @return
     */
    public static String convertDate(String dateStr, String format, String otherFormat) {
        try {
            Date date = convertStrToDate(dateStr, format);
            SimpleDateFormat sdf = new SimpleDateFormat(otherFormat);
            return sdf.format(date);
        } catch (Exception e) {
            logger.warn("convertDate fail, date is " + dateStr, e);
        }
        return null;
    }

    /**
     * 把字符串日期转换成另一种格式
     *
     * @param dateStr 字符串日期
     * @param format  转换格式
     * @return
     */
    public static String convertDate(String dateStr, String format) {
        return convertDate(dateStr, DEFAULT_DATETIME_PATTERN, format);
    }

    /**
     * 获取当前日期周一
     *
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Date getCurrentMonday(int hour, int minute, int second) {

        return getMonday(Calendar.getInstance().getTime(), hour, minute, second);
    }

    public static Date getBeginTimeOfCurrentWeek() {

        return getMonday(Calendar.getInstance().getTime(), 0, 0, 0);
    }

    /**
     * 获取指定日期周一
     *
     * @param InputDate
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Date getMonday(Date InputDate, int hour, int minute, int second) {
        Calendar cDate = Calendar.getInstance();
        cDate.setFirstDayOfWeek(Calendar.MONDAY);
        cDate.setTime(InputDate);
        Calendar firstDate = Calendar.getInstance();
        firstDate.setFirstDayOfWeek(Calendar.MONDAY);
        firstDate.setTime(InputDate);
        if (cDate.get(Calendar.WEEK_OF_YEAR) == 1 && cDate.get(Calendar.MONTH) == 11) {
            firstDate.set(Calendar.YEAR, cDate.get(Calendar.YEAR) + 1);
        }
        int typeNum = cDate.get(Calendar.WEEK_OF_YEAR);

        firstDate.set(Calendar.WEEK_OF_YEAR, typeNum);
        firstDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        //所在周开始日期
        firstDate.set(Calendar.HOUR_OF_DAY, hour);
        firstDate.set(Calendar.MINUTE, minute);
        firstDate.set(Calendar.SECOND, second);
        return firstDate.getTime();
    }

    /**
     * 获取当前日期周日
     *
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Date getCurrentSunday(int hour, int minute, int second) {
        return getSunday(Calendar.getInstance().getTime(), hour, minute, second);
    }

    public static Date getEndTimeOfCurrentWeek() {
        return getSunday(Calendar.getInstance().getTime(), 23, 59, 59);
    }

    /**
     * 获取指定日期周日
     *
     * @param date
     * @param hour
     * @param minute
     * @param second
     * @return
     */
    public static Date getSunday(Date date, int hour, int minute, int second) {
        Calendar lastDate = Calendar.getInstance();
        lastDate.setTime(date);

        int day_of_week = lastDate.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        lastDate.add(Calendar.DATE, -day_of_week + 7);
        lastDate.set(Calendar.HOUR_OF_DAY, hour);
        lastDate.set(Calendar.MINUTE, minute);
        lastDate.set(Calendar.SECOND, second);
        return lastDate.getTime();
    }

    public static boolean checkIsToday(Date date) {
        if (date == null) {
            return false;
        }
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        if (fmt.format(date).toString().equals(fmt.format(new Date()).toString())) {//格式化为相同格式
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断某个时间是否在指定时间区间内
     *
     * @param curTime
     * @param beginTime
     * @param endTime
     * @return
     */
    public static boolean isBetweenDate(Date curTime, Date beginTime, Date endTime) {
        if (curTime == null || beginTime == null || endTime == null) {
            return false;
        }
        if (curTime.after(beginTime) && curTime.before(endTime)) {
            return true;
        } else {
            return false;
        }
    }

    public static Date getBeginTimeOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static Date getEndTimeOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);

        return calendar.getTime();
    }

    /**
     * 获取指定天数的秒数
     *
     * @param days
     * @return
     */
    public static Integer getSecondsOfDays(Integer days) {

        return 60 * 60 * 24 * days;
    }

    /**
     * 获取两个日期之间的日期
     *
     * @param start
     * @param end
     * @param wrap  true为包含两个区间值的Date，false为不包含
     * @return
     */
    public static List<Date> getBetweenDates(Date start, Date end, Boolean wrap) {
        List<Date> result = Lists.newArrayList();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        while (tempStart.before(tempEnd)) {
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        if (wrap) {
            result.add(tempEnd.getTime());
        }
        return result;
    }

    /**
     * 获取两个日期之间的日期
     *
     * @param start
     * @param end 结束时间会被设为0时0分0秒0毫秒
     * @param wrap  true为包含两个区间值的Date，false为不包含
     * @return
     */
    public static List<Date> getBetweenDatesMinEnd(Date start, Date end, Boolean wrap) {
        List<Date> result = new ArrayList<>();
        Calendar tempStart = Calendar.getInstance();
        tempStart.setTime(start);

        Calendar tempEnd = Calendar.getInstance();
        tempEnd.setTime(end);
        tempEnd.set(Calendar.HOUR_OF_DAY, 0);
        tempEnd.set(Calendar.MINUTE, 0);
        tempEnd.set(Calendar.SECOND, 0);
        tempEnd.set(Calendar.MILLISECOND, 0);
        while (tempStart.before(tempEnd)) {
            System.out.println(convertDate(tempStart.getTime()));
            result.add(tempStart.getTime());
            tempStart.add(Calendar.DAY_OF_YEAR, 1);
        }
        if (wrap) {
            result.add(tempEnd.getTime());
        }
        return result;
    }

    public static Date getBeginTimeOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static Date getBeginTimeOfMonth(Long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    /**
     * 切换日期格式
     *
     * @param date
     * @param format
     * @return
     */
    public static Date modifyDate(Date date, String format) {
        String time = DateTimeUtil.convertDate(date, format);
        return DateTimeUtil.convertStrToDate(time);
    }

    /**
     * 根据秒数获取时间串
     *
     * @param second (eg: 100s)
     * @return (eg : 00 : 01 : 40)
     */
    public static String getTimeFormatBySecond(int second) {
        if (second <= 0) {
            return "00:00:00";
        }
        int hours = second / HOUR_SECOND;
        if (hours > 0) {
            second -= hours * HOUR_SECOND;
        }
        int minutes = second / MINUTE_SECOND;
        if (minutes > 0) {
            second -= minutes * MINUTE_SECOND;
        }
        return (hours > 10 ? (hours + "")
                : ("0" + hours) + ":" + (minutes > 10 ? (minutes + "") : ("0" + minutes)) + ":"
                + (second > 10 ? (second + "") : ("0" + second)));
    }

    /**
     * 将LocalDateTime转换成Date
     *
     * @param localDateTime
     * @return
     */
    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        Assert.notNull(localDateTime, "localDateTime is null");

        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }

    /**
     * 获取当天指定时分秒的时间戳
     * @return
     */
    public static Long getTodayDateTimestamp(int hour,int minute,int second){
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), hour, minute, second);
        return cal.getTime().getTime();
    }

    public static int getDaysOfSeconds(long seconds) {
        return (int) (seconds / (1000 * 60 * 60 * 24));
    }

    /**
     * 获取指定日期为周几,周一是1，依此类推<p></p>
     * @param date
     * @param subHour 不需要减去多少小时,则传值为0
     * @return
     */
    public static int getWeekDay(Date date,int subHour){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR, -subHour);
        int weekday = c.get(Calendar.DAY_OF_WEEK)-1;
        if (weekday == 0) {
            weekday = 7;
        }
        return weekday;
    }

    /**
     * 获取指定日期是第几月
     * @param date
     * @return
     */
    public static int getMonth(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH);
    }

    /**
     * 获取指定日期是当月第几天
     * @param date
     * @return
     */
    public static int getMonthDay(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取指定月份最后一天
     * @return
     */
    public static Date getMonthLastDay(Integer yearNum,Integer monthNum,Integer hour,Integer minute,Integer second){
        Calendar cal = Calendar.getInstance();
        if(yearNum != null){
            cal.set(Calendar.YEAR,yearNum);
        }
        if(monthNum != null){
            cal.set(Calendar.MONTH,monthNum-1);
        }
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        cal.set(Calendar.HOUR_OF_DAY,hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        return cal.getTime();
    }

    /**
     * 获取指定日期第一天
     * @return
     */
    public static Date getMonthFirstDay(Integer yearNum,Integer monthNum,Integer hour,Integer minute,Integer second){
        Calendar cal = Calendar.getInstance();
        if(yearNum != null){
            cal.set(Calendar.YEAR,yearNum);
        }
        if(monthNum != null){
            cal.set(Calendar.MONTH,monthNum-1);
        }
        cal.set(Calendar.DAY_OF_MONTH,1);
        cal.set(Calendar.HOUR_OF_DAY,hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        return cal.getTime();
    }
    /**
     * 获取某一周的周几<p></p>
     * lastWeek 如果是本周就填0,上周就填-1,下周就填1,依此类推<p></p>
     * dayWeek　周几,比如说 Calendar.MONDAY
     * @return
     */
    public static Date getMonday(Date InputDate,int lastWeek,int dayWeek, int hour, int minute, int second) {
        Calendar cDate = Calendar.getInstance();
        cDate.setFirstDayOfWeek(Calendar.MONDAY);
        cDate.setTime(InputDate);
        Calendar firstDate = Calendar.getInstance();
        firstDate.setFirstDayOfWeek(Calendar.MONDAY);
        firstDate.setTime(InputDate);
        if (cDate.get(Calendar.WEEK_OF_YEAR) == 1 && cDate.get(Calendar.MONTH) == 11) {
            firstDate.set(Calendar.YEAR, cDate.get(Calendar.YEAR) + 1);
        }
        int typeNum = cDate.get(Calendar.WEEK_OF_YEAR);

        firstDate.set(Calendar.WEEK_OF_YEAR, typeNum+lastWeek);
        firstDate.set(Calendar.DAY_OF_WEEK,dayWeek);
        //所在周开始日期
        firstDate.set(Calendar.HOUR_OF_DAY, hour);
        firstDate.set(Calendar.MINUTE, minute);
        firstDate.set(Calendar.SECOND, second);
        return firstDate.getTime();
    }


    public static Date getSubMonthDate(Date now,Integer subMonthNum,Integer hour,Integer minute,Integer second,Integer milli){
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.MONTH, -subMonthNum);
        cal.set(Calendar.HOUR_OF_DAY,hour);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, second);
        cal.set(Calendar.MILLISECOND,milli);
        return cal.getTime();
    }

    /**
     * 获取上一个月的时间
     * @param date
     * @return java.util.Date
     * @author qinrenchuan
     * @date 2019/11/19/0019 15:34
     */
    public static Date getLastMonthBeging(Date date) {
        Calendar calendar = Calendar.getInstance();
        // 设置为当前时间
        calendar.setTime(date);
        // 设置为上一个月
        calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) - 1);
        return calendar.getTime();
    }

    /**
     * 获取离给定日期这一天最后一秒相隔几秒
     * @param now
     * @return
     */
    public static Integer getDateCountdownSecond(Date now){
        Calendar midnight=Calendar.getInstance();
        midnight.setTime(now);
        midnight.add(midnight.DAY_OF_MONTH,1);
        midnight.set(midnight.HOUR_OF_DAY,0);
        midnight.set(midnight.MINUTE,0);
        midnight.set(midnight.SECOND,0);
        midnight.set(midnight.MILLISECOND,0);
        Integer seconds=(int)((midnight.getTime().getTime()-now.getTime())/1000);
        return seconds;
    }

    /**
     * 获取某月第一天
     * @return
     */
    public static Date getFisrtDayOfMonth()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        //获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        return cal.getTime();
    }


    public static String getRamadanDayStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_MONTH_DAY_PATTERN);
        String format = sdf.format(date);
        //去掉首位的0
        String newStr = format.replaceAll("^(0+)", "");
        return newStr;
    }




    public static void main(String[] args) {
       System.out.println(convertDate(getNextHour(new Date(),5)));
    }

}
