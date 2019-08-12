package com.ismyself.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ismyself.constant.MessageConstant;
import com.ismyself.dao.MenuDao;
import com.ismyself.dao.PermissionDao;
import com.ismyself.dao.RoleDao;
import com.ismyself.dao.UserDao;
import com.ismyself.entity.PageResult;
import com.ismyself.entity.Result;
import com.ismyself.pojo.Menu;
import com.ismyself.pojo.Permission;
import com.ismyself.pojo.Role;
import com.ismyself.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;

import java.util.HashMap;
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





    //分页查询用户列表信息
    @Override
    public Result findUser2Page(Integer currentPage, Integer pageSize, String queryString) {
        //PageHelper分页对象 调用静态方法startPage实现分页
        //传入两个参数 PageNum 当前页面 pageSize:每页显示记录数
        PageHelper.startPage(currentPage, pageSize);
        //紧跟着第二行就是需要分页的查询语句 select * from t_checkitem limit 0,10
        //pageHelper插件拦截查询表语句，动态为语句添加分页条件，查询分页数据
        Page<User> userList = userDao.selectByCondition(queryString);//返回结果的 插件定义Page<T>
        return new Result(true, MessageConstant.GET_USERLIST_SUCCESS,new PageResult(userList.getTotal(),userList.getResult()));
    }

    //添加用户
    @Override
    public void addUser(List<Integer> roleIds, User user) {
        //1 保存用户 返回自增主键
        userDao.addUser(user);
        //2 获取主键
        Integer uid = user.getId();
        //3 往中间表插入数据 绑定用户和角色多对多的关系
        setUserAndRole(roleIds, uid);
    }

    //删除用户
    @Override
    public void deleteUserById(Integer userId) {
        //1 删除用户角色表的关联信息
        userDao.deleteUserAndRoleByUid(userId);
        //2 删除用户信息
        userDao.deleteUserById(userId);
    }

    //根据ID查询用户信息
    @Override
    public User findUserById(Integer id) {
        return userDao.findUserById(id);
    }

    //根据用户ID查询用户角色列表
    @Override
    public List<Integer> findRoleIdsByUserId(Integer uid) {
        return userDao.findRoleIdsByUserId(uid);
    }

    //修改用户信息
    @Override
    public void editUser(List<Integer> ids, User user) {
        Integer uid = user.getId();
        //1 调用dao 更新用户
        userDao.editUser(user);
        //2 清空该用户中的所有关联角色
        userDao.deleteUserAndRoleByUid(uid);
        //3 如果ids不为空 那么就根据ids和用户的id进行关联表的关联数据的添加
        setUserAndRole(ids, uid);
    }

    //用户角色关联表数据的插入
    private void setUserAndRole(List<Integer> roleIds, Integer uid) {
        if (roleIds != null && roleIds.size() > 0) {
            //1 将两个数据封装到map中
            HashMap<String, Integer> map = new HashMap<>();
            for (Integer roleId : roleIds) {
                map.put("user_id", uid);
                map.put("role_id", roleId);
                //2 进行数据库信息插入操作
                userDao.setUserAndRole(map);
            }
        }
    }

}
