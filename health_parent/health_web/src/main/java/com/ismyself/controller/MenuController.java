package com.ismyself.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ismyself.constant.MessageConstant;
import com.ismyself.entity.PageResult;
import com.ismyself.entity.QueryPageBean;
import com.ismyself.entity.Result;
import com.ismyself.pojo.Menu;
import com.ismyself.service.MenuService;
import org.apache.xmlbeans.impl.jam.mutable.MElement;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * package com.ismyself.controller;
 *
 * @auther txw
 * @create 2019-08-09  15:29
 * @description：
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Reference
    private MenuService menuService;

    //findList
    @RequestMapping("/findPageList")
    private Result findList(@RequestBody QueryPageBean queryPageBean) {

        try {
            PageResult pageResult = menuService.findList(queryPageBean);
            return new Result(true, MessageConstant.QUERY_MENU_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_MENU_FAIL);
        }
    }

    //save
    @RequestMapping("/save")
    private Result save(@RequestBody Menu menu) {
        try {
            menuService.save(menu);
            return new Result(true, MessageConstant.ADD_MENU_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_MENU_FAIL);
        }
    }

    //deleteById
    @RequestMapping("/deleteById")
    private Result deleteById(Integer id) {
        try {
            menuService.deleteById(id);
            return new Result(true, MessageConstant.DELETE_MENU_SUCCESS);
        } catch (RuntimeException er) {
            return new Result(false, MessageConstant.DELETE_MENU_EXIST);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_MENU_FAIL);
        }
    }

    //findById
    @RequestMapping("/findById")
    private Result findById(Integer id) {
        try {
            Menu menu = menuService.findById(id);
            return new Result(true, MessageConstant.QUERY_MENU_SUCCESS, menu);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_MENU_FAIL);
        }
    }

    //update
    @RequestMapping("/update")
    private Result update(@RequestBody Menu menu) {
        try {
            menuService.update(menu);
            return new Result(true, MessageConstant.EDIT_MENU_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_MENU_FAIL);
        }
    }

    //findAllList
    @RequestMapping("/findAllList")
    private Result findAllList() {
        try {
            List<Menu> list = menuService.findAllList();
            return new Result(true, MessageConstant.QUERY_MENU_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_MENU_FAIL);
        }
    }

    //findIdsByRoleId
    @RequestMapping("/findIdsByRoleId")
    private Result findIdsByRoleId(Integer rId) {
        try {
            List<Integer> list = menuService.findIdsByRoleId(rId);
            return new Result(true, MessageConstant.QUERY_MENU_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_MENU_FAIL);
        }
    }
}
