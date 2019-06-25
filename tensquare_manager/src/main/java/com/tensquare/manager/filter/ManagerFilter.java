package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

/*  Zuul过滤器
* */
@Component
public class ManagerFilter extends ZuulFilter {


    @Override
    public String filterType() {
        return "pre";//前置过滤器  在请求前pre或者后post执行
    }

    @Override
    public int filterOrder() {
        return 0;// 优先级为0，数字越大，优先级越低
    }

    @Override
    public boolean shouldFilter() {
        return true;// 是否执行该过滤器，此处为true，说明需要过滤
    }

    /*  过滤器内执行的操作 return任何object的值都表示继续执行
     *  setsendzullRespponse(false)表示不再继续执行
    * */
    @Override
    public Object run() throws ZuulException {
        System.out.println("经过后台过滤器啦！");
        return null;
    }
}
