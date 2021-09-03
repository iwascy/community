package com.community.domain;

import lombok.Data;

@Data
public class Praise {
    private Integer id;
    private Integer user;
    private Integer question;
    private Long createTime;
    private Long updateTime;
}
