package com.community.service;

import com.community.domain.Notification;
import com.community.dto.NotificationDTO;
import com.community.enums.NotificationEnum;
import com.community.mapper.NotificationMapper;
import com.community.mapper.QuestionMapper;
import com.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public void addNotification(int notifier,int userNotified,int type,int outerId){
        String uuid = "notification:user:"+userNotified;
        boolean hasKey = redisTemplate.hasKey(uuid);
        int count = 0;
        notificationMapper.insertNotification(notifier,userNotified,type,0,outerId,System.currentTimeMillis());
        if(hasKey){
            redisTemplate.delete(uuid);
            count = notificationMapper.findNotificationCount(userNotified);
            redisTemplate.opsForValue().set(uuid,count,2l, TimeUnit.HOURS);
        }else{
            count = notificationMapper.findNotificationCount(userNotified);
            redisTemplate.opsForValue().set(uuid,count,2l, TimeUnit.HOURS);
        }
    }

    public List<NotificationDTO> notificationContent(int user){
        List<NotificationDTO> notificationDTOList = new ArrayList();
        List<Notification> notificationList = notificationMapper.findNotification(user);
        int notificationCount = notificationList.size();  //未读通知数量
        for (int i = 0; i < notificationCount; i++) {
            Notification notification = notificationList.get(i);
            int type = notification.getType();
            String title = "";
            if(type == 3){
                title = "你";
            }else{
                title = questionMapper.findTitleById(notification.getOuterId());
            }
            long time = notification.getCreateTime();
            String replyType = NotificationEnum.typeString(type);
            int userNotified = notification.getUserNotified();
            NotificationDTO notificationDTO= new NotificationDTO();
            notificationDTO.setNotifier(notification.getNotifier());
            notificationDTO.setQuestionId(notification.getOuterId());
            notificationDTO.setNotifierName(userMapper.findUserNameById(userNotified));
            notificationDTO.setTitle(title);
            notificationDTO.setTime(time);
            notificationDTO.setType(replyType);
            notificationDTOList.add(notificationDTO);
        }
        return notificationDTOList;
    }

    public int notificationCount(int user){
        String uuid = "notification:user:"+user;
        boolean hasKey = redisTemplate.hasKey(uuid);
        int count = 0;
        if(hasKey){
            count = (int)redisTemplate.opsForValue().get(uuid);
        }else{
            count = notificationMapper.findNotificationCount(user);
            redisTemplate.opsForValue().set(uuid,count,2l, TimeUnit.HOURS);
        }
        return count;
    }

    public void readNotification(int user){
        String uuid = "notification:user:"+user;
        redisTemplate.opsForValue().set(uuid,0);
        notificationMapper.setNotificationStatusTrue(user);
    }
}
