package com.tensquare.user.service;

import com.tensquare.user.dao.UserDao;
import com.tensquare.user.pojo.User;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;
import util.JwtUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private IdWorker idWorker;
    //缓存
    @Autowired
    private RedisTemplate redisTemplate;
    // 消息队列
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private HttpServletRequest request;


    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    /*      根据手机号和密码查询用户
     * */
    public User findByMobileAndPassword(String mobile,String password) {
        //先根据用户名查询对象。
        User userlogin = userDao.findByMobile(mobile);
        //然后拿数据库中的密码和用户输入的密码匹配是否相同
        // 判断if 防止空指针
        if (userlogin != null && encoder.matches(password, userlogin.getPassword())) {
            // 保证数据库中的对象中的密码和用户输入的密码是一致的，登录成功
            return userlogin;
        }
        // 登录失败
        return null;
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<User> findAll() {
        return userDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<User> findSearch(Map whereMap, int page, int size) {
        Specification<User> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return userDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<User> findSearch(Map whereMap) {
        Specification<User> specification = createSpecification(whereMap);
        return userDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public User findById(String id) {
        return userDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param user
     */
    public void add(User user) {
        // 完善基础信息  增加一些数据  刚注册的信息没有的话帮客户完善
        user.setId(idWorker.nextId() + "");
        user.setFollowcount(0);//关注数
        user.setFanscount(0);//粉丝数
        user.setOnline(0L);//在线时长
        user.setRegdate(new Date());//注册日期
        user.setUpdatedate(new Date());//更新日期
        user.setLastdate(new Date());//最后登陆日期
        userDao.save(user);
    }


    /**
     * 修改
     *
     * @param user
     */
    public void update(User user) {
        userDao.save(user);
    }

    /**
     * 删除  必须有admin角色才能删除  判断验证
     *
     * @param id
     */
    public void deleteById(String id) {
       /* String header=request.getHeader("Authorization");
        if (header==null || "".equals(header)){
            throw new RuntimeException("权限不足！~");
        }

        if (!header.startsWith("Bearer ")){
            throw new RuntimeException("权限不足呀..！");
        }
        //得到 token
        String token=header.substring(7);
        // 做验证信息才能删除.
        try{
            Claims claims = jwtUtil.parseJWT(token);
            String roles = (String) claims.get("roles");
            if (roles==null || !roles.equals("admin")){
                throw new RuntimeException("权限不足！！");
            }
        }catch (Exception e){
            throw new RuntimeException("权限不足呀！");
        }*/
        String tocken = (String) request.getAttribute("claims_admin");
        if (tocken == null || "".equals(tocken)) {
            throw new RuntimeException("权限不足！.");
        }
        userDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<User> createSpecification(Map searchMap) {

        return new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
                }
                // 手机号码
                if (searchMap.get("mobile") != null && !"".equals(searchMap.get("mobile"))) {
                    predicateList.add(cb.like(root.get("mobile").as(String.class), "%" + (String) searchMap.get("mobile") + "%"));
                }
                // 密码
                if (searchMap.get("password") != null && !"".equals(searchMap.get("password"))) {
                    predicateList.add(cb.like(root.get("password").as(String.class), "%" + (String) searchMap.get("password") + "%"));
                }
                // 昵称
                if (searchMap.get("nickname") != null && !"".equals(searchMap.get("nickname"))) {
                    predicateList.add(cb.like(root.get("nickname").as(String.class), "%" + (String) searchMap.get("nickname") + "%"));
                }
                // 性别
                if (searchMap.get("sex") != null && !"".equals(searchMap.get("sex"))) {
                    predicateList.add(cb.like(root.get("sex").as(String.class), "%" + (String) searchMap.get("sex") + "%"));
                }
                // 头像
                if (searchMap.get("avatar") != null && !"".equals(searchMap.get("avatar"))) {
                    predicateList.add(cb.like(root.get("avatar").as(String.class), "%" + (String) searchMap.get("avatar") + "%"));
                }
                // E-Mail
                if (searchMap.get("email") != null && !"".equals(searchMap.get("email"))) {
                    predicateList.add(cb.like(root.get("email").as(String.class), "%" + (String) searchMap.get("email") + "%"));
                }
                // 兴趣
                if (searchMap.get("interest") != null && !"".equals(searchMap.get("interest"))) {
                    predicateList.add(cb.like(root.get("interest").as(String.class), "%" + (String) searchMap.get("interest") + "%"));
                }
                // 个性
                if (searchMap.get("personality") != null && !"".equals(searchMap.get("personality"))) {
                    predicateList.add(cb.like(root.get("personality").as(String.class), "%" + (String) searchMap.get("personality") + "%"));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }

    /*	发送验证码
     * */
    public void sendSms(String mobile) {
        //生成6数字随机数  为验证码
        String checkcode = RandomStringUtils.randomNumeric(6);
        // 向缓存中放入一份  存放时间
        redisTemplate.opsForValue().set("checkcode_" + mobile, checkcode, 6, TimeUnit.HOURS);
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("checkcode", checkcode);
        //给用户分一份
        rabbitTemplate.convertAndSend("sms", map);

        //在控制台显示一份{方便测试}
        System.out.println("验证码为：" + checkcode);
    }

    /*  粉丝数 和关注数
     * */
    @Transactional
    public void updatefanscountandfollowcount(int x, String userid, String friendid) {
        userDao.updatefanscount(x,friendid);
        userDao.updatefollowcount(x,userid);

    }
}
