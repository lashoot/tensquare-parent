package com.tensquare.search.dao;

import com.tensquare.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface AritcleDao extends ElasticsearchRepository<Article,String> {
    //  文章查询 定义方法内容方法名：自动生成SQL语句
    public Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);

}
