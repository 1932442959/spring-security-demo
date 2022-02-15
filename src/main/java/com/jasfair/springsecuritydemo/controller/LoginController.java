package com.jasfair.springsecuritydemo.controller;

import com.jasfair.springsecuritydemo.entity.UserInfo;
import com.jasfair.springsecuritydemo.service.IUserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Controller
public class LoginController {

    @Resource
    private IUserService userService;

    //    @Secured("ROLE_abc")
    // PreAuthorize 允许 ROLE_ 开头，配置类不允许
//    @PreAuthorize("hasRole('abc')")
    @PreAuthorize("hasRole('ROLE_abc')")
    @RequestMapping("/toMain")
    public String toMain() {
        return "redirect:main.html";
    }

    @RequestMapping("/toError")
    public String toError() {
        return "redirect:error.html";
    }

    @RequestMapping("/showLogin")
    public String showLogin() {
        return "login";
    }
}
