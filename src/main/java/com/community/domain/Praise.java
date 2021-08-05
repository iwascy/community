package com.community.domain;

import lombok.Data;

@Data
public class Praise {
    private int id;
    private int user;
    private int question;
    private long createTime;
    private long updateTime;
}
