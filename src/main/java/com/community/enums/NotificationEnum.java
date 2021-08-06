package com.community.enums;

public enum NotificationEnum {
    REPLY_QUESTION(1,"回复了问题"),
    PRAISE_QUESTION(2,"点赞了问题"),
    FOLLOW_USER(3,"关注了");


    private int type;
    private String typeString;

    NotificationEnum(int type, String typeString) {
        this.type = type;
        this.typeString = typeString;
    }

    public int getType() {
        return type;
    }

    public String getTypeString() {
        return typeString;
    }

    public static String typeString(int type){
        NotificationEnum[] notificationEnums = NotificationEnum.values();
        for(NotificationEnum notificationEnum : notificationEnums){
            if(notificationEnum.getType() == type){
                return notificationEnum.getTypeString();
            }
        }
        return "";
    }
}
