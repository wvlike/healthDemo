package com.ismyself.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ismyself.Utils.DateUtils;
import com.ismyself.constant.MessageConstant;
import com.ismyself.dao.MemberDao;
import com.ismyself.dao.OrderDao;
import com.ismyself.dao.OrderSettingDao;
import com.ismyself.entity.Result;
import com.ismyself.pojo.Member;
import com.ismyself.pojo.Order;
import com.ismyself.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * package com.ismyself.service;
 *
 * @auther txw
 * @create 2019-08-02  15:20
 * @description：
 */
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private MemberDao memberDao;

    //    //idCard: "564654561315223"
    //    //name: "txw"
    //    //orderDate: "2019-08-04"
    //    //setmealId: "14"
    //    //sex: "1"
    //    //telephone: "15197251913"
    //    //validateCode: "1236"
    @Override
    public Result saveOrder(Map map) throws Exception {

        String orderDate = (String) map.get("orderDate");
        Date date = DateUtils.parseString2Date(orderDate);
        OrderSetting orderSetting = orderSettingDao.findOrderSettingByDate(date);
        if (orderSetting == null) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        int number = orderSetting.getNumber();
        int reservations = orderSetting.getReservations();
        if (number <= reservations) {
            return new Result(false, MessageConstant.ORDER_FULL);
        }
        String telephone = (String) map.get("telephone");
        Member member = memberDao.findMemberByTel(telephone);
        if (member != null) {
            Integer setmealId = Integer.valueOf((String) map.get("setmealId"));
            Order order = new Order(member.getId(), date, null, null,setmealId );

            //是否重复预约
            List<Order> orderList = orderDao.findByCondition(order);
            if (orderList.size() > 0 && orderList != null) {
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        } else {
            member = new Member();
            member.setName((String) map.get("name"));
            member.setSex((String) map.get("sex"));
            member.setIdCard((String) map.get("idCard"));
            member.setPhoneNumber(telephone);
            member.setRegTime(new Date());
            memberDao.saveMember(member);
        }


        //    private Integer memberId;//会员id
        //    private Date orderDate;//预约日期
        //    private String orderType;//预约类型 电话预约/微信预约
        //    private String orderStatus;//预约状态（是否到诊）
        //    private Integer setmealId;//体检套餐id

        //保存预约信息
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(date);
        order.setOrderType((String) map.get("orderType"));
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setSetmealId(Integer.parseInt((String) map.get("setmealId")));
        orderDao.saveOrder(order);

        //加一次该套餐的预约次数
        orderSetting.setReservations(orderSetting.getReservations() + 1);
        orderSettingDao.updateNumberByOrderDate(orderSetting);
        return new Result(true, MessageConstant.ORDER_SUCCESS, order);
    }

    //memberId
    @Override
    public Map findMessageById(Integer id) {
        return orderDao.findMessageById(id);
    }

}


