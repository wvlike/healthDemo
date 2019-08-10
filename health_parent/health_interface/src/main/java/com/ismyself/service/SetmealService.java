package com.ismyself.service;

import com.ismyself.entity.PageResult;
import com.ismyself.entity.QueryPageBean;
import com.ismyself.pojo.CheckGroup;
import com.ismyself.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * package com.ismyself.service;
 *
 * @auther txw
 * @create 2019-07-29  16:24
 * @description：
 */
public interface SetmealService {
    void save(Setmeal setmeal, List<Integer> ids);

    PageResult findMealList(QueryPageBean queryPageBean);

    String deleteById(Integer id);

    List<CheckGroup> findGroupList();

    Setmeal findById(Integer id);

    List<Integer> findGroupIdsById(Integer id);

    void update(Setmeal setmeal, List<Integer> ids);

    List<Setmeal> findSetmealList();

    List<Map<String, Object>> findSetmealNamesAndCounts();
}
