package com.ismyself.service;

import com.ismyself.entity.PageResult;
import com.ismyself.entity.QueryPageBean;
import com.ismyself.pojo.CheckItem;

import java.util.List;

/**
 * package com.ismyself.service;
 *
 * @auther txw
 * @create 2019-07-27  10:20
 * @description：
 */
public interface CheckItemService {

    void save(CheckItem checkItem);

    PageResult findCheckItemList(QueryPageBean queryPageBean);

    void deleteById(Integer id);

    CheckItem findCheckItemById(Integer id);

    void update(CheckItem checkItem);
}
