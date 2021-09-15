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
import java.util.Arrays;
import java.util.List;
import java.util.Set;
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
        String key = "notification:notifier:"+notifier+":userNotified:"+userNotified+":type:"+type+":outerId:"+outerId;
        boolean hasKey = redisTemplate.hasKey(key);
        if(hasKey){
            redisTemplate.delete(key);
//            if(redisTemplate.hasKey(uuid)){
//                int count = (Integer)redisTemplate.opsForValue().get(uuid);
//                if(count > 0){
//                    redisTemplate.opsForValue().decrement(uuid);
//                }
//            }else{
//                notificationMapper.deleteNotification(notifier,userNotified,type,outerId);
//                redisTemplate.opsForValue().set(uuid,notificationMapper.findNotificationCount(userNotified),2,TimeUnit.HOURS);
//            }
            redisTemplate.expire(uuid,2l, TimeUnit.HOURS);
        }else{
            //notificationMapper.insertNotification(notifier,userNotified,type,0,outerId,System.currentTimeMillis());
            //count = notificationMapper.findNotificationCount(userNotified);
            //redisTemplate.opsForValue().set(uuid,count,2l, TimeUnit.HOURS);
            //redisTemplate.opsForValue().set(key,1,2,TimeUnit.HOURS);
            if(notificationMapper.selectNotificationCount(notifier,userNotified,type,outerId)!=0){
                notificationMapper.deleteNotification(notifier,userNotified,type,outerId);
//                if(redisTemplate.hasKey(uuid)){
//                    redisTemplate.opsForValue().decrement(uuid);
//                }else{
//                    redisTemplate.opsForValue().set(uuid,notificationMapper.findNotificationCount(userNotified)-1,2,TimeUnit.HOURS);
//                }
            }else{
                notificationMapper.insertNotification(notifier,userNotified,type,0,outerId,System.currentTimeMillis());
                if(redisTemplate.hasKey(uuid)){
                    redisTemplate.opsForValue().increment(uuid);
                }else{
                    redisTemplate.opsForValue().set(uuid,notificationMapper.findNotificationCount(userNotified)+1,2,TimeUnit.HOURS);
                }
            }
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
        if(redisTemplate.hasKey(uuid)){
            redisTemplate.opsForValue().set(uuid,0);
        }else{
            notificationMapper.setNotificationStatusTrue(user);
        }

    }

    public void updateNotificationDetailToDb(){
        String prefix = "notification:notifier:*";
        Set<String> set = redisTemplate.keys(prefix);
        for (String str : set) {
            String[] s = str.split(":");
            int notifier = Integer.parseInt(s[2]);
            int userNotified = Integer.parseInt(s[4]);
            int type = Integer.parseInt(s[6]);
            int outerId = Integer.parseInt(s[8]);
            if(notificationMapper.selectNotificationCount(notifier,userNotified,type,outerId) == 0){
                notificationMapper.insertNotification(notifier,userNotified,type,0,outerId,System.currentTimeMillis());
            }
        }
    }
}
