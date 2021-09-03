package com.community.dto;


import lombok.Data;

@Data
public class ShowCommentDTO {
    private Long commentId;
    private String commentUser;
    private String avatar;
    private String content;
    private Long time;
    private Integer type;
    private Integer commentUserId;
}
