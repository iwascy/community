package com.community.domain;

import lombok.Data;

@Data
public class Notification {
    private Integer id;
    private Integer notifier;
    private Integer userNotified;
    private Integer type;
    private Integer status;
    private Integer outerId;
    private Long createTime;
}
