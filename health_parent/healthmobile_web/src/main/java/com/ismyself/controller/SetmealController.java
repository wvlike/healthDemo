package com.ismyself.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ismyself.Utils.StringUtils;
import com.ismyself.constant.MessageConstant;
import com.ismyself.constant.RedisConstant;
import com.ismyself.entity.Result;
import com.ismyself.pojo.CheckGroup;
import com.ismyself.pojo.Setmeal;
import com.ismyself.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * package com.ismyself.controller;
 *
 * @auther txw
 * @create 2019-08-01  17:12
 * @description：
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    //getSetmeal

    /**
     * 展示所有的套餐
     * @return
     */
    @RequestMapping("/getSetmeal")
    public Result getSetmeal(){
        try {
            List<Setmeal> setmealList = new ArrayList<>();
            String setmeal = jedisPool.getResource().get(RedisConstant.SETMEAL_List_DB_RESOURCES);
            Gson gson = new Gson();
            if (StringUtils.isEmpty(setmeal)){
                setmealList = setmealService.findSetmealList();
                String json = gson.toJson(setmealList);
                jedisPool.getResource().set(RedisConstant.SETMEAL_List_DB_RESOURCES,json);
            }else {
                setmealList = gson.fromJson(setmeal,new TypeToken <Collection <Setmeal>>(){}.getType());
            }
            return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS,setmealList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }
    /**
     * 通过id展示该套餐的信息
     */
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS,setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }



}
