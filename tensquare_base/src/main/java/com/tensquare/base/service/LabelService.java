package com.tensquare.base.service;


import com.tensquare.base.dao.LabelDao;
import com.tensquare.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/*  标签业务逻辑类
 * */
@Service
@Transactional
public class LabelService {
    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询全部标签
     *
     * @return
     */
    public List<Label> findAll() {
        return labelDao.findAll();
    }

    /* 根据ID查询标签
     * */
    public Label findById(String id) {
        //labelDao.findById(id).get() JDK 8 的新特性
        return labelDao.findById(id).get();
    }

    /*  增加标签
     * */
    public void save(Label label) {
        label.setId(idWorker.nextId() + "");//设置ID
        labelDao.save(label);
    }

    /* 修改标签
     * */
    public void update(Label label) {
        labelDao.save(label);
    }


    /*  删除标签
     * */
    public void deleteById(String id) {
        labelDao.deleteById(id);
    }


    /*  1.分页标签
     *  构建查询条件
     * */
    public List<Label> findSearch(Label label) {
        return labelDao.findAll(new Specification<Label>() {
            /**
             *   1 分页条件查询 ：
             * @param root  根对象，也就是要把条件封装到哪个对象中.where类名=label.getid
             * @param query  封装的都是查询关键字，比如group by order by 等
             * @param cb  用来封装条件对象的,如果直接返回null，表示不需要任何条件
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //new一个list集合，来存放所有的条件
                List<Predicate> list=new ArrayList<>();
                if (label.getLabelname() !=null && !"".equals(label.getLabelname())){
                   //where labelname like "% labelname %"
                    Predicate predicate = cb.like(root.get("labelname").as(String.class),"%" + label.getLabelname() + "%");
                    list.add(predicate);
                }
                if (label.getState() !=null && !"".equals(label.getState())){
                    //where state ="1"
                    Predicate predicate=cb.like(root.get("state").as(String.class),label.getState());
                    list.add(predicate);
                }
                // new 一个数组作为最终返回值的条件
                Predicate[] parr=new Predicate[list.size()];
               // 把list直接转成数组
                parr=list.toArray(parr);
                return cb.and(parr);//where labelname like "%labelname%" and state="1"
            }
        });
    }

    /*  2.带分页查询
    * */
    public Page<Label> pageQuery(Label label, int page, int size) {
        // 封装一个分页对象. page页面减1 框架是从0开始  不减的话页面会从0开始
        Pageable pageable= PageRequest.of(page-1,size);
        return labelDao.findAll(new Specification<Label>() {
            /**
             * 1 分页条件查询 ：
             *
             * @param root  根对象，也就是要把条件封装到哪个对象中.where类名=label.getid
             * @param query 封装的都是查询关键字，比如group by order by 等
             * @param cb    用来封装条件对象的,如果直接返回null，表示不需要任何条件
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                //new一个list集合，来存放所有的条件
                List<Predicate> list = new ArrayList<>();
                if (label.getLabelname() != null && !"".equals(label.getLabelname())) {
                    //where labelname like "% labelname %"
                    Predicate predicate = cb.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%");
                    list.add(predicate);
                }
                if (label.getState() != null && !"".equals(label.getState())) {
                    //where state ="1"
                    Predicate predicate = cb.like(root.get("state").as(String.class), label.getState());
                    list.add(predicate);
                }
                //new 一个数组作为最终返回值的条件
                Predicate[] parr = new Predicate[list.size()];
                //把list直接转成数组
                parr = list.toArray(parr);
                return cb.and(parr);
                //where labelname like "% labelname %" and state="1"
            }
        },pageable);
    }
}
