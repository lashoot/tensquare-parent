package com.tensquare.qa.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.qa.pojo.Problem;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 * 其中分页不能用List<> 泛型 ，要使用Page<> 泛型.
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    /*  最新问题标签   sql语句排序 replytime
    * */
    @Query(value = "SELECT * FROM tb_problem, tb_pl WHERE id=problemid AND labelid=? order by replytime desc" ,nativeQuery = true)
   // 分页 传入  Pageable pageable 这个对象就可以了！ 导入包名import org.springframework.data.domain.Pageable;
    public Page<Problem> newlist(String labelid, Pageable pageable);


    /* 热门问题标签    sql语句排序 reply
    * */
    @Query(value = "SELECT * FROM tb_problem, tb_pl WHERE id=problemid AND labelid=? ORDER BY reply DESC" ,nativeQuery = true)
    public Page<Problem> hostlist(String labelid, Pageable pageable);

    /*  等待问题标签  sql语句排序
    * */
    @Query(value = "SELECT * FROM tb_problem,tb_pl WHERE id=problemid AND labelid=? AND reply=0 ORDER BY createtime DESC" ,nativeQuery = true)
    public Page<Problem> waitlist(String labelid, Pageable pageable);

}
