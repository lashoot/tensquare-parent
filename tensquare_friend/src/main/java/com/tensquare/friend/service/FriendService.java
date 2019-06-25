package com.tensquare.friend.service;


import com.tensquare.friend.dao.FriendDao;
import com.tensquare.friend.dao.NoFriendDao;
import com.tensquare.friend.pojo.Friend;
import com.tensquare.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    public int addFrind(String userid, String friendid) {
        //先判断userID到friend 是否有数据，有就是重复添加好友.返回0
        Friend friend = friendDao.findByUseridAndAndFriendid(userid, friendid);
        if (friend!=null){
            return 0;
        }

        //直接添加好友，让好友表中userID 到friendid 方向的type 为0
        friend=new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);


        //判断从 friendID到 userid 是否有数据，如果有，把双方的状态都改为1
        if (friendDao.findByUseridAndAndFriendid(friendid,userid)!=null){
            //把双方的islike都改成1
            friendDao.updateIslike("1",userid,friendid);
            friendDao.updateIslike("1",friendid,userid);
        }
        return 1;
    }

    /*  noFrind
    * */
    public int addNoFrind(String userid, String friendid) {
        //先判断是否已经是非好友
        NoFriend noFriend=noFriendDao.findByUseridAndAndFriendid(userid,friendid);
        if (noFriend!=null){
            return 0;
        }
        noFriend=new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
        return 1;

    }

    // 删除好友方法
    public void deleteFriend(String userid, String friendid) {
        //删除还有表中 userid 到 friendid 这条数据
        friendDao.deletefriend(userid,friendid);
        //更新  friendid 到 userid 的islike 为0
        friendDao.updateIslike("0",friendid,userid);

        //非好友表中添加数据
        NoFriend noFriend=new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);
        noFriendDao.save(noFriend);
    }
}
