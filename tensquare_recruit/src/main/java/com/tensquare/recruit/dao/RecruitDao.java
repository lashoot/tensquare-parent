package com.tensquare.recruit.dao;

import com.tensquare.recruit.pojo.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{
	/*  推荐热门企业
	* */
	public List<Recruit> findTopByStateOrderByCreatetimeDesc(String state);//where state=? order by createime  相当于一个参数

    /*  查询最新职位列表(按创建日期降序排序)
     * */
    public List<Recruit> findTopByStateNotOrderByCreatetimeDesc(String state);//where state=? order by createime  相当于一个参数
}
