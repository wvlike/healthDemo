package com.ismyself.security;

import com.ismyself.pojo.Permission;
import com.ismyself.pojo.Role;
import com.ismyself.pojo.User;
import com.ismyself.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * package com.ismyself.service;
 *
 * @auther txw
 * @create 2019-08-04  15:04
 * @description：
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {


    @Autowired
    private BCryptPasswordEncoder encoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByUname(username);
        if (user == null){
            return null;
        }
        String password = user.getPassword();
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new SimpleGrantedAuthority("add"));
        list.add(new SimpleGrantedAuthority("delete"));
        list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return new org.springframework.security.core.userdetails.User(username,password,list);
    }

    private User findUserByUname(String username) {
        User user = null;
        if (username.equals("admin")){
            user = new User();
            user.setUsername(username);
            String encode = encoder.encode("123456");
            System.out.println(encode);
            user.setPassword(encode);
        }
        return user;
    }
}
