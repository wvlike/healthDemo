package com.ismyself.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ismyself.constant.MessageConstant;
import com.ismyself.entity.Result;
import com.ismyself.pojo.Menu;
import com.ismyself.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/getUsername")
    public Result getUsername() {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS,user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }

    @RequestMapping("/findUserMenuByUsername")
    public Result findUserMenuByUsername(String username){
        try {
            List<Menu> list = userService.findUserMenuByUsername(username);
            return new Result(true,MessageConstant.GET_MENU_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_MENU_FAIL);
        }
    }
}
