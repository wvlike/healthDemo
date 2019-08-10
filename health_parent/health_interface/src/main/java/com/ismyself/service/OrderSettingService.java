package com.ismyself.service;

import com.ismyself.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * package com.ismyself.service;
 *
 * @auther txw
 * @create 2019-07-31  17:42
 * @description：
 */
public interface OrderSettingService {
    void add(List<OrderSetting> orderSettingList);

    List<Map<String, Object>> getOrderSettingByMonth(String date);

    void addOrderSetting(OrderSetting orderSetting);
}
