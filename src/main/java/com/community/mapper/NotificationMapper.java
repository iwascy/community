package com.community.mapper;

import com.community.domain.Notification;
import com.community.domain.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotificationMapper {

    @Insert("insert into notification (notifier,user_notified,type,status,outer_id,create_time) values (#{notifier},#{userNotified},#{type},#{status},#{outerId},#{createTime})")
    void insertNotification(int notifier,int userNotified,int type,int status,int outerId,long createTime);

    /*
    未读通知数量
     */
    @Select("select count(*) from notification where user_notified = #{userNotified} and status = 0")
    int findNotificationCount(int userNotified);

    @Update("update notification set status = 0 where user_notified = #{userNotified}")
    void updateNotifiedStatus(int userNotified);

    @Select("select type from notification where id = #{notificatioinId}")
    int findTypeById(int notificationId);

    /*
    未读通知
     */
    @Select("select * from notification where user_notified = #{userNotified} and status = 0 order by create_time desc")
    List<Notification> findNotificationNotRead(int userNotified);

    /*
    已读通知
     */
    @Select("select * from notification where user_notified = #{userNotified} order by create_time desc" )
    List<Notification> findNotification(int userNotified);


    @Delete("delete from notification where user_notified = #{userNotified} and type = #{type} and outer_id = #{outerId}")
    void deleteNotification(int notifier,int userNotified,int type,int outerId);


    @Delete("update notification set status = 1 where user_notified = #{userNotified} and status = 0 ")
    void setNotificationStatusTrue(int userNotified);
}
