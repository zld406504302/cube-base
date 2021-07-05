package cn.cube.base.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtils {
    // 测试使用
    private static long SHIFT_TIME = 0;

    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_MILLITIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String DATE_MONTH_FORMAT = "yyyy-MM";
    public static final String DATE_SIMPLE_FORMAT = "yyyyMMdd";

    public static final String DATE_CHINA_FORMAT = "yyyy年M月d日";

    /**
     * 时间值定义
     */
    public static final long ONE_SECONDS_MILLIS = 1000;
    public static final long ONE_MINUTES_SECONDS = 60;
    public static final long ONE_HOURS_MINUTES = 60;
    public static final long ONE_DAY_HOURS = 24;

    public static final long ONE_MINUTES_MILLIS = ONE_MINUTES_SECONDS * ONE_SECONDS_MILLIS;
    public static final long ONE_HOURS_MILLIS = ONE_HOURS_MINUTES * ONE_MINUTES_MILLIS;
    public static final long ONE_DAY_MILLIS = ONE_DAY_HOURS * ONE_HOURS_MILLIS;

    public static final long ONE_DAY_SECONDS = ONE_DAY_MILLIS / ONE_SECONDS_MILLIS;

    public static final String DATE_TYPE_DATE = "date";
    public static final String DATE_TYPE_DATETIME = "datetime";


    /**
     * 返回当前时间的毫秒值
     */
    public static Date currentDate() {
        return new Date(currentTimeMillis());
    }

    /**
     * @return yyyy-MM-dd date str
     */
    public static String currentFormat() {
        return format(new Date(), DATE_FORMAT);
    }

    public static Date yesterday() {
        Date date = currDate();
        return addDays(date, -1);
    }

    public static Date tomorrow() {
        Date date = currDate();
        return addDays(date, 1);
    }

    /**
     * 返回当前时间的毫秒值
     */
    public static long currentTimeMillis() {
        return System.currentTimeMillis() + getShiftTime();
    }

    public static long getShiftTime() {
//        /** TODO 测试使用，上线时注释掉 **/
//        String open = PropertiesUtils.getValue("shift.time.open");
//        if (StringUtils.isEmpty(open)) {
//            return 0;
//        }
//        /** TODO 测试使用，上线时注释掉 **/
        return SHIFT_TIME;
    }

    public static void setShiftTime(long shiftTime) {
        SHIFT_TIME = shiftTime;
    }

    public static String getInterval(Date date) {
        // 获取距离现在的时间间隔，毫秒值
        long time = currentTimeMillis() - date.getTime();
        Date now = new Date();
        String intervalStr;
        if (time < 10 * ONE_MINUTES_MILLIS) { // 小与10 分钟
            intervalStr = "刚刚";
        } else if (time < ONE_HOURS_MILLIS) { //超过10分钟 不足1小时
            Long diffMinutes = diffMinutes(now, date);
            intervalStr = diffMinutes + "分钟前";
        } else if (isTheSameDay(date, now)) { //同日
            Long diffHours = diffHours(now, date);
            intervalStr = diffHours + "小时前";
        } else if (isSameYear(date, now)) {
            intervalStr = format(date, "M月d日");
        } else {
            intervalStr = format(date, "yyyy年M月d日");
        }

        return intervalStr;
    }

    public static Long diffMinutes(Date big, Date little) {
        Long bigL = big.getTime();
        Long litL = little.getTime();

        Long diffMillis = bigL - litL;

        Long diffMinutes = diffMillis / ONE_MINUTES_MILLIS;
        return diffMinutes;
    }

    public static Long diffHours(Date big, Date little) {
        Long bigL = big.getTime();
        Long litL = little.getTime();

        Long diffMillis = bigL - litL;

        Long diffHours = diffMillis / ONE_HOURS_MILLIS;
        return diffHours;
    }

    /**
     * Description:计算指定时间与当前时间的时间间隔
     *
     * @param beginDate 指定开始时间
     * @param endDate   指定结束时间
     * @param unit      返回的时间单位
     * @param isCeil    是否向上取整
     * @return
     * @author dekunzhao
     */
    public static Long getInterval(Date beginDate, Date endDate, TimeUnit unit, boolean isCeil) {
        long time = endDate.getTime() - beginDate.getTime();
        long result = time;
        if (TimeUnit.DAYS.equals(unit)) { // 1 天
            result = (time / ONE_DAY_MILLIS);
            if (isCeil) {
                result += (time % ONE_DAY_MILLIS) > 0 ? 1 : 0;
            }
        } else if (TimeUnit.HOURS.equals(unit)) { // 1 小时
            result = (time / ONE_HOURS_MILLIS);//得出的时间间隔的单位是小时
            if (isCeil) {
                result += (time % ONE_HOURS_MILLIS) > 0 ? 1 : 0;
            }
        } else if (TimeUnit.MINUTES.equals(unit)) { // 1 分钟
            result = (time / ONE_MINUTES_MILLIS);//得出的时间间隔的单位是分钟
            if (isCeil) {
                result += (time % ONE_MINUTES_MILLIS) > 0 ? 1 : 0;
            }
        } else if (TimeUnit.SECONDS.equals(unit)) { // 10 秒
            result = (time / ONE_SECONDS_MILLIS);
            if (isCeil) {
                result += (time % ONE_SECONDS_MILLIS) > 0 ? 1 : 0;
            }
        }
        return result;
    }


    public static Long calcIntervalToNextDay(Date currentDate, TimeUnit unit, boolean isCeil) {
        Date nextDate = DateUtils.addDays(currentDate, 1);
        Calendar cal = Calendar.getInstance();
        cal.setTime(nextDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        nextDate = cal.getTime();
        return DateUtils.getInterval(currentDate, nextDate, unit, isCeil);
    }

    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH);
    }

    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取当前时间距离下一天0点的剩余毫秒数
     *
     * @return
     */
    public static long getMillisTimeToNextDay() {
        Calendar cal = Calendar.getInstance();

        long nowTime = cal.getTimeInMillis();

        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        return cal.getTimeInMillis() - nowTime;
    }

    /**
     * 字符戳转Date类型的字符串
     *
     * @param timestamp    Long 时间戳
     * @param formatString String 字符串格式；如：yyyy-MM-dd hh:mm:ss
     */
    public static String long2DateString(long timestamp, String formatString) {
        if (formatString == null) {
            formatString = DATE_TIME_FORMAT;
        }
        DateFormat dd = new SimpleDateFormat(formatString);
        return dd.format(timestamp);
    }

    public static Date long2Date(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        LocalDate localDate = LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date long2DateTime(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        return cal.getTime();
    }

    /**
     * 字符戳转Date类型的字符串, 默认使用 yyyy-MM-dd hh:mm:ss 格式
     *
     * @param date date 时间
     */
    public static String format(Date date) {
        return format(date, DATE_TIME_FORMAT);
    }

    /**
     * 字符戳转Date类型的字符串, 默认使用 yyyy-MM-dd hh:mm:ss 格式
     *
     * @param date         date 时间
     * @param formatString String 字符串格式；如：yyyy-MM-dd hh:mm:ss
     */
    public static String format(Date date, String formatString) {
        if (formatString == null) {
            formatString = DATE_TIME_FORMAT;
        }
        DateFormat dd = new SimpleDateFormat(formatString);
        return dd.format(date);
    }

    public static Date addMonths(Date date, int amount) {
        return org.apache.commons.lang3.time.DateUtils.addMonths(date, amount);
    }

    public static Date addDays(Date date, int amount) {
        return org.apache.commons.lang3.time.DateUtils.addDays(date, amount);
    }

    public static Date addHours(Date date, int amount) {
        return org.apache.commons.lang3.time.DateUtils.addHours(date, amount);
    }

    public static Date addMinutes(Date date, int amount) {
        return org.apache.commons.lang3.time.DateUtils.addMinutes(date, amount);
    }

    public static Date addSeconds(Date date, int amount) {
        return org.apache.commons.lang3.time.DateUtils.addSeconds(date, amount);
    }

    public static Date setDays(Date date, int amount) {
        return org.apache.commons.lang3.time.DateUtils.setDays(date, amount);
    }

    public static Date setHours(Date date, int amount) {
        return org.apache.commons.lang3.time.DateUtils.setHours(date, amount);
    }

    public static Date setMinutes(Date date, int amount) {
        return org.apache.commons.lang3.time.DateUtils.setMinutes(date, amount);
    }

    public static Date setSeconds(Date date, int amount) {
        return org.apache.commons.lang3.time.DateUtils.setSeconds(date, amount);
    }

    /**
     * 字符转Date类型
     *
     * @param dateString   String 时间字符串
     * @param formatString String 字符串格式；如：yyyy-MM-dd hh:mm:ss，年-月-日 时:分:秒
     */
    public static Date parseDate(String dateString, String formatString) {
        if (formatString == null) {
            formatString = DATE_TIME_FORMAT;
        }
        DateFormat dd = new SimpleDateFormat(formatString);
        try {
            return dd.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param date1
     * @param date2
     * @return int 天数
     * @Description:计算date1和date2相隔多少天
     */
    public static int sub(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new RuntimeException("params is null");
        }
        Calendar cal = Calendar.getInstance();

        cal.setTime(date1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long time1 = cal.getTimeInMillis();

        cal.setTime(date2);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long time2 = cal.getTimeInMillis();

        int days = (int) ((time1 - time2) / ONE_DAY_MILLIS);

        return days;
    }

    /**
     * 非自然天数差
     *
     * @param bigDate
     * @param littelDate
     * @return int
     * @Description:计算date1和date2相隔多少天
     */
    public static int dateDiff(Date bigDate, Date littelDate) {
        if (bigDate == null || littelDate == null) {
            throw new RuntimeException("params is null");
        }
        Calendar cal = Calendar.getInstance();

        cal.setTime(bigDate);
        long time1 = cal.getTimeInMillis();

        cal.setTime(littelDate);
        long time2 = cal.getTimeInMillis();

        int days = (int) ((time1 - time2) / ONE_DAY_MILLIS);

        return days;
    }


    /**
     * @param year
     * @param month
     * @return
     * @Date:2017年3月31日
     * @Description:获取X年X月的天数
     * @author:dekunzhao
     */
    public static int getDayOfMonth(int year, int month) {
        Calendar date = Calendar.getInstance();
        date.set(Calendar.YEAR, year);
        date.set(Calendar.MONTH, month);

        date.set(Calendar.DATE, 1);
        date.roll(Calendar.DATE, -1);

//        int result = date.getActualMaximum(Calendar.DAY_OF_MONTH);

        return date.get(Calendar.DATE);
    }

    /**
     * 时间距离时间凌晨过去多少分钟
     *
     * @param date
     * @return
     */
    public static int getMinuteToAM(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        int calculateMinute = hour * 60 + minute;

        return calculateMinute;
    }

    /**
     * TODO 两个日期是不是同一天(优化方法)
     *
     * @param date1
     * @param date2
     * @return boolean
     */
    public static boolean isTheSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(date1);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date2);

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取日期年字符串
     *
     * @param dateString
     * @param formatString
     * @return String
     */
    public static String getYearString(String dateString, String formatString) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(parseDate(dateString, formatString));
        int year = cal.get(Calendar.YEAR);
        return String.valueOf(year);
    }

    /**
     * 两个日期时间是否是同一周内
     *
     * @param date1
     * @param date2
     * @return boolean
     */
    public static boolean isSameWeek(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(date1);

        Calendar ca2 = Calendar.getInstance();
        ca2.setFirstDayOfWeek(Calendar.MONDAY);
        ca2.setTime(date2);

        return cal.get(Calendar.WEEK_OF_YEAR) == ca2.get(Calendar.WEEK_OF_YEAR);
    }

    public static boolean isSameYear(Date date1, Date date2) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.setTime(date1);

        Calendar ca2 = Calendar.getInstance();
        ca2.setFirstDayOfWeek(Calendar.MONDAY);
        ca2.setTime(date2);
        return cal.get(Calendar.YEAR) == ca2.get(Calendar.YEAR);
    }

    public static Date currDate() {
        return DateUtils.parseDate(DateUtils.format(new Date(), DateUtils.DATE_FORMAT), DateUtils.DATE_FORMAT);
    }

    public static int chinaDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                return 1;
            case Calendar.TUESDAY:
                return 2;
            case Calendar.WEDNESDAY:
                return 3;
            case Calendar.THURSDAY:
                return 4;
            case Calendar.FRIDAY:
                return 5;
            case Calendar.SATURDAY:
                return 6;
            case Calendar.SUNDAY:
                return 7;
            default:
                return 1;
        }
    }

    /**
     * 获取当前一天结束时间
     *
     * @return
     */
    public static Date getEndDateTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);

        return cal.getTime();
    }

    /**
     * 获取某天某时
     *
     * @return
     */
    public static Date getDateHourTime(Date date, int hour) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        return cal.getTime();
    }

    /**
     * 生成10位时间戳
     *
     * @return
     */
    public static String get10TimeStamp() {
        String timestamp = System.currentTimeMillis() + "";
        timestamp = timestamp.substring(0, 10);
        return timestamp;
    }

    public static boolean isExpire(Date now, Date start, Date end) {
        if (null == start || null == end) {
            return false;
        }
        return !now.after(start) || !now.before(end);
    }


    public static void main(String[] args) {

        Date now = new Date();


        Date start = DateUtils.addDays(now, -1);
        Date end = DateUtils.addDays(now, 1);

        System.out.println(isExpire(now, start, end));
    }


}
