package com.ismyself.dao;

import com.ismyself.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * package com.ismyself.dao;
 *
 * @auther txw
 * @create 2019-07-31  17:41
 * @description：
 */
public interface OrderSettingDao {

    long findCountByOrderDate(Date orderDate);

    void add(OrderSetting orderSetting);

    void updateNumberByOrderDate(OrderSetting orderSetting);

    List<OrderSetting> getOrderSettingByMonth(Map<String, String> map);

    OrderSetting findOrderSettingByDate(Date date);

    void deleteBeforeDate(String date);
}
