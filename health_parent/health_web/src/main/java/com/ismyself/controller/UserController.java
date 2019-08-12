package com.ismyself.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ismyself.constant.MessageConstant;
import com.ismyself.entity.QueryPageBean;
import com.ismyself.entity.Result;
import com.ismyself.pojo.Menu;
import com.ismyself.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.awt.SystemColor.menu;

/**
 * package com.ismyself.controller;
 *
 * @auther txw
 * @create 2019-08-06  10:41
 * @description：
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;
    private User user;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @RequestMapping("/getUsername")
    public Result getUsername() {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }

    @RequestMapping("/findUserMenuByUsername")
    public Result findUserMenuByUsername(String username) {
        try {
            List<Menu> list = userService.findUserMenuByUsername(username);
            return new Result(true, MessageConstant.GET_MENU_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MENU_FAIL);
        }
    }

    //根据ID查询用户
    @RequestMapping(value = "/findUserById", method = RequestMethod.GET)
    public Result findUserById(Integer id) {
        // 根据检查组的id查询检查组详细信息进行数据回显
        try {
            com.ismyself.pojo.User user = userService.findUserById(id);

            return new Result(true, MessageConstant.GET_USERINFO_SUCCESS, user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERINFO_FAIL);
        }
    }

    //获取用户角色勾选状态
    @RequestMapping(value = "/findRoleIdsByUserId", method = RequestMethod.GET)
    public Result findRoleIdsByUserId(Integer uid) {
        try {
            List<Integer> list = userService.findRoleIdsByUserId(uid);
            return new Result(true, MessageConstant.QUERY_ROLEIDS_SUCCESS, list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_ROLEIDS_FAIL);
        }
    }

    //添加用户
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public Result addUser(@RequestParam List<Integer> roleIds, @RequestBody com.ismyself.pojo.User user) {
        try {
            //对用户密码加密
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            userService.addUser(roleIds, user);
            //保存成功
            return new Result(true, MessageConstant.ADD_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_USER_FAIL);
        }
    }

    //调用业务层分页查询用户
    @RequestMapping(value = "/findUser2Page", method = RequestMethod.POST)
    public Result findUser2Page(@RequestBody QueryPageBean queryPageBean) {
        try {
            Result result =
                    userService.findUser2Page(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERLIST_FAIL);
        }
    }

    //删除用户
    @RequestMapping(value = "/deleteUserById", method = RequestMethod.POST)
    public Result deleteUserById(Integer userId) {
        try {
            userService.deleteUserById(userId);
            return new Result(true, MessageConstant.DELETE_USER_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.DELETE_USER_FAIL);
        }
    }

    //编辑用户
    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    public Result editUser(@RequestParam List<Integer> ids, @RequestBody com.ismyself.pojo.User user) {
        try {
            userService.editUser(ids, user);
            return new Result(true, MessageConstant.EDIT_USERINFO_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_USERINFO_FAIL);
        }
    }


}
