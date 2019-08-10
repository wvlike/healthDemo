package com.ismyself.mongodb;

import java.util.List;

/**
 * package com.ismyself.pojo;
 *
 * @auther txw
 * @create 2019-08-08  15:51
 * @description：
 */
public interface DbUserService {

    void save(Users user);

    void insert(Users user);

    void remove(Double _id);

    int update(Users user);

    List<Users> findByUid(Double _id);

    List<Users> findByUidAndTimestamp(Double _id, String name);

    List<Users> page(int page, Double _id);

    List<Users> findAll();
}
