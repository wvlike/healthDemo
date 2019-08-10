package com.ismyself.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ismyself.dao.MenuDao;
import com.ismyself.dao.PermissionDao;
import com.ismyself.dao.RoleDao;
import com.ismyself.entity.PageResult;
import com.ismyself.entity.QueryPageBean;
import com.ismyself.pojo.Menu;
import com.ismyself.pojo.Permission;
import com.ismyself.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * package com.ismyself.service;
 *
 * @auther txw
 * @create 2019-08-10  18:38
 * @description：
 */
@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService {


    @Autowired
    private RoleDao roleDao;

    @Autowired
    private MenuDao menuDao;

    @Autowired
    private PermissionDao permissionDao;


    @Override
    public PageResult findList(QueryPageBean queryPageBean) {
        Integer pageSize = queryPageBean.getPageSize();
        Integer currentPage = queryPageBean.getCurrentPage();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);
        Page<Role> page = roleDao.findByQueryString(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void save(Role role, List<Integer> pIds, List<Integer> mIds) {
        roleDao.save(role);
        Integer roleId = role.getId();
        System.out.println(roleId + "*****************************************************");
        setRoleAndMenu(roleId, mIds);
        setRoleAndPermission(roleId, pIds);
    }

    private void setRoleAndMenu(Integer roleId, List<Integer> mIds) {
        if (mIds != null && mIds.size() > 0) {
            for (Integer mId : mIds) {
                Map<String, Object> map = new HashMap<>();
                map.put("roleId", roleId);
                map.put("menuId", mId);
                roleDao.setRoleAndMenu(map);
            }
        }
    }

    private void setRoleAndPermission(Integer roleId, List<Integer> pIds) {
        if (pIds != null && pIds.size() > 0) {
            for (Integer pId : pIds) {
                Map<String, Object> map = new HashMap<>();
                map.put("roleId", roleId);
                map.put("permissionId", pId);
                roleDao.setRoleAndPermission(map);
            }
        }
    }


    @Override
    public void deleteById(Integer id) {
        roleDao.deleteRoleAndMenuById(id);
        roleDao.deleteRoleAndPermissionById(id);
        roleDao.deleteById(id);
    }

    @Override
    public Role findById(Integer id) {
        Role role = roleDao.findById(id);
        return role;
    }

    @Override
    public void update(Role role, List<Integer> pIds, List<Integer> mIds) {
        Integer roleId = role.getId();
        roleDao.deleteRoleAndMenuById(roleId);
        roleDao.deleteRoleAndPermissionById(roleId);
        setRoleAndMenu(roleId, mIds);
        setRoleAndPermission(roleId, pIds);
        roleDao.update(role);
    }
}
