package com.ismyself.controller;

import com.aliyuncs.exceptions.ClientException;
import com.ismyself.Utils.SMSUtils;
import com.ismyself.Utils.ValidateCodeUtils;
import com.ismyself.constant.MessageConstant;
import com.ismyself.constant.RedisMessageConstant;
import com.ismyself.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

/**
 * package com.ismyself.controller;
 *
 * @auther txw
 * @create 2019-08-02  10:35
 * @description：
 */
@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {

    @Autowired
    private JedisPool jedisPool;

    /**
     * 预约验证码
     */
    @RequestMapping("/send4Order")
    public Result send4Order(String telephone) {
        try {
            String code = String.valueOf(ValidateCodeUtils.generateValidateCode(4));
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, code);
            jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_ORDER, 5 * 60, code);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

    //send4Login

    /**
     * 登录验证码
     */
    @RequestMapping("/send4Login")
    public Result send4Login(String telephone) {
        //随机生成一个登录6位验证码
        try {
            String code = String.valueOf(ValidateCodeUtils.generateValidateCode(6));
            if (false){
                SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE, telephone, code);
            }
            jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_LOGIN, 5 * 60, code);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }


}
