package com.community.service;

import com.community.domain.Notification;
import com.community.dto.NotificationDTO;
import com.community.enums.NotificationEnum;
import com.community.mapper.NotificationMapper;
import com.community.mapper.QuestionMapper;
import com.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;

    public void addNotification(int notifier,int userNotified,int type,int outerId){
        notificationMapper.insertNotification(notifier,userNotified,type,0,outerId,System.currentTimeMillis());
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
        return notificationMapper.findNotificationCount(user);
    }

    public void readNotification(int user){
        notificationMapper.setNotificationStatusTrue(user);
    }
}
