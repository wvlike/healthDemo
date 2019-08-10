package com.ismyself.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ismyself.constant.MessageConstant;
import com.ismyself.constant.RedisConstant;
import com.ismyself.dao.SetmealDao;
import com.ismyself.entity.PageResult;
import com.ismyself.entity.QueryPageBean;
import com.ismyself.pojo.CheckGroup;
import com.ismyself.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * package com.ismyself.service;
 *
 * @auther txw
 * @create 2019-07-29  16:25
 * @description：
 */
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private JedisPool jedisPool;

    @Autowired
    private SetmealDao setmealDao;


    //将图片名称保存到Redis
    private void savePic2Redis(String pic) {
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, pic);
    }

    @Override
    public List<CheckGroup> findGroupList() {
        return setmealDao.findGroupList();
    }

    @Override
    public Setmeal findById(Integer id) {
        Setmeal setmeal = setmealDao.findSetmealById(id);
        return setmeal;
    }

    @Override
    public List<Integer> findGroupIdsById(Integer id) {
        return setmealDao.findGroupIdsById(id);
    }

    @Override
    @Transactional
    public void update(Setmeal setmeal, List<Integer> ids) {

        //先根据id过去原来的图片名称
        Setmeal oldSermeal = findById(setmeal.getId());
        String oldImg = oldSermeal.getImg();
        //删除redis的信息，防止为空
        try {
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_DB_RESOURCES, oldImg);
        } catch (Exception e) {
            throw new RuntimeException(MessageConstant.GET_IMAGE_ADDRESS_FAIL);
        }
        //添加现在redis的信息
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());
        //删除原来的setmeal and group信息
        setmealDao.deleteMealAndGroup(setmeal.getId());
        //更新setmeal信息
        setmealDao.update(setmeal);
        //添加新的setmeal and group信息
        setMealAndGroup(setmeal.getId(), ids);

    }

    @Override
    public List<Setmeal> findSetmealList() {
        return setmealDao.findSetmealList();
    }

    @Override
    public List<Map<String, Object>> findSetmealNamesAndCounts() {
        return setmealDao.findSetmealNamesAndCounts();
    }


    @Override
    @Transactional
    public void save(Setmeal setmeal, List<Integer> checkGroupIds) {
        setmealDao.save(setmeal);
        Integer setmealId = setmeal.getId();
/*        System.out.println(setmealId+"****************************************************************");
        System.out.println(checkGroupIds);*/
        setMealAndGroup(setmealId, checkGroupIds);
        if (setmeal.getImg() != null || !"".equals(setmeal.getImg())) {
            savePic2Redis(setmeal.getImg());
        }
    }

    public void setMealAndGroup(Integer setmealId, List<Integer> checkGroupIds) {
        if (checkGroupIds != null && checkGroupIds.size() > 0) {
            for (Integer checkGroupId : checkGroupIds) {
                Map<String, Integer> map = new HashMap<>();
                map.put("setmealId", setmealId);
                map.put("checkGroupId", checkGroupId);
                setmealDao.setMealAndGroup(map);
            }
        }
    }

    @Override
    public PageResult findMealList(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        PageHelper.startPage(currentPage, pageSize);
        Page<Setmeal> page = setmealDao.findSetmaelListAndTotal(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public String deleteById(Integer id) {

        Setmeal setmeal = setmealDao.findById(id);

        setmealDao.deleteMealAndGroup(id);

        setmealDao.deleteById(id);

        return setmeal.getImg();
    }


}
