package com.ismyself.dao;

import com.github.pagehelper.Page;
import com.ismyself.pojo.CheckGroup;
import com.ismyself.pojo.CheckItem;
import com.ismyself.pojo.Setmeal;

import java.util.List;
import java.util.Map;

/**
 * package com.ismyself.dao;
 *
 * @auther txw
 * @create 2019-07-27  10:11
 * @description：
 */
public interface SetmealDao {

    void save(Setmeal setmeal);

    void setMealAndGroup(Map<String, Integer> map);

    Page<Setmeal> findSetmaelListAndTotal(String queryString);

    void deleteMealAndGroup(Integer id);

    void deleteById(Integer id);

    Setmeal findById(Integer id);

    List<CheckGroup> findGroupList();

    List<Integer> findGroupIdsById(Integer id);

    void update(Setmeal setmeal);

    List<Setmeal> findSetmealList();

    Setmeal findSetmealById(Integer id);

    List<Map<String, Object>> findSetmealNamesAndCounts();
}
