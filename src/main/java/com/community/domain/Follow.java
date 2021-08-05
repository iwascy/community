package com.community.domain;

import lombok.Data;

@Data
public class Follow {
    private int id;
    private int user;
    private int userFollowed;
    private long createTime;
    private long updateTime;
}
