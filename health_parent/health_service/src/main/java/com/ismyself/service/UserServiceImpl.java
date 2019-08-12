package com.ismyself.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ismyself.dao.MenuDao;
import com.ismyself.dao.PermissionDao;
import com.ismyself.dao.RoleDao;
import com.ismyself.dao.UserDao;
import com.ismyself.pojo.Menu;
import com.ismyself.pojo.Permission;
import com.ismyself.pojo.Role;
import com.ismyself.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Set;

/**
 * package com.ismyself.service;
 *
 * @auther txw
 * @create 2019-08-04  19:08
 * @description：
 */
@Service(interfaceClass = UserService.class)
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private MenuDao menuDao;

    @Override
    public User findUserByUname(String username) {
        User user = userDao.findUserByUsername(username);
        if (user != null) {
            Set<Role> roles = roleDao.findByUid(user.getId());
            if (roles != null && roles.size() > 0) {
                for (Role role : roles) {
                    Integer roleId = role.getId();
                    Set<Permission> permissions = permissionDao.findByRoleId(roleId);
                    role.setPermissions(permissions);
                }
                user.setRoles(roles);
            }
        }
        return user;
    }

    @Override
    public List<Menu> findUserMenuByUsername(String username) {
        List<Menu> list = menuDao.findMenuByUsername(username);
        if (list.size() > 0 && list != null) {
            for (Menu menu : list) {
                Integer parentMenuId = menu.getId();
                List<Menu> childList = menuDao.findListByParentId(parentMenuId);
                menu.setChildren(childList);
            }
        }
        return list;
    }
}
