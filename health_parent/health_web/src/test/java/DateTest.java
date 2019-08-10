import com.ismyself.Utils.DateUtils;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * package PACKAGE_NAME;
 *
 * @auther txw
 * @create 2019-08-07  11:04
 * @description：
 */
public class DateTest {
    public static void main(String[] args) {

        String firstDay2, lastDay2;
        Calendar curStartCal = Calendar.getInstance();
        Calendar cal2 = (Calendar) curStartCal.clone();
        cal2.set(Calendar.DAY_OF_MONTH, cal2.getActualMinimum(Calendar.DAY_OF_MONTH));
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        firstDay2 = format.format(cal2.getTime());
        cal2.set(Calendar.DAY_OF_MONTH, cal2.getActualMaximum(Calendar.DAY_OF_MONTH));
        lastDay2 = format.format(cal2.getTime());
        System.out.println("本月第一天和最后一天分别是:" + firstDay2 + " and " + lastDay2);

        System.out.println("----------------------------");
        String firstDay, lastDay, curWeek;
        Calendar curStartCal2 = Calendar.getInstance();
        Calendar cal = (Calendar) curStartCal2.clone();
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat dfWeek = new SimpleDateFormat("(第ww周)");
        firstDay = df.format(cal.getTime());
        curWeek = dfWeek.format(cal.getTime());
        cal.add(Calendar.DATE, 6);
        lastDay = df.format(cal.getTime());
        System.out.println("本周周一和周末:" + firstDay + " and " + lastDay + " and "+ curWeek);

    }


    @Test
    public void test01(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(format.format(DateUtils.getThisWeekSunday()));
        System.out.println(format.format(DateUtils.getThisWeekMonday()));
        System.out.println(format.format(DateUtils.getFirstDay4ThisMonth()));
        System.out.println(format.format(DateUtils.getLastDay4ThisMonth()));

    }
}
