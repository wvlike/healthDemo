package com.ismyself.security;

import com.alibaba.dubbo.config.annotation.Reference;
import com.ismyself.pojo.Permission;
import com.ismyself.pojo.Role;
import com.ismyself.pojo.User;
import com.ismyself.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @Reference
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findUserByUname(username);
        if (user == null) {
            return null;
        }
        Set<Role> roles = user.getRoles();
        List<GrantedAuthority> list = new ArrayList<>();
        if (roles != null && roles.size() > 0) {
            for (Role role : roles) {
                list.add(new SimpleGrantedAuthority(role.getKeyword()));
                Set<Permission> permissions = role.getPermissions();
                if (permissions != null & permissions.size() > 0) {
                    for (Permission permission : permissions) {
                        list.add(new SimpleGrantedAuthority(permission.getKeyword()));
                    }
                }
            }
        }
        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), list);
    }
}