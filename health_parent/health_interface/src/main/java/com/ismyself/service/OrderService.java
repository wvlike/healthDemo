package com.ismyself.service;

import com.ismyself.entity.Result;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * package com.ismyself.service;
 *
 * @auther txw
 * @create 2019-08-02  15:19
 * @description：
 */
public interface OrderService {

    Result saveOrder(Map map) throws Exception;

    Map findMessageById(Integer id);

}
