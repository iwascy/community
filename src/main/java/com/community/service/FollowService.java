package com.community.service;

import com.community.mapper.FollowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FollowService {
    @Autowired
    private FollowMapper followMapper;

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
        }else {
            addFollow(user,userFollowed);
        }
    }
}
