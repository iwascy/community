package com.community.service;

import com.community.domain.Like;
import com.community.mapper.LikeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {
    @Autowired
    private LikeMapper likeMapper;

    public boolean addLike(Like like){
        if(alreadyLike(like)){
            return false;
        }else{
            likeMapper.addLike(like.getUser(), like.getId(), like.getCreateTime(), like.getUpdateTime());
            return true;
        }
    }

    public boolean alreadyLike(Like like){
        if(likeMapper.findLikeCountByUserAndQuestion(like.getUser(),like.getQuestion()) != 0){
            return true;
        }
        return false;
    }
}
