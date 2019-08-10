package com.ismyself.service;

import com.ismyself.entity.PageResult;
import com.ismyself.entity.QueryPageBean;
import com.ismyself.pojo.CheckGroup;
import com.ismyself.pojo.CheckItem;

import java.util.List;

/**
 * package com.ismyself.service;
 *
 * @auther txw
 * @create 2019-07-28  19:59
 * @description：
 */
public interface CheckgroupService {
    List<CheckItem> findCheckItemList();

    void save(CheckGroup checkGroup, Integer[] ids);

    PageResult findList(QueryPageBean queryPageBean);

    void deleteById(Integer groupId);

    CheckGroup findById(Integer id);

    Integer[] findItemIdsById(Integer id);


    void update(CheckGroup checkGroup, Integer[] ids);
}
