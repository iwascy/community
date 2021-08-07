package com.community.service;

import com.community.enums.NotificationEnum;
import com.community.mapper.FollowMapper;
import com.community.mapper.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FollowService {
    @Autowired
    private FollowMapper followMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    public void addFollow(int user,int userFollowed){
        long createTime = System.currentTimeMillis();
        followMapper.addFollow(user,userFollowed,createTime,createTime);
    }

    public void deleteFollow(int user,int userFollowed){
        followMapper.deleteFollow(user,userFollowed);
    }

    public boolean followStatus(int user,int userFollowed){
        int count = followMapper.findFollowStatus(user,userFollowed);
        if(count == 1) return true;
        return false;
    }

    public void follow(int user,int userFollowed){
        if(followStatus(user,userFollowed)){
            deleteFollow(user,userFollowed);
            notificationMapper.deleteNotification(user,userFollowed,NotificationEnum.FOLLOW_USER.getType(), -1);
        }else {
            addFollow(user,userFollowed);
            notificationMapper.insertNotification(user,userFollowed, NotificationEnum.FOLLOW_USER.getType(), 0,-1,System.currentTimeMillis());
        }
    }
}
