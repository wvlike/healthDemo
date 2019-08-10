package com.ismyself.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * package com.ismyself.controller;
 *
 * @auther txw
 * @create 2019-08-05  19:13
 * @description：
 */
@Controller
@RequestMapping("/hello")
public class HelloController {

    @RequestMapping("/add")
    @PreAuthorize("hasAnyAuthority('add')")
    public String add(){
        System.out.println("add...............................");
        return "success";
    }

    @RequestMapping("/delete")
    @PreAuthorize("hasAnyAuthority('delete')")
    public String delete(){
        System.out.println("delete..............................");
        return "success";
    }
}
