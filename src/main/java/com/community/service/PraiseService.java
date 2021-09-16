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
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RedisTemplate redisTemplate;

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

    public boolean alreadyPraiseInRedis(int user,int question){
        String key = "praise:"+"question:"+question+":user:"+user;
        if(redisTemplate.hasKey(key)){
            if((Integer)redisTemplate.opsForValue().get(key)==1){
                return true;
            }else {
                return false;
            }
        }else{
            if(praiseMapper.findPraiseCountByUserAndQuestion(user,question) != 0){
                redisTemplate.opsForValue().set(key,1,2,TimeUnit.HOURS);
                return true;
            }else{
                redisTemplate.opsForValue().set(key,0,2,TimeUnit.HOURS);
                return false;
            }
        }
    }

    public int getPraiseCount(int questionId){
        String praiseCountKey = "praiseCount:question:"+questionId;
        if(redisTemplate.hasKey(praiseCountKey)){
            return (Integer)redisTemplate.opsForValue().get(praiseCountKey);
        }else{
            int count = questionMapper.findPraiseCount(questionId);
            redisTemplate.opsForValue().set(praiseCountKey,count,2,TimeUnit.HOURS);
            return count;
        }

    }

    /**
     * redis更新点赞数量至mysql
     */
    public void praiseCountRedisToDb(){
        String prefix = "praiseCount:*";
        Set<String> set = redisTemplate.keys(prefix);
        for (String str : set) {
            String[] s = str.split(":");
            int questionId = Integer.valueOf(s[2]);
            int count = (Integer)redisTemplate.opsForValue().get(str);
            questionMapper.updatePraiseCount(questionId,count);
        }
    }

    public void praiseDetailRedisToDb(){
        String prefix = "praise:*";
        Set<String> set = redisTemplate.keys(prefix);
        for (String str : set) {
            String[] s = str.split(":");
            int questionId = Integer.parseInt(s[2]);
            int userId = Integer.parseInt(s[4]);
            praiseMapper.addPraise(userId,questionId,System.currentTimeMillis(),System.currentTimeMillis());
        }
    }

    public boolean addLikeByRedis(PraiseUserDTO praiseUserDTO){
        int questionId = praiseUserDTO.getQuestion();
        int userId = praiseUserDTO.getUser();
        long createTime = System.currentTimeMillis();
        long updateTime = System.currentTimeMillis();
        int userNotified = questionMapper.findCreatorByQuestion(questionId);
        String key = "praise:"+"question:"+questionId+":user:"+userId;
        String praiseCountKey = "praiseCount:question:"+questionId;
        //判断redis是否有点赞的数据
        if(alreadyPraiseInRedis(userId,questionId)){
            redisTemplate.opsForValue().set(key,0,2,TimeUnit.HOURS);
            if(redisTemplate.hasKey(praiseCountKey)){
                redisTemplate.opsForValue().decrement(praiseCountKey);
            }else{
                int count = praiseMapper.findPraiseCountByUserAndQuestion(userId,questionId);
                redisTemplate.opsForValue().set(praiseCountKey,count-1,2, TimeUnit.HOURS);
            }
            //删除通知
            notificationService.addNotification(userId,userNotified,2,questionId);
        }else{
            redisTemplate.opsForValue().set(key,1,2,TimeUnit.HOURS);
            if(redisTemplate.hasKey(praiseCountKey)){
                redisTemplate.opsForValue().increment(praiseCountKey);
            }else{
                int count = praiseMapper.findPraiseCountByUserAndQuestion(userId,questionId);
                redisTemplate.opsForValue().set(praiseCountKey,count+1,2, TimeUnit.HOURS);

            }
            //添加通知
            notificationService.addNotification(userId,userNotified,2,questionId);
        }
        int count = (Integer)redisTemplate.opsForValue().get(praiseCountKey);
        redisTemplate.opsForValue().set(praiseCountKey,count,2,TimeUnit.HOURS);
        return false;
    }

}
