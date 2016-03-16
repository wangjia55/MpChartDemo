package com.wqdata.net.chart;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by bill on 2015/7/1.
 */
public class DateUtils {
    private static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd HH:mm";
    private static SimpleDateFormat mFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
    private static final String DATE_FORMAT = "yyyy/MM/dd";
    private static SimpleDateFormat mDateFormat = new SimpleDateFormat(DATE_FORMAT);
    private static final String HOUR_MIN_FORMAT = "HH:mm";
    private static SimpleDateFormat mHourMinFormat = new SimpleDateFormat(HOUR_MIN_FORMAT);
    private static final String HOUR_MIN_SPLIT_FORMAT = "HH: mm";
    private static SimpleDateFormat mHourMinSplitFormat = new SimpleDateFormat(HOUR_MIN_SPLIT_FORMAT);
    private static final String MONTH_FORMAT = "MM";
    private static SimpleDateFormat mMonthFormat = new SimpleDateFormat(MONTH_FORMAT);
    private static final String DAY_FORMAT = "dd";
    private static SimpleDateFormat mDayFormat = new SimpleDateFormat(DAY_FORMAT);
    private static final String[] MONTH_FOR_SHORT = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private static SimpleDateFormat sDailySimpleFormat = new SimpleDateFormat("MM.dd");
    private static SimpleDateFormat sMonthlySimpleFormat = new SimpleDateFormat("yyyy.MM");
    private static SimpleDateFormat sSimpleFormat1 = new SimpleDateFormat("MM.dd");
    private static SimpleDateFormat sSimpleFormat2 = new SimpleDateFormat("MM月dd日");
    private static SimpleDateFormat sTimeSimpleFormat = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat sBirthdaySimpleFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static DateUtils sInstance;

    private DateUtils() {

    }

    public static DateUtils getInstance() {
        if (sInstance == null) {
            synchronized (DateUtils.class) {
                if (sInstance == null) {
                    sInstance = new DateUtils();
                }
            }
        }
        return sInstance;
    }


    public static String formatDate(Date date) {
        return mFormat.format(date);
    }

    public static String formatDate(long millis) {
        return mFormat.format(millis);
    }

    public static String formatYMD(long millis) {
        return mDateFormat.format(millis);
    }

    public static String formatYMD(Date date) {
        return mDateFormat.format(date);
    }

    public static String formatMonth(long millis) {
        return mMonthFormat.format(millis);
    }

    public static String formatDay(long millis) {
        return mDayFormat.format(millis);
    }

    /**
     * 判断时间戳为多少天之前
     *
     * @param millis 时间戳
     * @return 0表示今天，1表示1天谴，-1表示超过一天
     */
    public static int getDaysInterval(long millis) {
        Calendar now = Calendar.getInstance();
        long ms = 1000 * (now.get(Calendar.HOUR_OF_DAY) * 3600 + now.get(Calendar.MINUTE) * 60 + now.get(Calendar.SECOND));//毫秒数

        long curTime = System.currentTimeMillis();
        long time = curTime - millis;

        if (time < ms) {
            return 0;
        } else if (time < (ms + 24 * 3600 * 1000)) {
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * Use to only format hours and minutes
     */
    public static String formatHourMin(long millis) {
        return mHourMinFormat.format(millis);
    }

    /**
     * Use to only format hours and minutes
     */
    public static String formatHourMinSplit(long millis) {
        return mHourMinSplitFormat.format(millis);
    }

    public static String getMonthForShort(long millis) {
        String month = mMonthFormat.format(millis);
        return MONTH_FOR_SHORT[Integer.valueOf(month) - 1];
    }


    public static String formatDate1(long date) {
        return sSimpleFormat1.format(date);
    }

    public static String formatDate2(long date) {
        if (isToday(date)) {
            return "今天";
        } else if (isYesterday(date)) {
            return "昨天";
        }

        return sSimpleFormat2.format(date);
    }


    public static long getDayBeginTimeInMillis(long timeInMillis) {
        Calendar calendar = (Calendar) Calendar.getInstance().clone();
        calendar.setTimeInMillis(timeInMillis);
        calendar.set(Calendar.HOUR_OF_DAY, Calendar.getInstance().getActualMinimum(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, Calendar.getInstance().getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, Calendar.getInstance().getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MILLISECOND, Calendar.getInstance().getActualMinimum(Calendar.MILLISECOND));
        return calendar.getTimeInMillis();
    }

    public static Calendar getStartOfDay(Calendar calendar) {
        Calendar start = (Calendar) calendar.clone();
        start.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        start.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        start.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        start.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
        return start;
    }

    public static Calendar getEndOfDay(Calendar calendar) {
        Calendar end = (Calendar) calendar.clone();
        end.set(Calendar.HOUR_OF_DAY, calendar.getActualMaximum(Calendar.HOUR_OF_DAY));
        end.set(Calendar.MINUTE, calendar.getActualMaximum(Calendar.MINUTE));
        end.set(Calendar.SECOND, calendar.getActualMaximum(Calendar.SECOND));
        end.set(Calendar.MILLISECOND, calendar.getActualMaximum(Calendar.MILLISECOND));
        return end;
    }

    public static String getDailyFormatString(long calendar) {
        return sDailySimpleFormat.format(calendar);
    }

    public static String getDailyFormatString(Calendar calendar) {
        return sDailySimpleFormat.format(calendar.getTimeInMillis());
    }

    public static String getBirthdayFormatString(long calendar) {
        return sBirthdaySimpleFormat.format(calendar);
    }

    public static Date getBirthdayParseString(String calendar) {
        try {
            return sBirthdaySimpleFormat.parse(calendar);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static boolean isToday(Calendar calendar) {
        return (calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)) &&
                (calendar.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
    }

    public static boolean isToday(long timeInMillis) {
        Calendar calendar = (Calendar) Calendar.getInstance().clone();
        calendar.setTimeInMillis(timeInMillis);
        return (calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)) &&
                (calendar.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR));
    }

    public static boolean isYesterday(long timeInMillis) {
        Calendar calendar = (Calendar) Calendar.getInstance().clone();
        calendar.setTimeInMillis(timeInMillis);
        return (calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)) &&
                (calendar.get(Calendar.DAY_OF_YEAR) == Calendar.getInstance().get(Calendar.DAY_OF_YEAR) - 1);
    }

    public static Calendar getMondayOfWeek(Calendar calendar) {
        Calendar monday = (Calendar) calendar.clone();
        int day_of_week = monday.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        monday.add(Calendar.DATE, -day_of_week + 1);
        monday.set(Calendar.HOUR_OF_DAY, monday.getActualMinimum(Calendar.HOUR_OF_DAY));
        monday.set(Calendar.MINUTE, monday.getActualMinimum(Calendar.MINUTE));
        monday.set(Calendar.SECOND, monday.getActualMinimum(Calendar.SECOND));
        monday.set(Calendar.MILLISECOND, monday.getActualMinimum(Calendar.MILLISECOND));
        return monday;
    }

    public static Calendar getSundayOfWeek(Calendar calendar) {
        Calendar sunday = (Calendar) calendar.clone();
        int day_of_week = sunday.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        sunday.add(Calendar.DATE, -day_of_week + 7);
        sunday.set(Calendar.HOUR_OF_DAY, sunday.getActualMaximum(Calendar.HOUR_OF_DAY));
        sunday.set(Calendar.MINUTE, sunday.getActualMaximum(Calendar.MINUTE));
        sunday.set(Calendar.SECOND, sunday.getActualMaximum(Calendar.SECOND));
        sunday.set(Calendar.MILLISECOND, sunday.getActualMaximum(Calendar.MILLISECOND));
        return sunday;
    }

    public static String getWeeklyFormatString(Calendar calendar) {
        Calendar monday = getMondayOfWeek(calendar);
        Calendar sunday = getSundayOfWeek(calendar);
        return sDailySimpleFormat.format(monday.getTimeInMillis()) + " - " + sDailySimpleFormat.format(sunday.getTimeInMillis());
    }

    public static boolean isThisWeek(Calendar calendar) {
        return (calendar.get(Calendar.WEEK_OF_YEAR) == Calendar.getInstance().get(Calendar.WEEK_OF_YEAR)) &&
                (calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR));
    }

    public static Calendar getStartOfMonth(Calendar calendar) {
        Calendar begin = (Calendar) calendar.clone();
        begin.set(Calendar.DAY_OF_MONTH, begin.getActualMinimum(Calendar.DAY_OF_MONTH));
        begin.set(Calendar.HOUR_OF_DAY, begin.getActualMinimum(Calendar.HOUR_OF_DAY));
        begin.set(Calendar.MINUTE, begin.getActualMinimum(Calendar.MINUTE));
        begin.set(Calendar.SECOND, begin.getActualMinimum(Calendar.SECOND));
        begin.set(Calendar.MILLISECOND, begin.getActualMinimum(Calendar.MILLISECOND));
        return begin;
    }

    public static Calendar getEndOfMonth(Calendar calendar) {
        Calendar end = (Calendar) calendar.clone();
        end.set(Calendar.DAY_OF_MONTH, end.getActualMaximum(Calendar.DAY_OF_MONTH));
        end.set(Calendar.HOUR_OF_DAY, end.getActualMaximum(Calendar.HOUR_OF_DAY));
        end.set(Calendar.MINUTE, end.getActualMaximum(Calendar.MINUTE));
        end.set(Calendar.SECOND, end.getActualMaximum(Calendar.SECOND));
        end.set(Calendar.MILLISECOND, end.getActualMaximum(Calendar.MILLISECOND));
        return end;
    }

    public static String getMonthlyFormatString(Calendar calendar) {
        return sMonthlySimpleFormat.format(calendar.getTimeInMillis());
    }

    public static boolean isThisMonth(Calendar calendar) {
        return (calendar.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH)) &&
                (calendar.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR));
    }

    public static long getAccuracyToHour(long collectTime) {
        Calendar calendar = (Calendar) Calendar.getInstance().clone();
        calendar.setTimeInMillis(collectTime);
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        return calendar.getTimeInMillis();
    }

    public static long getAccuracyToDay(long collectTime) {
        Calendar calendar = (Calendar) Calendar.getInstance().clone();
        calendar.setTimeInMillis(collectTime);
        calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
        calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
        calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
        calendar.set(Calendar.HOUR_OF_DAY, calendar.getActualMinimum(Calendar.HOUR_OF_DAY));
        return calendar.getTimeInMillis();
    }

    public static String getTimeFormatString(long time) {
        return sTimeSimpleFormat.format(time);
    }

    public static long getSomeMinuteAgo(Calendar calendar, int minute) {
        Calendar end = (Calendar) calendar.clone();
        end.add(Calendar.MINUTE, -1 * minute);
        return end.getTime().getTime();
    }

    public static long getSomeHoursAgo(Calendar calendar, int hours) {
        Calendar end = (Calendar) calendar.clone();
        end.add(Calendar.HOUR_OF_DAY, -1 * hours);
        return end.getTime().getTime();
    }

    public static long getSomeDaysAgo(Calendar calendar, int days) {
        Calendar end = (Calendar) calendar.clone();
        end.add(Calendar.DAY_OF_MONTH, -1 * days);
        return end.getTime().getTime();
    }


    public static Date getSomeMinuteAfter(Calendar calendar, int minute) {
        Calendar end = (Calendar) calendar.clone();
        end.add(Calendar.MINUTE, minute);
        return end.getTime();
    }

    public static List<Date> getBeforeAndAfterHalfHour(Date date) {
        List<Date> dateList = new ArrayList<Date>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, -1 * 30);
        Date startDate = calendar.getTime();
        calendar.add(Calendar.MINUTE, 60);
        Date endDate = calendar.getTime();
        dateList.add(startDate);
        dateList.add(endDate);
        return dateList;
    }


    /**
     * 判断是否是同一年
     */
    public static boolean isSameYear(Date date) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date);
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR);
    }

    public static boolean isSameDay(Calendar source, Calendar target) {
        return (source.get(Calendar.YEAR) == target.get(Calendar.YEAR)) &&
                (source.get(Calendar.MONTH) == target.get(Calendar.MONTH)) &&
                (source.get(Calendar.DAY_OF_MONTH) == target.get(Calendar.DAY_OF_MONTH));
    }


}
