package com.community.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private String title;
    private long time;
    private String notifierName;
    private String type;
    private int notifier;
    private int questionId;
}
