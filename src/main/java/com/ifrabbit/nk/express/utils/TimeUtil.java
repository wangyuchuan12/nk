package com.ifrabbit.nk.express.utils;

import ir.nymph.date.DateTime;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.time.DateUtils;

/**
 * Created with IDEA
 * author:SunJiaJian
 * Date:2018/3/20
 * Time:15:24
 */
public class TimeUtil {
    public static Long getDistanceTime(String date1,String date2){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;

        try{
            one = dateFormat.parse(date1);
            two = dateFormat.parse(date2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if(time1 < time2){
                diff = time2 - time1;
            }else{
                diff = time1 - time2;
            }
            day = diff / (60 * 60 * 1000);
//          hour = (diff / (60 * 60 * 1000) - day * 24);
//          min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
//          sec = (diff/1000-day*24*60*60-hour*60*60-min*60);

        }catch (Exception e){
            e.printStackTrace();
        }
        return day;
    }

    //获取最初时间戳
    public static Timestamp getTime() {
        Timestamp timestamp =  new DateTime().toTimestamp();
        Date currentTime = timestamp;
        SimpleDateFormat format = new SimpleDateFormat("2000-01-01");
        String dateString = format.format(currentTime);
        try {
            return new Timestamp(format.parse(dateString).getTime());
        }catch (Exception e){
            e.printStackTrace();
        }
        return timestamp;
    }

    /**
     * yyyy-MM-dd
     * author:lishaomiao
     */
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final long DAY_MILLION_SECONDS = 1000 * 60 * 60 * 24;

    private static final DateFormat ymdhmsFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateFormat ymdhmFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    // 默认转换出为2018-09-08类型的当前日
    public static String getToday(String fmt) {
        if (StringUtils.isBlank(fmt)) {
            fmt = "yyyy-MM-dd";
        }
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        return sdf.format(dt);
    }

    public static String getTime(Date date) {
        String s = TimeUtil.getDate(date, "yyyy-MM-dd HH:mm:ss");
        return s;
    }


    /**
     * 字符串格式的日期与日期格式的日期按照相应的格式进行转换,此方法为根据指定日期格式返回相应的日期字符串
     *
     * @param date
     *            指定日期
     * @param format
     *            指定日期格式字符串
     * @return 相应的日期字符串
     * author:lishaomiao
     */
    public static String getDate(Date date, String format) {
        SimpleDateFormat df;
        try {
            df = new SimpleDateFormat(format);
            return df.format(date);
        } catch (Exception e) {
            return null;
        } finally {
            df = null;
        }
    }

    /**
     * 获取日期(format: yyyy-MM-dd)
     *
     * @author
     * @param p_date
     * @return
     * author:lishaomiao
     */
    public static Date getDate(String p_date) {
        Date date = null;
        try {
            date = TimeUtil.getDate(p_date, TimeUtil.YYYY_MM_DD);
        } catch (ParseException e) {
        }
        return date;
    }

    /**
     * format: yyyy-MM-dd
     *
     * @author
     * @return
     * author:lishaomiao
     */
    public static String getDefaultStringDate() {
        SimpleDateFormat df;
        try {
            df = new SimpleDateFormat(YYYY_MM_DD);
            return df.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            df = null;
        }
    }

    /**
     * 字符串格式的日期与日期格式的日期按照相应的格式进行转换,此方法为根据指定日期字符串格式返回相应的日期
     *
     * @param date
     *            指定日期字符串
     * @param format
     *            指定日期字符串格式
     * @return 相应的日期
     * @throws ParseException
     * author:lishaomiao
     */
    public static Date getDate(String date, String format)
            throws ParseException {
        SimpleDateFormat df;
        try {
            df = new SimpleDateFormat(format);
            return df.parse(date);
        } catch (Exception e) {
            return null;
        } finally {
            df = null;
        }
    }

    /**
     * 数字格式的日期与日期格式的日期按照相应的格式进行转换,此方法为根据指定的数字日期返回对应的日期
     *
     * @param date
     *            指定的数字日期
     * @return 对应的日期
     * author:lishaomiao
     */
    public static Date getDate(long date) {
        try {
            return new Date(date);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {

        }
    }

    /**
     * 数字格式的日期与日期格式的日期按照相应的格式进行转换,此方法为根据指定的数字日期和指定的日期格式返回对应的日期字符串
     *
     * @param date
     *            指定的数字日期
     * @param format
     *            指定的日期格式
     * @return 对应的日期字符串
     * author:lishaomiao
     */
    public static String getDate(long date, String format) {
        SimpleDateFormat df;
        Date de;
        try {
            df = new SimpleDateFormat(format);
            de = new Date(date);

            return df.format(de);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            df = null;
            de = null;
        }
    }

    /**
     * 数字格式的日期与日期格式的日期按照相应的格式进行转换,此方法为根据指定的日期返回对应的数字日期
     *author:lishaomiao
     * @param date
     *            指定的日期
     * @return 对应的数字日期
     */
    public static long getDate(Date date) {
        try {
            return date.getTime();
        } catch (Exception e) {
            return 0;
        }

    }

    /**
     * 日期加减，提供当前日期以及指定的年月日时分秒进行日期加减；
     *
     * @param now
     *            当前数字格式日期
     * @param year
     *            年
     * @param month
     *            月
     * @param day
     *            日
     * @param hour
     *            时
     * @param minute
     *            分
     * @param second
     *            秒
     * @return 经过日期加减处理过的数字日期
     * author:lishaomiao
     */
    public static long getDate(long now, int year, int month, int day,
                               int hour, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(now);
        if (second != 0)
            cal.add(Calendar.SECOND, second);
        if (minute != 0)
            cal.add(Calendar.MINUTE, minute);
        if (hour != 0)
            cal.add(Calendar.HOUR, hour);
        if (day != 0)
            cal.add(Calendar.DATE, day);
        if (month != 0)
            cal.add(Calendar.MONTH, month);
        if (year != 0)
            cal.add(Calendar.YEAR, year);

        return cal.getTime().getTime();
    }

    /**
     * 根据传入的日期，欲转换成的日期样式，返回转换后的日期字符串
     *author:lishaomiao
     * @param ldate
     *            - 日期
     * @param pattern
     *            - 具体的值范围见java.text.SimpleDateFormat的说明
     * @see SimpleDateFormat
     * @return String 转换后的日期字符串
     */
    public static String format(Date ldate, String pattern) {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.format(ldate);
    }

    /**
     * 根据传入的字符串，欲转换成的日期样式，返回转换后的日期
     *author:lishaomiao
     * @param ldate
     *            - 日期
     * @param pattern
     *            - 具体的值范围见java.text.SimpleDateFormat的说明
     * @see SimpleDateFormat
     * @return Date 转换后的日期
     * @throws ParseException
     */
    public static Date parse(String ldate, String pattern)
            throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        return dateFormat.parse(ldate);
    }

    /**
     * 增加几个月
     *author:lishaomiao
     * @param month
     * @return
     */
    public static String addMonth(int month) {
        DateFormat df = new SimpleDateFormat(YYYY_MM_DD);
        Calendar l_calendar = Calendar.getInstance();
        l_calendar.add(Calendar.MONTH, month);
        return df.format(l_calendar.getTime());
    }

    /**
     * 增加几个月
     *author:lishaomiao
     * @param month
     * @return
     */
    public static Date addMonthDate(int month) {
        Calendar l_calendar = Calendar.getInstance();
        l_calendar.add(Calendar.MONTH, month);
        return l_calendar.getTime();
    }

    /**
     * 时间类型字符串类型转换
     * @param dateStr
     * @param oldFormat
     * @param newFormat
     * @return
     * author:lishaomiao
     */
    public static String stringToString(String dateStr,String oldFormat,String newFormat){
        String result = null;
        try {
            String[] temp = {oldFormat};
            Date date = DateUtils.parseDate(dateStr, temp);
            result = dateToString(date, newFormat);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 时间类型转换成字符串
     * @param date
     * @return
     * author:lishaomiao
     */
    public static String dateToString(Date date,String format){
        String result = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            result = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     *根据字符串转成日期格式
     * author:lishaomiao
     * @version Jul 6, 2011
     * @param ymdhmsStringDate
     * @return
     */
    public static final Date ymdhmsString2DateTime(String ymdhmsStringDate){
        if (ymdhmsStringDate == null)
            return null;
        Date date = null;
        try {
            date = ymdhmsFormat.parse(ymdhmsStringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }


    /**
     *把20170706113721类型的字符串转成date
     * @version Jul 6, 2011
     * @param str
     * @return
     * @throws ParseException
     * author:lishaomiao
     */
    public synchronized static Date getDateStr(String str) {
        if(str == null || str.length()<14){
            return null;
        }

        StringBuffer sb = new StringBuffer();
        sb.append(str.substring(0, 4)).append("-");
        sb.append(str.substring(4, 6)).append("-");
        sb.append(str.substring(6, 8)).append(" ");
        sb.append(str.substring(8, 10)).append(":");
        sb.append(str.substring(10, 12)).append(":");
        sb.append(str.substring(12, 14));

        return ymdhmsString2DateTime(sb.toString());
    }
    /**
     *判断2个时间差:d2比d1相差多少时间（单位：分钟）
     * @version Jul 6, 2011
     * @param d1
     * @param d2
     * @return
     * author:lishaomiao
     */
    public static double getMinuteDiff(Date d1,Date d2) {
        double d1t = d1.getTime();
        double d2t = d2.getTime();
        return (double)((d2t - d1t) / 60000);
    }

    /**
     *判断2个时间差:d2比d1相差多少时间（单位：天）
     * @version Jul 6, 2011
     * @param d1
     * @param d2
     * @return
     * author:lishaomiao
     */
    public static double getDayDiff(Date d1,Date d2){
        double d1t = d1.getTime();
        double d2t = d2.getTime();
        return (double)((d2t - d1t) / (24*60*60*1000));
    }

    /**
     * 指定日期的前几天的日期
     * @param date
     * @param days
     * @param format
     * @return
     * author:lishaomiao
     */
    public static String getBeforeDay(Date date,int days,String format){
        long time = date.getTime() - days*24*60*60*1000;
        date = new Date(time);
        return getDate(date,format);
    }

    /**
     *输出时间格式：比如Thu Jul 14 14:40:40 CST 2011
     *则输出    1440
     * author:lishaomiao
     * @version Jul 14, 2011
     * @param date
     * @return
     */
    public static String getDateStrhhmms(Date date){
        String str = ymdhmFormat.format(date);
        str = str.replaceAll("-", "");
        str = str.replaceAll(":", "");
        str = str.replaceAll(" ", "");
        if(str == null || str.length() < 9){
            str = "";
        } else {
            str = str.substring(8);
        }
        return str;
    }


    /**
     * author:lishaomiao
     * 取当天的 0点0分0秒0毫秒
     * @version Mar 9, 2010
     * @return 2017-03-09 00：00：00:000
     */
    public static Date zerolizedTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
    /**
     * 时间查询时,结束时间的 23:59
     * author:lishaomiao
     */
    public static   String addDateEndPostfix(String datestring) {
        if (datestring == null || datestring.equals("")) {
            return null;
        }
        return datestring.substring(0, 8) + "235959";
    }

    public static String getEndDate(String date) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        StringBuilder sb = new StringBuilder(date.replaceAll("-", ""));
        sb.append("235959");
        return sb.toString();
    }

    /**
     *加减天数
     * @version Aug 12, 2011
     * @param days
     * @return
     * author:lishaomiao
     */
    public static Date increaseDate(Integer days){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar.getTime();
    }
    /**
     * 获取月份起始日期
     * @param date
     * @return
     * @throws ParseException
     * author:lishaomiao
     */
    public static String getMinMonthDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return  TimeUtil.getDate(calendar.getTime(), "yyyyMMdd")+"000000";
    }

    /**
     * 根据当前日期查询星期几
     * @version Jul 18, 2018
     * @param pTime
     * @return
     * @throws Exception
     * author:lishaomiao
     */
    public static String dayForWeek(String pTime){
        String[] weeks = {"星期一","星期二","星期三","星期四","星期五","星期六","星期日"};
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        int dayForWeek = 0;
        try {
            c.setTime(df.parse(pTime));
            if(c.get(Calendar.DAY_OF_WEEK) == 1){
                dayForWeek = 7;
            }else{
                dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return weeks[dayForWeek-1];
    }


    public static String timePare(String dateString) throws ParseException {
        dateString = dateString.replace("GMT", "").replaceAll("\\(.*\\)", "");
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss ", Locale.ENGLISH);
        Date dateTrans = format.parse(dateString);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateTrans).toString();
    }

    public static String timePare2(String dateString) throws ParseException {
        dateString = dateString.replace("GMT", "").replaceAll("\\(.*\\)", "");
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss ", Locale.ENGLISH);
        Date dateTrans = format.parse(dateString);
        return new SimpleDateFormat("yyyy-MM-dd").format(dateTrans).toString();
    }

    public static String timePare3(String dateString) throws ParseException {
        dateString = dateString.replace("GMT", "").replaceAll("\\(.*\\)", "");
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss ", Locale.ENGLISH);
        Date dateTrans = format.parse(dateString);
        return new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(dateTrans).toString();
    }

    public static String timePare4(String dateString) throws ParseException {
        dateString = dateString.replace("GMT", "").replaceAll("\\(.*\\)", "");
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss ", Locale.ENGLISH);
        Date dateTrans = format.parse(dateString);
        return new SimpleDateFormat("yyyy-MM-dd 23:59:59").format(dateTrans).toString();
    }

}
