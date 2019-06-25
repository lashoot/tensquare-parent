package com.tensquare.friend.config;

import com.tensquare.friend.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

public class InterceptorConfig extends WebMvcConfigurationSupport {
   /*  配置拦截器类
   * */
    @Autowired
    private JwtInterceptor jwtInterceptor;

   protected void addInterceptors(InterceptorRegistry registry){
       //注册拦截器要声明拦截器对象和要拦截的请求
       registry.addInterceptor(jwtInterceptor)
               .addPathPatterns("/**")
               .excludePathPatterns("/**/login/**");

   }


}
