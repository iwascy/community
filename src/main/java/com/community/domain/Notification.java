package com.community.domain;

import lombok.Data;

@Data
public class Notification {
    private int id;
    private int notifier;
    private int userNotified;
    private int type;
    private int status;
    private int outerId;
    private long createTime;
}
