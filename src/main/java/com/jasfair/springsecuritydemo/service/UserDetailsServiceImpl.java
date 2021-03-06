package com.jasfair.springsecuritydemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 实现 UserDetailsService
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder pw;

    /**
     * 判断登陆，并设置角色列表
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询数据库判断用户名是否存在，不存在抛出 UsernameNotFoundException 异常
        if (!"admin".equals(username)) {
            throw new UsernameNotFoundException("用户名不存在");
        }
        // 把查询出来的密码（注册时已经加密的密码）进行解析，或者直接把密码放入构造方法
        String password = pw.encode("123");
        return new User(username, password, AuthorityUtils.commaSeparatedStringToAuthorityList
                                                                  ("admin,normal,ROLE_abc,/main.html"));
    }
}
