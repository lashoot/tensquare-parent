package com.tensquare.spit.controller;

import com.tensquare.spit.pojo.Spit;
import com.tensquare.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;




@RestController
@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;
    // redistemplate 缓存
    @Autowired
    private RedisTemplate redisTemplate;

// 查询所有
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK,"查询成功！",spitService.findAll());
    }
// 根据ID查询信息
    @RequestMapping(value = "/{spitId}",method = RequestMethod.GET)
    public Result findById(@PathVariable String spitId ){
        return new Result(true, StatusCode.OK,"ID.查询成功！",spitService.findById(spitId));
    }

//  增加信息
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Spit spit){
        spitService.save(spit);
        return new Result(true, StatusCode.OK,"增加成功！");
    }

//    修改信息
    @RequestMapping(value = "/{spitId}",method = RequestMethod.PUT)
    public Result update(@PathVariable String spitId, @RequestBody Spit spit){
        spit.set_id(spitId);
        spitService.update(spit);
        return new Result(true,StatusCode.OK,"修改成功，");
    }
//  删除信息
    @RequestMapping(value = "/{spitId}",method = RequestMethod.DELETE)
    public Result delete(@PathVariable String spitId){
        spitService.deleteById(spitId);
        return new Result(true,StatusCode.OK,"删除成功，");
    }

//    分页信息
    @RequestMapping(value = "/comment/{parentid}/{page}/{size}",method = RequestMethod.GET)
    public Result findByParentid(@PathVariable String parentid,@PathVariable int page,@PathVariable int size){
        Page<Spit> pageData = spitService.findByParentid(parentid, page, size);
        return  new Result(true,StatusCode.OK,"分页功能查询成功！",new PageResult<Spit>(pageData.getTotalElements(),pageData.getContent()));
    }

//  吐槽点赞   加入不能重复点赞
    @RequestMapping(value = "/thumbup/{spitId}",method = RequestMethod.PUT)
    public Result thumbup(@PathVariable String spitId){
        //判断当前用户是否已经点赞，但是现在我们没有做认证，暂时把 userid 写死
        String userid="'111";
        //判断当前用户是否已经点赞
        if (redisTemplate.opsForValue().get("thumbup_"+userid)!=null){
            return new Result(false,StatusCode.REPERROR,"不能重复点赞,你已经点过赞啦！");
        }

        spitService.thumbup(spitId);
        redisTemplate.opsForValue().set("thumbup_"+userid,1);
        return new Result(true,StatusCode.OK,"点赞成功.");

    }



}
