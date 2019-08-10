import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * package PACKAGE_NAME;
 *
 * @auther txw
 * @create 2019-07-31  20:46
 * @description：
 */
public class TimeTest {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = format.parse("2019-2-28");
        System.out.println(parse);

    }

    @Test
    public void test01(){
        //正数的[精度]precision、[非标度值]unscale、[标度]scale
//0 precision=1,unscale=0,scale=0
        BigDecimal tmp = new BigDecimal("0");
        System.out.println( "0 precision=" + tmp.precision() +",unscale=" + tmp.unscaledValue()+ ",scale=" + tmp.scale() );

        tmp = new BigDecimal("0.0");
//0.0 precision=1,unscale=0,scale=1
        System.out.println( "0.0 precision=" + tmp.precision() +",unscale=" + tmp.unscaledValue()+ ",scale=" + tmp.scale());

        tmp = new BigDecimal("1");
//1 precision=1,unscale=1,scale=0
        System.out.println( "1 precision=" + tmp.precision() +",unscale=" + tmp.unscaledValue()+ ",scale=" + tmp.scale());

        tmp = new BigDecimal("1.0");
//1.0 precision=2,unscale=10,scale=1
        System.out.println( "1.0 precision=" + tmp.precision() +",unscale=" + tmp.unscaledValue()+ ",scale=" + tmp.scale());

        tmp = new BigDecimal("0.1");
//0.1 precision=1,unscale=1,scale=1
        System.out.println( "0.1 precision=" + tmp.precision() +",unscale=" + tmp.unscaledValue()+ ",scale=" + tmp.scale());

        tmp = new BigDecimal("122.000");
//122.000 precision=6,unscale=122000,scale=3
        System.out.println( "122.000 precision=" + tmp.precision() +",unscale=" + tmp.unscaledValue()+ ",scale=" + tmp.scale());


//----------负数的[精度]precision、[非标度值]unscale、[标度]scale
//-0 precision=1,unscale=0,scale=0
        tmp = new BigDecimal("-0");
        System.out.println( "-0 precision=" + tmp.precision() +",unscale=" + tmp.unscaledValue()+ ",scale=" + tmp.scale() );

        tmp = new BigDecimal("-0.0");
//-0.0 precision=1,unscale=0,scale=1
        System.out.println( "-0.0 precision=" + tmp.precision() +",unscale=" + tmp.unscaledValue()+ ",scale=" + tmp.scale());

        tmp = new BigDecimal("-1");
//-1 precision=1,unscale=-1,scale=0
        System.out.println( "-1 precision=" + tmp.precision() +",unscale=" + tmp.unscaledValue()+ ",scale=" + tmp.scale());

        tmp = new BigDecimal("-1.0");
//-1.0 precision=2,unscale=-10,scale=1
        System.out.println( "-1.0 precision=" + tmp.precision() +",unscale=" + tmp.unscaledValue()+ ",scale=" + tmp.scale());

        tmp = new BigDecimal("-0.1");
//-0.1 precision=1,unscale=-1,scale=1
        System.out.println( "-0.1 precision=" + tmp.precision() +",unscale=" + tmp.unscaledValue()+ ",scale=" + tmp.scale());

        tmp = new BigDecimal("-122.000");
//-122.000 precision=6,unscale=-122000,scale=3
        System.out.println( "-122.000 precision=" + tmp.precision() +",unscale=" + tmp.unscaledValue()+ ",scale=" + tmp.scale());

    }
}
