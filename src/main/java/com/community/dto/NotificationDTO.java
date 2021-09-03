package com.community.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private String title;
    private Long time;
    private String notifierName;
    private String type;
    private Integer notifier;
    private Integer questionId;
}
