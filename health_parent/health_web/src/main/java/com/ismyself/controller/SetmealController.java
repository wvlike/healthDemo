package com.ismyself.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.ismyself.Utils.QiniuUtils;
import com.ismyself.constant.MessageConstant;
import com.ismyself.constant.RedisConstant;
import com.ismyself.entity.PageResult;
import com.ismyself.entity.QueryPageBean;
import com.ismyself.entity.Result;
import com.ismyself.pojo.CheckGroup;
import com.ismyself.pojo.Setmeal;
import com.ismyself.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;


import java.util.List;
import java.util.UUID;

/**
 * package com.ismyself.controller;
 *
 * @auther txw
 * @create 2019-07-29  16:26
 * @description：
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private JedisPool jedisPool;

    @Reference
    private SetmealService setmealService;

    //将图片名称从Redis删除
    private void delPic2Redis(String pic) {
        jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_DB_RESOURCES, pic);
    }

    //save
    @RequestMapping("/save")
    @PreAuthorize("hasAnyAuthority('SETMEAL_ADD')")
    public Result save(@RequestBody Setmeal setmeal, @RequestParam List<Integer> ids) {
        try {
            setmealService.save(setmeal, ids);
            updateRedis();
            return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
    }

    //findGroupList
    @RequestMapping("/findGroupList")
    @PreAuthorize("hasAnyAuthority('SETMEAL_QUERY')")
    public Result findGroupList() {
        try {
            List<CheckGroup> list = setmealService.findGroupList();
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    //upload
    @RequestMapping("/upload")
    @PreAuthorize("hasAnyAuthority('SETMEAL_QUERY')")
    public Result upload(@RequestParam MultipartFile imgFile) {
        try {
            String filename = imgFile.getOriginalFilename();
            int indexDot = filename.indexOf(".");
            String footer = filename.substring(indexDot);
            String newName = UUID.randomUUID().toString().replace("-", "") + footer;
            QiniuUtils.upload2Qiniu(imgFile.getBytes(), newName);

            jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES, newName);

            return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, newName);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
    }

    //findList
    @RequestMapping(value = "/findList", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('SETMEAL_QUERY')")
    public Result findList(QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = setmealService.findMealList(queryPageBean);
            return new Result(true, MessageConstant.QUERY_SETMEALLIST_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEALLIST_FAIL);
        }
    }

    //deleteById
    @RequestMapping("/deleteById")
    @PreAuthorize("hasAnyAuthority('SETMEAL_DELETE')")
    public Result deleteById(Integer id) {
        try {
            String picImg = setmealService.deleteById(id);
            delPic2Redis(picImg);
            updateRedis();
            return new Result(true, MessageConstant.DELETE_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_SETMEAL_FAIL);
        }
    }

    //findById
    @RequestMapping("/findById")
    @PreAuthorize("hasAnyAuthority('SETMEAL_QUERY')")
    public Result findById(Integer id) {
        try {
            Setmeal setmeal = setmealService.findById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, setmeal);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    //findGroupIdsById
    @RequestMapping("/findGroupIdsById")
    @PreAuthorize("hasAnyAuthority('SETMEAL_QUERY')")
    public Result findGroupIdsById(Integer id) {
        try {
            List<Integer> list = setmealService.findGroupIdsById(id);
            return new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_SETMEAL_FAIL);
        }
    }

    //update
    @RequestMapping("/update")
    @PreAuthorize("hasAnyAuthority('SETMEAL_EDIT')")
    public Result update(@RequestBody Setmeal setmeal,@RequestParam List<Integer> ids) {
        try {
            setmealService.update(setmeal,ids);
            updateRedis();
            return new Result(true, MessageConstant.EDIT_SETMEAL_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_SETMEAL_FAIL);
        }
    }

    private void updateRedis(){
        List<Setmeal> setmealList = setmealService.findSetmealList();
        jedisPool.getResource().set(RedisConstant.SETMEAL_List_DB_RESOURCES,new Gson().toJson(setmealList));
    }
}
