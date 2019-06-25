package com.tensquare.search.service;


import com.tensquare.search.dao.AritcleDao;
import com.tensquare.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import util.IdWorker;

@Service
public class ArticleService {

    @Autowired
    private AritcleDao aritcleDao;

//    @Autowired
//    private IdWorker idWorker;

    /*  增加文章
    * */
    public void save(Article article){
        //  article.setId(idWorker.nextId()+"");
        aritcleDao.save(article);//直接用默认ID 把上面注释
    }
//  查询所有方法
    public Page<Article> findByKey(String key, int page, int size) {
        Pageable pageable = PageRequest.of(page-1,size);
        return aritcleDao.findByTitleOrContentLike(key,key,pageable);
    }
}
