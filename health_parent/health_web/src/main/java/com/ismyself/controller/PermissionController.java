package com.ismyself.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ismyself.constant.MessageConstant;
import com.ismyself.entity.PageResult;
import com.ismyself.entity.QueryPageBean;
import com.ismyself.entity.Result;
import com.ismyself.pojo.Menu;
import com.ismyself.pojo.Permission;
import com.ismyself.service.PermissionService;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * package com.ismyself.controller;
 *
 * @auther txw
 * @create 2019-08-10  18:49
 * @description：
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Reference
    private PermissionService permissionService;

    @RequestMapping("/findList")
    public Result findList(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = permissionService.findList(queryPageBean);
            return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_PERMISSION_FAIL);
        }
    }


    //save
    @RequestMapping("/save")
    private Result save(@RequestBody Permission permission) {
        try {
            permissionService.save(permission);
            return new Result(true, MessageConstant.ADD_PERMISSION_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_PERMISSION_FAIL);
        }
    }

    //deleteById
    @RequestMapping("/deleteById")
    private Result deleteById(Integer id) {
        try {
            permissionService.deleteById(id);
            return new Result(true, MessageConstant.DELETE_PERMISSION_SUCCESS);
        } catch (RuntimeException er) {
            return new Result(false,MessageConstant.DELETE_PERMISSION_EXIST);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_PERMISSION_FAIL);
        }
    }

    //findById
    @RequestMapping("/findById")
    private Result findById(Integer id) {
        try {
            Permission permission = permissionService.findById(id);
            return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS, permission);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_PERMISSION_FAIL);
        }
    }

    //update
    @RequestMapping("/update")
    private Result update(@RequestBody Permission permission) {
        try {
            permissionService.update(permission);
            return new Result(true, MessageConstant.EDIT_PERMISSION_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_PERMISSION_FAIL);
        }
    }


    //findAllList
    @RequestMapping("/findAllList")
    private Result findAllList() {
        try {
            List<Permission> list = permissionService.findAllList();
            return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_PERMISSION_FAIL);
        }
    }

    //findIdsByRoleId
    @RequestMapping("/findIdsByRoleId")
    private Result findIdsByRoleId(Integer rId) {
        try {
            List<Integer> list = permissionService.findIdsByRoleId(rId);
            return new Result(true, MessageConstant.QUERY_PERMISSION_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_PERMISSION_FAIL);
        }
    }
}
