package com.jasfair.springsecuritydemo.config;

import com.jasfair.springsecuritydemo.handle.MyAccessDeniedHandler;
import com.jasfair.springsecuritydemo.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;

/**
 * SpringSecurity 配置类
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private MyAccessDeniedHandler myAccessDeniedHandler;

    @Resource
    private UserDetailsServiceImpl userDetailsService;

    @Resource
    private PersistentTokenRepository persistentTokenRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 表单提交
        http.formLogin()
            // 与表单中一致，默认是 username
            .usernameParameter("username")
            // 与表单中一致，默认为 password
            .passwordParameter("password")
            // 当发现是 /login 时认为是登陆，必须和表单提交的地址相同，会执行 UserDetailsServiceImpl 逻辑
            .loginProcessingUrl("/login")
            // 自定义登录页面
            .loginPage("/login.html")
            // 登陆成功后跳转页面，必须是 POST 请求
            .successForwardUrl("/toMain")
            // 登陆后跳转页面，重写登陆成功后处理器，不能和 successForwardUrl 共存
//            .successHandler(new MyAuthenticationSuccessHandler("http://www.baidu.com"))
            // 登录失败后跳转页面，必须是 POST 请求
            .failureForwardUrl("/toError");
        // 登陆失败跳转页面，重写登陆失败后处理器，不能和 failureForwardUrl 共存
//            .failureHandler(new MyAuthenticationFailureHandler("/error.html"));

        // 授权认证
        http.authorizeRequests()
            // login.html 不需要被验证
//            .antMatchers("/login.html", "/error.html").permitAll()
            // 配置页面权限
//            .antMatchers("/main1.html").hasAuthority("admin")
//            .antMatchers("/main1.html").hasAnyAuthority("admin", "admin1")
            // 配置角色访问
//                .antMatchers("/main1.html").hasRole("abc")
//            .antMatchers("/main1.html").hasAnyRole("abc", "abc1")
            // access 配置
            .antMatchers("/login.html", "/error.html").access("permitAll()")
//            .antMatchers("/main1.html").access("hasRole('abc')")
            // 自定义权限配置
//            .anyRequest().access("@permissionServiceImpl.hasPermission(request, authentication)");
            // 所有请求都必须被认证，必须是登录之后访问
            .anyRequest().authenticated();

        // 关闭 csrf 防护
        http.csrf().disable();

        // 异常处理
        http.exceptionHandling()
            .accessDeniedHandler((myAccessDeniedHandler));

        http.rememberMe()
            // 失效时间，单位秒
            .tokenValiditySeconds(60)
            // 表单参数名称
            .rememberMeParameter("remember-me")
            // 登陆逻辑
            .userDetailsService(userDetailsService)
            // 持久层对象
            .tokenRepository(persistentTokenRepository);

        // 退出登陆
        http.logout()
            .logoutUrl("/logout")
            //退出登录跳转页面
            .logoutSuccessUrl("/login.html");
    }

    @Bean
    public PasswordEncoder getPw() {
        // 加密方法
        return new BCryptPasswordEncoder();
    }

}
