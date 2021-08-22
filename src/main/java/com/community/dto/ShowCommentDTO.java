package com.community.dto;


import lombok.Data;

@Data
public class ShowCommentDTO {
    private long commentId;
    private String commentUser;
    private String avatar;
    private String content;
    private long time;
    private int type;
    private int commentUserId;
}
