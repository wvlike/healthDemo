package com.ismyself.dao;

import com.github.pagehelper.Page;
import com.ismyself.entity.QueryPageBean;
import com.ismyself.pojo.CheckItem;

import java.util.List;

/**
 * package com.ismyself.dao;
 *
 * @auther txw
 * @create 2019-07-27  10:11
 * @description：
 */
public interface CheckItemDao {

    void save(CheckItem checkItem);

//    List<CheckItem> findCheckItemList(QueryPageBean queryPageBean);

//    Long findTotalCount(QueryPageBean queryPageBean);

    void deleteById(Integer id);

    Page<CheckItem> findByQueryString(String queryString);

    int findCheckItemByItemAndGroup(Integer id);

    CheckItem findCheckItemById(Integer id);

    void update(CheckItem checkItem);

    CheckItem findItemByGroupId(Integer id);
}
