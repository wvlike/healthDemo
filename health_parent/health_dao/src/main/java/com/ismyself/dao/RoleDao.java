package com.ismyself.dao;

import com.github.pagehelper.Page;
import com.ismyself.pojo.Role;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * package com.ismyself.dao;
 *
 * @auther txw
 * @create 2019-08-04  19:27
 * @description：
 */
public interface RoleDao {

    Set<Role> findByUid(Integer id);

    Page<Role> findByQueryString(String queryString);

    void save(Role role);
    void setRoleAndMenu(Map<String, Object> map);

    void setRoleAndPermission(Map<String, Object> map);

    void deleteRoleAndMenuById(Integer id);

    void deleteRoleAndPermissionById(Integer id);

    void deleteById(Integer id);

    Role findById(Integer id);

    void update(Role role);

    List<Role> findAllRole();
}
