package com.ismyself.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ismyself.Utils.StringUtils;
import com.ismyself.constant.MessageConstant;
import com.ismyself.constant.RedisMessageConstant;
import com.ismyself.entity.Result;
import com.ismyself.pojo.Member;
import com.ismyself.service.LoginService;
import com.ismyself.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * package com.ismyself.controller;
 *
 * @auther txw
 * @create 2019-08-03  14:13
 * @description：
 */
@RestController
@RequestMapping("/login")
public class LoginController {

    @Reference
    private LoginService loginService;

    @Reference
    private MemberService memberService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/check")
    public Result login(HttpServletResponse response, @RequestBody Map map){
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");

        if (StringUtils.isEmpty(validateCode) || StringUtils.isEmpty(telephone)){
            return new Result(false, MessageConstant.TELEPHONE_VALIDATECODE_NOTNULL);
        }
        String code = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_LOGIN);
        if (StringUtils.isEmpty(code) || !validateCode.equals(code)){
            return new Result(false,MessageConstant.VALIDATECODE_ERROR);
        }else {
            Member member = memberService.findMemberByTel(telephone);
            if (member == null){
                member = new Member();
                member.setPhoneNumber(telephone);
                member.setRegTime(new Date());
                memberService.saveMember(member);
            }
            //登录成功
            //写入Cookie，跟踪用户
            Cookie cookie = new Cookie("login_member_telephone",telephone);
            cookie.setPath("/");//路径
            cookie.setMaxAge(60*60*24*30);//有效期30天
            response.addCookie(cookie);

            return new Result(true,MessageConstant.LOGIN_SUCCESS);
        }
    }

}
