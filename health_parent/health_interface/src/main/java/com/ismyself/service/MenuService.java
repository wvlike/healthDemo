package com.ismyself.service;

import com.ismyself.entity.PageResult;
import com.ismyself.entity.QueryPageBean;
import com.ismyself.pojo.Menu;

import java.util.List;

/**
 * package com.ismyself.service;
 *
 * @auther txw
 * @create 2019-08-09  15:28
 * @description：
 */
public interface MenuService {
    PageResult findList(QueryPageBean queryPageBean);

    void save(Menu menu);

    void deleteById(Integer id);

    Menu findById(Integer id);

    void update(Menu menu);

    List<Menu> findAllList();

    List<Integer> findIdsByRoleId(Integer rId);
}
