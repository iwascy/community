package com.community.domain;

import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String title;
    private String detail;
    private Long createTime;
    private Long updateTime;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer praiseCount;
    private String tag;
}
