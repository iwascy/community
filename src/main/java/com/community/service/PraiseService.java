package com.community.service;

import com.community.domain.Praise;
import com.community.mapper.PraiseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PraiseService {
    @Autowired
    private PraiseMapper praiseMapper;

    public boolean addPraise(Praise praise){
        if(alreadyPraise(praise.getUser(),praise.getQuestion())){
            praiseMapper.deletePraiseByUserAndQuestion(praise.getUser(),praise.getQuestion());
            return false;
        }else{
            praiseMapper.addPraise(praise.getUser(), praise.getQuestion(), praise.getCreateTime(), praise.getUpdateTime());
            return true;
        }
    }

    public boolean alreadyPraise(int user,int question){
        if(praiseMapper.findPraiseCountByUserAndQuestion(user,question) != 0){
            return true;
        }
        return false;
    }
}
