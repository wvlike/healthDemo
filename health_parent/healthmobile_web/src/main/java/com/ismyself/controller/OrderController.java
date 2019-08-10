package com.ismyself.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ismyself.Utils.SMSUtils;
import com.ismyself.Utils.StringUtils;
import com.ismyself.constant.MessageConstant;
import com.ismyself.constant.RedisMessageConstant;
import com.ismyself.entity.Result;
import com.ismyself.pojo.Order;
import com.ismyself.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

/**
 * package com.ismyself.controller;
 *
 * @auther txw
 * @create 2019-08-02  15:16
 * @description：
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private OrderService orderService;

    //idCard: "564654561315223"
    //name: "txw"
    //orderDate: "2019-08-04"
    //setmealId: "14"
    //sex: "1"
    //telephone: "15197251913"
    //validateCode: "1236"

    /**
     * @param map
     * @return
     */
    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map) {
        //判断是否存在该验证码
        String telephone = (String) map.get("telephone");
        String validateCode = (String) map.get("validateCode");
        String redisCode = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        Result result;
        if (!StringUtils.isEmpty(validateCode) && !StringUtils.isEmpty(redisCode) && redisCode.equals(validateCode)) {
            try {
                map.put("orderType", Order.ORDERTYPE_WEIXIN);
                result = orderService.saveOrder(map);
                if (result.isFlag()){
                    String orderDate = (String) map.get("orderDate");
                    SMSUtils.sendShortMessage(SMSUtils.ORDER_NOTICE,telephone,orderDate);
                }
            } catch (Exception e) {
                e.printStackTrace();
                return new Result(false, MessageConstant.ORDERSETTING_FAIL);
            }
        } else {
            return new Result(false, MessageConstant.VALIDATECODE_ERROR);
        }
        return result;
    }

    //findById
    @RequestMapping("/findById")
    public Result findById(Integer id) {
        try {
            Map map = orderService.findMessageById(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);

        }
    }

}
