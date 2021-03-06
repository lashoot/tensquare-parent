package com.tensquare.spit.dao;

import com.tensquare.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/*  吐槽数据访问层
 * */
public interface SpitDao extends MongoRepository<Spit,String> {

//    分页标签
    public Page<Spit> findByParentid(String parentid, Pageable pageable);

}
