package com.ismyself.dao;

import com.github.pagehelper.Page;
import com.ismyself.pojo.Menu;

import java.util.LinkedHashSet;
import java.util.List;

/**
 * package com.ismyself.dao;
 *
 * @auther txw
 * @create 2019-08-09  15:27
 * @description：
 */
public interface MenuDao {
    Page<Menu> findByQueryString(String queryString);

    void save(Menu menu);

    Integer findRoleAndMenuCountById(Integer id);

    void deleteById(Integer id);

    Menu findById(Integer id);

    void update(Menu menu);

    List<Menu> findAllList();

    LinkedHashSet<Menu> findMenusByRid(Integer id);

    List<Integer> findIdsByRoleId(Integer rId);
}
