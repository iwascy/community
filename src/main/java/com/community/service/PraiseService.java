package com.community.service;

import com.community.domain.Praise;
import com.community.domain.Question;
import com.community.dto.NotificationDTO;
import com.community.dto.PraiseUserDTO;
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

    @Autowired
    private NotificationService notificationService;

    public boolean addPraise(PraiseUserDTO praiseUserDTO){
        int questionId = praiseUserDTO.getQuestion();
        int userId = praiseUserDTO.getUser();
        long createTime = System.currentTimeMillis();
        long updateTime = System.currentTimeMillis();
        int userNotified = questionMapper.findCreatorByQuestion(questionId);
        if(alreadyPraise(userId,questionId)){
            praiseMapper.deletePraiseByUserAndQuestion(userId,questionId);
            notificationMapper.deleteNotification(userId,userNotified,NotificationEnum.PRAISE_QUESTION.getType(), questionId);
            return false;
        }else{
            praiseMapper.addPraise(userId, questionId, createTime, updateTime);
            notificationService.addNotification(userId,userNotified,NotificationEnum.PRAISE_QUESTION.getType(),questionId);
            //notificationMapper.insertNotification(userId,userNotified,NotificationEnum.PRAISE_QUESTION.getType(),0, questionId, System.currentTimeMillis());
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
