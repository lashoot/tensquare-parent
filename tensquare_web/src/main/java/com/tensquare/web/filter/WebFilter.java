package com.tensquare.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component  //放入容器
public class WebFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        // 做转发 得到request 上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        //得到 request 域
        HttpServletRequest request=currentContext.getRequest();
        //得到头信息
        String header = request.getHeader("Authorization");
        //判断是否有头信息
        if (header!=null && !"".equals(header)){
            //把头信息继续往下传
            currentContext.addZuulRequestHeader("Authorization",header);
        }

        return null;
    }
}
