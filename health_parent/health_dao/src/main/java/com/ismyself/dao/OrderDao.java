package com.ismyself.dao;

import com.ismyself.pojo.Member;
import com.ismyself.pojo.Order;
import com.ismyself.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * package com.ismyself.dao;
 *
 * @auther txw
 * @create 2019-08-02  15:19
 * @description：
 */
public interface OrderDao {

    List<Order> findByCondition(Order order);

    void saveOrder(Order order);

    Map findMessageById(Integer id);

    Integer findTodayOrderNumber(String date);

    Integer findTodayVisitsNumber(String date);

    Integer findOrderCountAfterDate(String thisMonthFirstDay);

    Integer findVisitsCountAfterDate(String thisMonthFirstDay);

    List<Map<String, Object>> findHotOrderSetmeal();
}
