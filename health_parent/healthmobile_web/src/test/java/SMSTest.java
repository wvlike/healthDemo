import com.aliyuncs.exceptions.ClientException;
import com.ismyself.Utils.SMSUtils;

/**
 * package PACKAGE_NAME;
 *
 * @auther txw
 * @create 2019-08-04  9:14
 * @description：
 */
public class SMSTest {
    public static void main(String[] args) {
        try {
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, "16625157227", "123456");
            System.out.println("成功了吗");
        } catch (ClientException e) {
            e.printStackTrace();
        }

    }
}
