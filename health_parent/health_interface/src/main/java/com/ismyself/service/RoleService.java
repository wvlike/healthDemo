package com.ismyself.service;

import com.ismyself.entity.PageResult;
import com.ismyself.entity.QueryPageBean;
import com.ismyself.pojo.Role;

import java.util.List;

/**
 * package com.ismyself.service;
 *
 * @auther txw
 * @create 2019-08-10  18:38
 * @description：
 */
public interface RoleService {
    PageResult findList(QueryPageBean queryPageBean);

    void save(Role role, List<Integer> pIds, List<Integer> mIds);

    void deleteById(Integer id);

    Role findById(Integer id);

    void update(Role role, List<Integer> pIds, List<Integer> mIds);
}
