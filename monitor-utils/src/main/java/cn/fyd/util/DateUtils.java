package cn.fyd.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static cn.fyd.common.Constant.*;

/**
 * 时间格式转换工具类
 * @author fanyidong
 * @date Created in 2018-12-14
 */
public class DateUtils {

    /**
     * Date类型转换为String类型
     * @param date
     * @param format
     * @return
     */
    public static String dateToString(Date date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * String类型转化为date
     * @param time
     * @param format
     * @return
     */
    public static Date stringToDate(String time, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(time);
    }

    /**
     * Date类型转化为Calendar
     * @param date
     * @return
     */
    public static Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * 判断日期是否属于过去十二个月
     * @param param date类型的日期
     * @return boolean
     */
    public static boolean isBelongLastTwelveMonths(Date param) {
        // 获取当前时间
        Calendar today = Calendar.getInstance();
        // 获取当前时间的年份和月份
        int todayYear = today.get(Calendar.YEAR);
        int todayMonth = today.get(Calendar.MONTH);
        // 获取需要比较的时间
        Calendar paramDay = dateToCalendar(param);
        // 获取需要比较时间的年月
        int paramYear = paramDay.get(Calendar.YEAR);
        int paramMonth = paramDay.get(Calendar.MONTH);

        if (todayYear == paramYear) {
            // 如果传入参数属于是今年的，肯定属于过去十二个月
            return true;
        } else {
            // 如果年份不相等，仅当今年年份-传入参数年份=1时，属于过去十二个月
            if (todayYear-paramYear!=1) {
                return false;
            } else {
                // 13减去当前月份是属于过去十二月的最小月份，如果小于等于传入参数的月份，则返回true
                if ((NINE+FOUR-todayMonth) <= paramMonth) {
                    return true;
                }
            }
        }
        return false;
    }
}
