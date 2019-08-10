package com.ismyself.dao;

import com.github.pagehelper.Page;
import com.ismyself.pojo.CheckGroup;
import com.ismyself.pojo.CheckItem;

import java.util.List;
import java.util.Map;

/**
 * package com.ismyself.dao;
 *
 * @auther txw
 * @create 2019-07-28  19:59
 * @description：
 */
public interface CheckgroupDao {
    List<CheckItem> findCheckItemList();

    void save(CheckGroup checkGroup);

    void setCheckItemAndCheckGroup(Map<String, Integer> map);

    Page<CheckGroup> findList(String queryString);

    void deleteItemAndGroupById(Integer groupId);

    void deleteById(Integer groupId);

    CheckGroup findById(Integer id);

    Integer[] findItemIdsById(Integer id);

    void updateGroup(CheckGroup checkGroup);

    CheckGroup findGroupBySetmealId(Integer id);
}
