package com.community.dto;

import lombok.Data;

@Data
public class QuestionProfileDTO {
    private Integer id;
    private String name;
    private String title;
    private String detail;
    private Integer commentCount;
    private Integer viewCount;
    private Integer praiseCount;
    private String tag;
}
