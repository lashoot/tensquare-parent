package com.tensquare.rabbit.customer;


import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component  //  放入容器
@RabbitListener(queues = "itcast")  //监听器   (queues= “指定发送的路径”)
public class Customer1 {
/*  rabbit消息队列里面可以 ：负载均衡： 好处：不至于某一个机器，某机器压力过大，大家可以相互平衡
* */
    @RabbitHandler
    public void getMsg(String msg){
        // 负载均衡的输出输出语句
        //System.out.println("3.直接模式消费消息："+msg);
        System.out.println("itcast："+msg);
    }

}
