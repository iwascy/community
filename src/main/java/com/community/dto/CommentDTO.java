package com.community.dto;


import lombok.Data;

@Data
public class CommentDTO {
    private Integer questionId;
    private String content;
    private Integer type;
    private Integer commentator;
}
