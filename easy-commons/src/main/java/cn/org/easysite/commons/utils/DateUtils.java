package cn.org.easysite.commons.utils;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static cn.org.easysite.commons.constants.BaseConstants.LONG_DATE_FORMAT_STR;


/**
 * 日期/时间工具类
 *
 * @author trang
 */
public final class DateUtils {

    public static final TimeZone TIME_ZONE = TimeZone.getTimeZone("GMT+8");

    /**
     * 	SimpleDateFormat 的parse, format不是线程安全的。
     */
    public static final ThreadLocal<SimpleDateFormat> LONG_DATE_FORMAT = ThreadLocal.withInitial(() -> {
        SimpleDateFormat dateFormat = new SimpleDateFormat(LONG_DATE_FORMAT_STR);
        dateFormat.setTimeZone(TIME_ZONE);
        return dateFormat;
    });

    /**
     * 获取默认时间格式: yyyy-MM-dd HH:mm:ss
     */
    private static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER = DateFormat.LONG_DATE_PATTERN_LINE.formatter;

    /**
     * String to LocalDateTime
     */
    public static LocalDateTime parse(String dateTime) {
        return LocalDateTime.parse(dateTime, DEFAULT_DATETIME_FORMATTER);
    }

    /**
     * String to LocalDateTime
     */
    public static LocalDateTime parse(String dateTime, DateFormat format) {
        return LocalDateTime.parse(dateTime, format.formatter);
    }

    /**
     * LocalDateTime to String
     */
    public static String to(LocalDateTime dateTime) {
        return DEFAULT_DATETIME_FORMATTER.format(dateTime);
    }

    /**
     * LocalDateTime to String
     */
    public static String to(LocalDateTime dateTime, DateFormat format) {
        return format.formatter.format(dateTime);
    }

    /**
     * 获取当前时间
     */
    public static String now() {
        return DEFAULT_DATETIME_FORMATTER.format(LocalDateTime.now());
    }

    /**
     * 获取当前时间
     */
    public static String now(DateFormat format) {
        return format.formatter.format(LocalDateTime.now());
    }

    public static String toDatePattenString(long date) {
        return DEFAULT_DATETIME_FORMATTER.format(new Date(date).toInstant().atOffset(ZoneOffset.of("+8")).toLocalDateTime());
    }

    /**
     * 获取当前时间距 1970-1-1 毫秒数
     */
    public static long currentMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 获取指定时间距 获取某个时间 毫秒数
     */
    public static long toMillis(LocalDateTime dateTime) {
        return dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
    }

    /**
     * 格式化日期
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 获取当天0点时间戳
     * @return
     */
    public static long getCurrentDateZeroMillis() {
        return System.currentTimeMillis() / (1000*3600*24)*(1000*3600*24) - TimeZone.getDefault().getRawOffset();
    }

    public static LocalDateTime minusDay(int day) {
        return LocalDateTime.now().minus(day, ChronoUnit.DAYS);
    }

    /**
     * 获取当天0点的秒
     * @return
     */
    public static int getCurrentZeroSeconds() {
        return (int) (getCurrentDateZeroMillis() / 1000);
    }

    /**
     * 时间格式
     */
    public enum DateFormat {

        /**
         * 短时间格式
         */
        SHORT_DATE_PATTERN_LINE("yyyy-MM-dd"),
        SHORT_DATE_PATTERN_SLASH("yyyy/MM/dd"),
        SHORT_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd"),
        SHORT_DATE_PATTERN_NONE("yyyyMMdd"),
        SHORT_DATE_PATTERN_MIN("yyyyMMddHHmm"),
        SHORT_DATE_PATTERN_SECOND("yyyyMMddHHmmss"),

        /**
         * 长时间格式
         */
        LONG_DATE_PATTERN_LINE("yyyy-MM-dd HH:mm:ss"),
        LONG_DATE_PATTERN_SLASH("yyyy/MM/dd HH:mm:ss"),
        LONG_DATE_PATTERN_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss"),
        LONG_DATE_PATTERN_NONE("yyyyMMdd HH:mm:ss"),

        /**
         * 长时间格式 带毫秒
         */
        LONG_DATE_PATTERN_WITH_MILLIS_LINE("yyyy-MM-dd HH:mm:ss.SSS"),
        LONG_DATE_PATTERN_WITH_MILLIS_SLASH("yyyy/MM/dd HH:mm:ss.SSS"),
        LONG_DATE_PATTERN_WITH_MILLIS_DOUBLE_SLASH("yyyy\\MM\\dd HH:mm:ss.SSS"),
        LONG_DATE_PATTERN_WITH_MILLIS_NONE("yyyyMMddHHmmssSSS");

        private transient DateTimeFormatter formatter;

        DateFormat(String pattern) {
            formatter = DateTimeFormatter.ofPattern(pattern);
        }
    }

    /**
     * 功能描述：返回分
     *
     *            日期
     * @return 返回分钟
     */
    public static int getNowMinute() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 计算相差天数
     */
    public static int differentDaysByMillisecond(Date date1, Date date2)
    {
        return Math.abs((int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24)));
    }

    /**
     * 计算日期相差天数
     * @param date1
     * @param date2
     * @return
     */
    public static int diffDays(Date date1, Date date2) {
        date1 = getMinOfDay(date1);
        date2 = getMinOfDay(date2);
        return Math.abs((int) ((date2.getTime() - date1.getTime()) / (1000 * 3600 * 24)));
    }

    public static Date getMinOfDay(Date date) {
        if (date == null) {
            date = new Date();
        }
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date getMaxOfDay(Date date) {
        if (date == null) {
            date = new Date();
        }
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());;
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date getMinOfCurrentMonth() {
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        return getMinOfDay(cale.getTime());
    }

    public static Date getMaxOfCurrentMonth() {
        Calendar cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        return getMinOfDay(cale.getTime());
    }

    /**
     * 在活动之间
     * @param date
     * @param startDate
     * @param endDate
     * @return
     */
    public static boolean between(Date date, Date startDate, Date endDate) {
        return date.getTime() >= startDate.getTime() && date.getTime() <= endDate.getTime();
    }

}
