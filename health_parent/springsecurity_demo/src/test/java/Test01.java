import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * package PACKAGE_NAME;
 *
 * @auther txw
 * @create 2019-08-04  17:18
 * @description：
 */
public class Test01 {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));
        System.out.println(encoder.matches("123456","$2a$10$JDWc7LhPtsPdcQ.CbluG7etY5lqxw4Cfe1Y2mdlrChY0Gpzky8EQ6"));
    }
}
