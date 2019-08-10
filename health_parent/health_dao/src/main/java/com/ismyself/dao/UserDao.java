package com.ismyself.dao;

import com.ismyself.pojo.User;

/**
 * package com.ismyself.dao;
 *
 * @auther txw
 * @create 2019-08-04  19:24
 * @description：
 */
public interface UserDao {

    User findUserByUsername(String username);
}
