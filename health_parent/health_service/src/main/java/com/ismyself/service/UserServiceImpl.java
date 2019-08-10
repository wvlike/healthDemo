package com.ismyself.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.ismyself.dao.PermissionDao;
import com.ismyself.dao.RoleDao;
import com.ismyself.dao.UserDao;
import com.ismyself.pojo.Permission;
import com.ismyself.pojo.Role;
import com.ismyself.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;

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
}
