package com.tensquare.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

@EnableEurekaClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class BaseApplication {
    public static void main(String[] args) {
        // 启动入口
        SpringApplication.run(BaseApplication.class,args);
    }

    // 使用util包中的IdWorker类 在定义一个方法然后把方法放入IOC容器中
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1,1);
    }
}
