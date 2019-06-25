package com.tensquare.spit.service;

import com.tensquare.spit.dao.SpitDao;
import com.tensquare.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import java.util.Date;
import java.util.List;

/*  业务逻辑
 * */
@Service
@Transactional
public class SpitService {

    @Autowired
    private SpitDao spitDao;
    // 注入属性 自己指定id
    @Autowired
    private IdWorker idWorker;

    // 方式2.原生 MongoTemplate 操作mongo
    @Autowired
    private MongoTemplate mongoTemplate;

    /*   查询全部记录
     * */
    public List<Spit> findAll() {
        return spitDao.findAll();
    }

    //    根据主键查询实体
    public Spit findById(String id) {
        return spitDao.findById(id).get();
    }

    //  增加信息  发布吐槽（或吐槽评论）
    public void save(Spit spit) {
        spit.set_id(idWorker.nextId() + "");
        spit.setPublishtime(new Date());//发布日期     
        spit.setVisits(0);//浏览量      
        spit.setShare(0);//分享数     
        spit.setThumbup(0);//点赞数    
        spit.setComment(0);//回复数     
        spit.setState("1");//状态
        //如果当前的吐槽，有父节点，那么父节点的吐槽回复数要加一
        if (spit.getParentid()!=null && !"".equals(spit.getParentid())){
            Query query=new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));

            Update update=new Update();
            update.inc("comment",1);

            mongoTemplate.updateFirst(query,update,"spit");
        }

        spitDao.save(spit);
    }

    //  修改
    public void update(Spit spit) {
        spitDao.save(spit);
    }

    // 删除
    public void deleteById(String id) {
        spitDao.deleteById(id);
    }

    //    分页标签
    public Page<Spit> findByParentid(String parentid, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return spitDao.findByParentid(parentid, pageable);
    }

    // 吐槽点赞标签
    public void thumbup(String spitId) {
        // 方式1：效率有问题
     /*   Spit spit=spitDao.findById(spitId).get();
        spit.setThumbup((spit.getThumbup()==null?0:spit.getThumbup())+1);
        spitDao.save(spit);*/

        // 方式2：使用原生mongo命令来实现自增 db.spit.update({"_id":"1"},{$inc:{thumbup:NumberInt(1)}})
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is("1"));
        Update update = new Update();
        update.inc("thumbup", 1);
        mongoTemplate.updateFirst(query, update, "spit");

    }
}

