package com.community.dto;

import lombok.Data;

@Data
public class QuestionProfileDTO {
    private int id;
    private String name;
    private String title;
    private String detail;
    private int commentCount;
    private int viewCount;
    private int praiseCount;
    private String tag;
}
