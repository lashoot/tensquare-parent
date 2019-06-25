package com.tensquare.qa.client;

import com.tensquare.qa.client.impl.BaseClientImpl;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component  //没有实现类可以不加Component 因为这个微服务里面没有创建实体类 都是也可以加
@FeignClient(value = "tensquare-base",fallback = BaseClientImpl.class)
public interface BaseClient {

    //问答微服务调用基础微服务的配置接口方法findById
    @RequestMapping(value = "/label/{labelId}",method = RequestMethod.GET)
    public Result findById(@PathVariable("labelId") String labelId);
}
