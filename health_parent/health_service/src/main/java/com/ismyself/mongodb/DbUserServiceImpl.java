package com.ismyself.mongodb;

import com.alibaba.dubbo.config.annotation.Service;
import com.ismyself.mongodb.DbUserService;
import com.ismyself.mongodb.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * package com.ismyself.pojo;
 *
 * @auther txw
 * @create 2019-08-08  15:52
 * @description：
 */
@Service(interfaceClass = DbUserService.class)
@Transactional
public class DbUserServiceImpl implements DbUserService {

    @Autowired
    private MongoOperations mongoOperations;

    @Override
    public void save(Users user) {
        mongoOperations.save(user);
    }

    @Override
    public void insert(Users user) {
        mongoOperations.insert(user);
    }

    @Override
    public void remove(Double _id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(_id));
        mongoOperations.remove(query, Users.class);
    }

    @Override
    public int update(Users user) {
        return 0;
    }

    @Override
    public List<Users> findByUid(Double _id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(_id));
        return mongoOperations.find(query,Users.class);
    }

    @Override
    public List<Users> findByUidAndTimestamp(Double _id, String name) {
        return null;
    }

    @Override
    public List<Users> page(int page, Double _id) {
        return null;
    }

    @Override
    public List<Users> findAll() {
        Query query = new Query();
        return mongoOperations.find(query,Users.class);

    }


}
