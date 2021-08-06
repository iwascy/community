package com.community.service;

import com.community.domain.Praise;
import com.community.dto.NotificationDTO;
import com.community.enums.NotificationEnum;
import com.community.mapper.NotificationMapper;
import com.community.mapper.PraiseMapper;
import com.community.mapper.QuestionMapper;
import com.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PraiseService {
    @Autowired
    private PraiseMapper praiseMapper;

    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public boolean addPraise(Praise praise){
        int userNotified = questionMapper.findCreatorByQuestion(praise.getQuestion());
        if(alreadyPraise(praise.getUser(),praise.getQuestion())){
            praiseMapper.deletePraiseByUserAndQuestion(praise.getUser(),praise.getQuestion());
            notificationMapper.deleteNotification(praise.getId(),userNotified,NotificationEnum.PRAISE_QUESTION.getType(), praise.getQuestion());
            return false;
        }else{
            praiseMapper.addPraise(praise.getUser(), praise.getQuestion(), praise.getCreateTime(), praise.getUpdateTime());
            notificationMapper.insertNotification(praise.getId(),userNotified,NotificationEnum.PRAISE_QUESTION.getType(),0, praise.getQuestion(), System.currentTimeMillis());
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
