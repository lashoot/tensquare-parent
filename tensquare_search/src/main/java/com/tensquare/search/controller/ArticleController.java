package com.tensquare.search.controller;


import com.tensquare.search.pojo.Article;
import com.tensquare.search.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RequestMapping("/article")
@RestController
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    //  添加文章数据
    @RequestMapping(method = RequestMethod.POST)
    public Result save(@RequestBody Article article){
        articleService.save(article);
        return new Result(true, StatusCode.OK,"添加操作成功！");
    }

    // 查询使用文章
    @RequestMapping(value = "/{key}/{page}/{size}",method = RequestMethod.GET)
    public Result findByKey(@PathVariable String key,@PathVariable int page,@PathVariable int size){
        Page<Article> pageData=articleService.findByKey(key,page,size);
        return new Result(true,StatusCode.OK,"文章查询使用成功！",new PageResult<Article>(pageData.getTotalElements(),pageData.getContent()));
    }


}
