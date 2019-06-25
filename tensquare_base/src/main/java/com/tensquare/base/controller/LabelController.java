package com.tensquare.base.controller;

import com.tensquare.base.pojo.Label;
import com.tensquare.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController // 转对象的时候不用写@ResponseBody这个注解
@CrossOrigin    // 跨域
@RequestMapping("/label")
@RefreshScope  //自定义的IP 会刷新
public class LabelController {
    /*  标签控制层
     * */
    @Autowired
    private LabelService labelService;

    @Autowired
    private HttpServletRequest request;

    @Value("${ip}")
    private String ip;

    /*  查询所有  GET：请求
    * */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        System.out.println("ip为："+ip);
        // 网关 获取头信息 测试
        String header = request.getHeader("Authorization");
        System.out.println(header);

        List<Label>list =labelService.findAll();
        return new Result(true, StatusCode.OK,"查询成功",list);
    }

    /*  根据ID查询  GET：请求
    * */
    @RequestMapping(value = "/{labelId}",method = RequestMethod.GET)
    public Result findById(@PathVariable("labelId") String labelId){
        Label label=labelService.findById(labelId);
        return new Result(true, StatusCode.OK,"查询成功",label);
    }

    /*  添加  POST：请求
    * */
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Label label){
        labelService.save(label);
        return new Result(true,StatusCode.OK,"添加成功");

    }

    /*
    *    更新修改操作ID PUT:请求
    * */
    @RequestMapping(value = "/{labelId}",method = RequestMethod.PUT)
    public Result update(@PathVariable String labelId, @RequestBody Label label){
        label.setId(labelId);
        labelService.update(label);
        return new Result(true,StatusCode.OK,"更新成功");

    }


    /*  删除ID  DELETE:请求
    * */
    @RequestMapping(value = "/{labelId}",method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String labelId){
        labelService.deleteById(labelId);
        return new Result(true,StatusCode.OK,"删除成功");

    }

    /*  1.分页  根据条件查询   json即可转Map 也可以转对象Label
     * */
    @RequestMapping(value = "/search",method = RequestMethod.POST)
    public Result findSearch(@RequestBody Label label){
        List<Label> list=labelService.findSearch(label);
        return new Result(true,StatusCode.OK,"Hello！条件查询成功",list);
    }

    /*   2.分页条件查询
     * */
    @RequestMapping(value = "/search/{page}/{size}",method = RequestMethod.POST)
    public Result pageQuery(@RequestBody Label label,@PathVariable int page,@PathVariable int size){
        Page<Label> pageData=labelService.pageQuery(label,page,size);
        //  总记录数
        return new Result(true,StatusCode.OK,"你好！请输入条件，然后查询，返回成功！",new PageResult<Label>(pageData.getTotalElements(),pageData.getContent()));
    }

}
