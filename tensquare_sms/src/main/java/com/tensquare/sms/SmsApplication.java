package com.tensquare.sms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SmsApplication {
    // 消费者服务器一直启动
    public static void main(String[] args) {
        SpringApplication.run(SmsApplication.class);
    }
}
