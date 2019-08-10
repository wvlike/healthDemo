import org.junit.runner.RunWith;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * package PACKAGE_NAME;
 *
 * @auther txw
 * @create 2019-07-29  20:52
 * @description：
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "applicationContext-jobs.xml")
public class Test {

    @org.junit.Test
    public void test01(){
        new ClassPathXmlApplicationContext("applicationContext-jobs.xml");
    }
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("applicationContext-jobs.xml");
    }
}
