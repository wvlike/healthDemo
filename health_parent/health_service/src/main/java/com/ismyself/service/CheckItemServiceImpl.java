package com.ismyself.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ismyself.constant.MessageConstant;
import com.ismyself.dao.CheckItemDao;
import com.ismyself.entity.PageResult;
import com.ismyself.entity.QueryPageBean;
import com.ismyself.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * package com.ismyself.service;
 *
 * @auther txw
 * @create 2019-07-27  10:21
 * @description：
 */
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    @Transactional
    public void save(CheckItem checkItem) {
        checkItemDao.save(checkItem);
    }

    @Override
    public PageResult findCheckItemList(QueryPageBean queryPageBean) {

        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage,pageSize);
        Page<CheckItem> page = checkItemDao.findByQueryString(queryString);

        PageResult pageResult = new PageResult();
        pageResult.setTotal(page.getTotal());
        pageResult.setRows(page.getResult());

//        List<CheckItem> list = checkItemDao.findCheckItemList(queryPageBean);
//        pageResult.setRows(list);
//        Long totalCount = checkItemDao.findTotalCount(queryPageBean);
//        pageResult.setTotal(totalCount);

        return pageResult;
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        int count = checkItemDao.findCheckItemByItemAndGroup(id);
        if (count>0){
            throw new RuntimeException(MessageConstant.DELETE_CHECKITEM_EXIST);
        }
        checkItemDao.deleteById(id);
    }

    @Override
    public CheckItem findCheckItemById(Integer id) {
        return checkItemDao.findCheckItemById(id);
    }

    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }
}
