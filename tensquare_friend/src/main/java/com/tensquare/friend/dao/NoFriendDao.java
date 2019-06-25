package com.tensquare.friend.dao;


import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NoFriendDao extends JpaRepository<NoFriend,String> {
    //联合主键查询  写上这个方法 SQL语句可以不用写
    public NoFriend findByUseridAndAndFriendid(String userid, String friendid);

}
