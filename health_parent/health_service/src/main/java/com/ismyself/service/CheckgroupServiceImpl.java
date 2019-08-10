package com.ismyself.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ismyself.dao.CheckgroupDao;
import com.ismyself.entity.PageResult;
import com.ismyself.entity.QueryPageBean;
import com.ismyself.pojo.CheckGroup;
import com.ismyself.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * package com.ismyself.service;
 *
 * @auther txw
 * @create 2019-07-28  20:00
 * @description：
 */
@Service(interfaceClass = CheckgroupService.class)
public class CheckgroupServiceImpl implements CheckgroupService {

    @Autowired
    private CheckgroupDao checkgroupDao;

    @Override
    public List<CheckItem> findCheckItemList() {
        return checkgroupDao.findCheckItemList();
    }

    /**
     * 添加到checkGroup与checkItem联系表
     *
     * @param checkGroupId
     * @param checkItemIds
     */
    public void setCheckItemAndCheckGroup(Integer checkGroupId, Integer[] checkItemIds) {
        if (checkItemIds != null && checkItemIds.length > 0) {
            for (Integer checkItemId : checkItemIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("checkGroupId", checkGroupId);
                map.put("checkItemId", checkItemId);
                checkgroupDao.setCheckItemAndCheckGroup(map);
            }
        }
    }

    /**
     * 添加到checkGroup
     *
     * @param checkGroup
     * @param ids
     */
    @Override
    @Transactional
    public void save(CheckGroup checkGroup, Integer[] ids) {
        checkgroupDao.save(checkGroup);
        Integer checkGroupId = checkGroup.getId();
        setCheckItemAndCheckGroup(checkGroupId, ids);
    }

    @Override
    public PageResult findList(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckGroup> page = checkgroupDao.findList(queryString);
        PageResult pageResult = new PageResult(page.getTotal(), page.getResult());
        return pageResult;
    }

    @Override
    @Transactional
    public void deleteById(Integer groupId) {
        //先删除关联
        checkgroupDao.deleteItemAndGroupById(groupId);
        //再删除Group表
        checkgroupDao.deleteById(groupId);
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkgroupDao.findById(id);
    }

    @Override
    public Integer[] findItemIdsById(Integer id) {
        return checkgroupDao.findItemIdsById(id);
    }

    @Override
    @Transactional
    public void update(CheckGroup checkGroup, Integer[] ids) {
        //更新Group表
        checkgroupDao.updateGroup(checkGroup);
        //删除原来的关联
        checkgroupDao.deleteItemAndGroupById(checkGroup.getId());
        //插入新的关联
        setCheckItemAndCheckGroup(checkGroup.getId(), ids);
    }
}
