package com.ismyself.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ismyself.constant.MessageConstant;
import com.ismyself.dao.MenuDao;
import com.ismyself.entity.PageResult;
import com.ismyself.entity.QueryPageBean;
import com.ismyself.pojo.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * package com.ismyself.controller;
 *
 * @auther txw
 * @create 2019-08-09  15:28
 * @description：
 */
@Service(interfaceClass = MenuService.class)
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Override
    public PageResult findList(QueryPageBean queryPageBean) {

        Integer pageSize = queryPageBean.getPageSize();
        Integer currentPage = queryPageBean.getCurrentPage();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);
        Page<Menu> page = menuDao.findByQueryString(queryString);
        PageResult pageResult = new PageResult(page.getTotal(), page.getResult());

        return pageResult;
    }

    @Override
    public void save(Menu menu) {
        menuDao.save(menu);
    }

    @Override
    public void deleteById(Integer id) {
        Integer count = menuDao.findRoleAndMenuCountById(id);
        if (count >= 1){
            throw new RuntimeException(MessageConstant.DELETE_MENU_EXIST);
        }
        menuDao.deleteById(id);
    }

    @Override
    public Menu findById(Integer id) {
        return menuDao.findById(id);
    }

    @Override
    public void update(Menu menu) {
        menuDao.update(menu);
    }

    @Override
    public List<Menu> findAllList() {
        return menuDao.findAllList();
    }

    @Override
    public List<Integer> findIdsByRoleId(Integer rId) {
        return menuDao.findIdsByRoleId(rId);
    }
}
