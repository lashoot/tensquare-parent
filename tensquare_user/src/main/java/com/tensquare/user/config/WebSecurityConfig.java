package com.tensquare.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全配置类
 */
@Configuration  //配置类
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
       1. authorizeRequests所有security全配置实现的开端，表示开始说明需要的权限。
       2. 需要的权限分两部分，第一部分是拦截的路径，第二部分访问该路径需要的权限.
       3.  antMatchers  表示拦截什么路径， permitAll 任何权限都可以访问.直接放行所有
       4. anyRequest()任何的请求.authenticated认证后才能访问
       5.  .and().csrf().disable()固定写法.表示使csrf拦截失败
         */

        http
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
    }
}
