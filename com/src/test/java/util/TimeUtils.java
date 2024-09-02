package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {
    public static long nd = 1000 * 24 * 60 * 60;
    public static long nh = 1000 * 60 * 60;
    public static long nm = 1000 * 60;

    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    /**
     * 得到当前是月的第几天
     * 
     * @return
     */
    public static int getCurrentDayOfMonth(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 得到当前是周的第几天
     * 
     * @return
     */
    public static int getCurrentDayOfWeek(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 得到一个月的最大日
     * 
     * @return
     */
    public static int getActualMaxDayOfMonth() {
        Calendar calendar = getCalendar();
        return calendar.getActualMaximum(Calendar.DATE);
    }

    /**
     * 加减几天
     * 
     * @param days
     * @return
     */
    public static Calendar addDay(int days) {
        Calendar calendar = getCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, days);
        return calendar;
    }

    /**
     * 获得下一个月的时间
     */
    public String nextMonthDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 1);
        String time = formatter.format(c.getTime());
        return time;
    }

    /**
     * 获得本月第一天
     */
    public String MonthFirstDay() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        String time = formatter.format(c.getTime());
        return time;
    }

    /**
     * 获得一个精确到毫秒的string，用来做时间戳
     */
    public static String getMillisecond() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSS");
        Calendar c = Calendar.getInstance();
        String time = formatter.format(c.getTime());
        return time;
    }

    /**
     * 根据时间戳获得一个手机号
     * 
     * @author lizhenwei
     * @date 2015年10月21日
     * @return
     */
    public static String getPhone() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("MMddHHmmss");
        Calendar c = Calendar.getInstance();
        String time = "1" + formatter.format(c.getTime());
        return time;
    }

    public static String getDatePoor(Date endDate, Date nowDate) {
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;
        // 计算差多少秒//输出结果
        // long sec = diff % nd % nh % nm / ns;
        return day + "天" + hour + "小时" + min + "分钟";
    }

    public static long getDateDifferentMin(Date endDate, Date nowDate) {
        // long ns = 1000;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少分钟
        long min = diff % nm / 60 / 60;
        return min;
    }

    public static void main(String args[]) {
        TimeUtils timeUtils = new TimeUtils();
        System.err.println(timeUtils.MonthFirstDay());
        System.err.println(timeUtils.nextMonthDate());
    }
}
