package com.tensquare.friend.dao;


import com.tensquare.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface FriendDao extends JpaRepository<Friend,String> {
    //联合主键查询  写上这个方法 SQL语句可以不用写
    public Friend findByUseridAndAndFriendid(String userid,String friendid);

    //更新
    @Modifying
    @Query(value = "UPDATE tb_friend SET islike=? WHERE userid = ? AND friendid = ?",nativeQuery = true)
    public void updateIslike(String islike,String userid,String friendid);

    //删除
    @Modifying
    @Query(value = "delete  from tb_friend  WHERE userid = ? AND friendid = ?",nativeQuery = true)
    void deletefriend(String userid, String friendid);
}
