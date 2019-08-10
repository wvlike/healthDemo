package com.ismyself.mongodb;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ismyself.mongodb.DbUserService;
import com.ismyself.mongodb.Users;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * package com.ismyself.mongodb;
 *
 * @auther txw
 * @create 2019-08-08  16:14
 * @description：
 */
@Controller
@RequestMapping("/mongodb")
public class DbUserController {

    @Reference
    private DbUserService dbUserService;

    @RequestMapping("/save")
    public void save(){
        Users dbUser = new Users();
        dbUser.set_id(5.0);
        dbUser.setName("dark");
        dbUser.setAge(Double.valueOf(18));
        dbUserService.save(dbUser);
        System.out.println("--------------------------------------------------------------------------------------------");
    }

    @RequestMapping("/findone")
    public void findone(){
        List<Users> users = dbUserService.findByUid(2.0);
        System.out.println(users+"--------------------------------------------------------------------------------------------");
    }


    @RequestMapping("/findall")
    public void findall(){
        List<Users> users = dbUserService.findAll();
        System.out.println(users+"--------------------------------------------------------------------------------------------");
    }




}
