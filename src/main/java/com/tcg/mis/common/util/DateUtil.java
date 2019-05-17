package com.tcg.mis.common.util;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * @author Eddie
 */
@SuppressWarnings("Duplicates")
public class DateUtil {

    public static final String PATTERN_1 = "yyyy-MM-dd";
    public static final String PATTERN_2 = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_3 = "yyyy-MM-dd HH:mm";
    public static final String PATTERN_4 = "MM/dd/yyyy";
    public static final String PATTERN_5 = "MM/dd/yyyy HH:mm:ss";
    public static final String PATTERN_6 = "MM/dd/yyyy HH:mm";
    public static final String PATTERN_7 = "yyyy-MM-dd HH:mm:ss.SSS";

    private DateUtil() {
        throw new IllegalStateException("Utility class");
    }

    
    /**
     * get a local date
     */
    public static Date getTodayTime() {
        return Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault()).getTime();
    }

    /**
     * 使用参数Format格式化Date成字符串
     */
    public static String format(Date date, String pattern) {
        return date == null ? " " : new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 使用参数Format将字符串转为Date
     */
    public static Date parse(String strDate, String pattern) throws ParseException {
        return StringUtils.isBlank(strDate) ? null : new SimpleDateFormat(pattern).parse(strDate);
    }

    /**
     * 取得给定日期前(后)几天要或前(后)几个月、或前(后)几年
     * <p>
     * field: the calendar field
     * field = Calendar.DATE 为天
     * field = Calendar.MONTH 为月
     * field = Calendar.YEAR 为年
     * <p>
     * amount:the amount of date or time to be added to the field
     * 1 = 加1
     * -1 = 减1
     *
     * @param date   date
     * @param field  the calendar field.
     * @param amount the amount of date or time to be added to the field.
     */
    public static Date getBeforeOrAfterByGivenDate(Date date, int field, int amount) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(field, amount);
        return cal.getTime();
    }

    /**
     * 获取某天00:00:00点
     */
    public static Date getStartTime(Date date) {
        Calendar transDate = Calendar.getInstance();
        transDate.setTime(date);
        transDate.set(Calendar.HOUR_OF_DAY, 0);
        transDate.set(Calendar.MINUTE, 0);
        transDate.set(Calendar.SECOND, 0);
        transDate.set(Calendar.MILLISECOND, 000);
        return transDate.getTime();
    }

    /**
     * 获取某天23:59:59点
     */
    public static Date getEndTime(Date date) {
        Calendar transDate = Calendar.getInstance();
        transDate.setTime(date);
        transDate.set(Calendar.HOUR_OF_DAY, 23);
        transDate.set(Calendar.MINUTE, 59);
        transDate.set(Calendar.SECOND, 59);
        transDate.set(Calendar.MILLISECOND, 999);
        return transDate.getTime();
    }

    public static boolean isDatePattern(String date, String patten, int length) {
        boolean flag = false;

        try {
            if (org.apache.commons.lang.StringUtils.isNotBlank(date) && date.length() == length) {
                SimpleDateFormat dateFormat = new SimpleDateFormat(patten);
                dateFormat.setLenient(false);
                dateFormat.parse(date);
                flag = true;
            }
        } catch (ParseException var5) {
            flag = false;
        }

        return flag;
    }

    private static boolean verifyDateInput(String input, String dateFormat) {
        if (StringUtils.isNotBlank(input)) {
            try {
                SimpleDateFormat format = new SimpleDateFormat(dateFormat);
                Date ret = format.parse(input.trim());
                if (format.format(ret).equals(input.trim())) {
                    return true;
                }
            } catch (ParseException ignored) {

            }
        }
        return false;
    }

    public static boolean verifyDateInput(String input) {
        return verifyDateInput(input, PATTERN_1);
    }

    public static boolean verifyDateTimeInput(String input) {
        return verifyDateInput(input, PATTERN_2);
    }

    public static int getDifferenceDays(Date d1, Date d2) {
        long diff = d2.getTime() - d1.getTime();
        return (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    /**
     * validate date for yyyy-MM-dd
     *
     * @param input
     *
     * @return
     */
    public static boolean checkDateInput(String input) {
        if (StringUtils.isNotBlank(input)) {
            try {
                SimpleDateFormat format = new SimpleDateFormat(PATTERN_1);
                Date ret = format.parse(input.trim());
                if (format.format(ret).equals(input.trim())) {
                    return true;
                }
            } catch (ParseException e) {
                return false;
            }
        }
        return false;
    }

    public static Date addDays(Date date, int days) {
        return new DateTime(date).plusDays(days).toDate();
    }


    public static Date getDate(Integer year,Integer month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,null != year ? year : calendar.get(Calendar.YEAR));
        calendar.set(Calendar.MONTH,null != month ? month-1 : calendar.get(Calendar.MONTH));
        return calendar.getTime();
    }

}
