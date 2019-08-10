package com.ismyself.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ismyself.dao.OrderSettingDao;
import com.ismyself.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * package com.ismyself.service;
 *
 * @auther txw
 * @create 2019-07-31  17:42
 * @description：
 */
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    @Transactional
    public void add(List<OrderSetting> orderSettingList) {
        if (orderSettingList != null && orderSettingList.size() > 0) {
            for (OrderSetting orderSetting : orderSettingList) {
                saveOrUpdateOrderSetting(orderSetting);
            }
        }
    }

    private void saveOrUpdateOrderSetting(OrderSetting orderSetting) {
        long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        if (count > 0) {
            orderSettingDao.updateNumberByOrderDate(orderSetting);
        } else {
            orderSettingDao.add(orderSetting);
        }
    }

    @Override
    public List<Map<String, Object>> getOrderSettingByMonth(String date) {
        Map<String, String> map = getCurrentMonthFristAndLast(date);
        List<OrderSetting> orderSettingList = orderSettingDao.getOrderSettingByMonth(map);
        List<Map<String, Object>> list = new ArrayList<>();
        for (OrderSetting orderSetting : orderSettingList) {
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("date", orderSetting.getOrderDate().getDate());
            resultMap.put("number", orderSetting.getNumber());
            resultMap.put("reservations", orderSetting.getReservations());
            list.add(resultMap);
        }
        return list;
    }

    @Override
    @Transactional
    public void addOrderSetting(OrderSetting orderSetting) {
        saveOrUpdateOrderSetting(orderSetting);
    }

    private Map<String, String> getCurrentMonthFristAndLast(String date) {
        Map<String, String> map = new HashMap<>();
        map.put("fristDate", date + "-1");
        map.put("lastDate", date + "-31");
        return map;
    }
}
