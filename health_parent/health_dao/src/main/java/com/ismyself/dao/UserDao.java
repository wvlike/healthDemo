package com.ismyself.dao;

import com.github.pagehelper.Page;
import com.ismyself.pojo.User;

import java.util.HashMap;
import java.util.List;

/**
 * package com.ismyself.dao;
 *
 * @auther txw
 * @create 2019-08-04  19:24
 * @description：
 */
public interface UserDao {

    User findUserByUsername(String username);

    Page<User> selectByCondition(String queryString);

    void addUser(User user);

    void deleteUserAndRoleByUid(Integer userId);

    void deleteUserById(Integer userId);

    User findUserById(Integer id);

    List<Integer> findRoleIdsByUserId(Integer uid);

    void editUser(User user);

    void setUserAndRole(HashMap<String, Integer> map);
}
