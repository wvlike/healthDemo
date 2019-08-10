package com.ismyself.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ismyself.constant.MessageConstant;
import com.ismyself.dao.PermissionDao;
import com.ismyself.entity.PageResult;
import com.ismyself.entity.QueryPageBean;
import com.ismyself.pojo.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * package com.ismyself.service;
 *
 * @auther txw
 * @create 2019-08-10  18:48
 * @description：
 */
@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Override
    public PageResult findList(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<Permission> page = permissionDao.findByQueryString(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void save(Permission permission) {
        permissionDao.save(permission);
    }

    @Override
    public void deleteById(Integer id) {
        Integer count = permissionDao.findRAPCountById(id);
        if (count >= 1 ){
            throw new RuntimeException(MessageConstant.DELETE_PERMISSION_EXIST);
        }
        permissionDao.deleteById(id);
    }

    @Override
    public Permission findById(Integer id) {
        return permissionDao.findById(id);
    }

    @Override
    public void update(Permission permission) {
        permissionDao.update(permission);
    }

    @Override
    public List<Permission> findAllList() {
        return permissionDao.findAllList();
    }

    @Override
    public List<Integer> findIdsByRoleId(Integer rId) {
        return permissionDao.findIdsByRoleId(rId);
    }
}
