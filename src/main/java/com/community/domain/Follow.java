package com.community.domain;

import lombok.Data;

@Data
public class Follow {
    private Integer id;
    private Integer user;
    private Integer userFollowed;
    private Long createTime;
    private Long updateTime;
}
