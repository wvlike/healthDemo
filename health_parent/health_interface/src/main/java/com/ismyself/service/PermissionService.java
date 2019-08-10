package com.ismyself.service;

import com.ismyself.entity.PageResult;
import com.ismyself.entity.QueryPageBean;
import com.ismyself.pojo.Permission;

import java.util.List;

/**
 * package com.ismyself.service;
 *
 * @auther txw
 * @create 2019-08-10  18:47
 * @description：
 */
public interface PermissionService {
    PageResult findList(QueryPageBean queryPageBean);

    void save(Permission permission);

    void deleteById(Integer id);

    Permission findById(Integer id);

    void update(Permission permission);

    List<Permission> findAllList();

    List<Integer> findIdsByRoleId(Integer rId);
}
