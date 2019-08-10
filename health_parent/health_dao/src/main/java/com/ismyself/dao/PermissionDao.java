package com.ismyself.dao;

import com.github.pagehelper.Page;
import com.ismyself.entity.PageResult;
import com.ismyself.entity.QueryPageBean;
import com.ismyself.pojo.Permission;

import java.util.List;
import java.util.Set;

/**
 * package com.ismyself.dao;
 *
 * @auther txw
 * @create 2019-08-04  19:26
 * @description：
 */
public interface PermissionDao {
    Set<Permission> findByRoleId(Integer roleId);

    Page<Permission> findByQueryString(String queryString);

    void save(Permission permission);

    Integer findRAPCountById(Integer id);

    void deleteById(Integer id);

    Permission findById(Integer id);

    void update(Permission permission);

    List<Permission> findAllList();

    Set<Permission> findPermissionsByRid(Integer id);

    List<Integer> findIdsByRoleId(Integer rId);
}
