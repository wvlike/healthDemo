package com.ismyself.service;

import com.ismyself.entity.Result;
import com.ismyself.pojo.Menu;
import com.ismyself.pojo.User;

import java.util.List;

/**
 * package com.ismyself.service;
 *
 * @auther txw
 * @create 2019-08-04  19:08
 * @description：
 */
public interface UserService {
    User findUserByUname(String username);

    List<Menu> findUserMenuByUsername(String username);

    List<Integer> findRoleIdsByUserId(Integer uid);

    User findUserById(Integer id);

    void addUser(List<Integer> roleIds, User user);

    Result findUser2Page(Integer currentPage, Integer pageSize, String queryString);

    void deleteUserById(Integer userId);

    void editUser(List<Integer> ids, User user);
}
