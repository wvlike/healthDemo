package com.ismyself.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ismyself.constant.MessageConstant;
import com.ismyself.entity.PageResult;
import com.ismyself.entity.QueryPageBean;
import com.ismyself.entity.Result;
import com.ismyself.pojo.Role;
import com.ismyself.service.RoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * package com.ismyself.controller;
 *
 * @auther txw
 * @create 2019-08-10  18:39
 * @description：
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Reference
    private RoleService roleService;


    //findList
    @RequestMapping("/findPageList")
    private Result findList(@RequestBody QueryPageBean queryPageBean) {

        try {
            PageResult pageResult = roleService.findList(queryPageBean);
            return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ROLE_FAIL);
        }
    }

    //save
    @RequestMapping("/save")
    private Result save(@RequestBody Role role, @RequestParam List<Integer> pIds,@RequestParam List<Integer> mIds) {
        try {
            roleService.save(role,pIds,mIds);
            return new Result(true, MessageConstant.ADD_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_ROLE_FAIL);
        }
    }

    //deleteById
    @RequestMapping("/deleteById")
    private Result deleteById(Integer id) {
        try {
            roleService.deleteById(id);
            return new Result(true, MessageConstant.DELETE_ROLE_SUCCESS);
        } catch (RuntimeException er) {
            return new Result(false, MessageConstant.DELETE_ROLE_EXIST);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_ROLE_FAIL);
        }
    }

    //findById
    @RequestMapping("/findById")
    private Result findById(Integer id) {
        try {
            Role role = roleService.findById(id);
            return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS, role);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ROLE_FAIL);
        }
    }

    //update
    @RequestMapping("/update")
    private Result update(@RequestBody Role role, @RequestParam List<Integer> pIds,@RequestParam List<Integer> mIds) {
        try {
            roleService.update(role,pIds,mIds);
            return new Result(true, MessageConstant.EDIT_ROLE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_ROLE_FAIL);
        }
    }

    //查询所有角色
    @RequestMapping(value = "/findAllRole", method = RequestMethod.GET)
    public Result findAllRole(){
        try {
            List<Role> roleList = roleService.findAllRole();
            return new Result(true,MessageConstant.QUERY_ROLE_SUCCESS,roleList);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ROLE_FAIL);
        }

    }




}
