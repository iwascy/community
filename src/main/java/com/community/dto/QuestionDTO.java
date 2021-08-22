package com.community.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionDTO {
    private String title;
    private String author;
    private long time;
    private String detail;
    private int questionId;
    private String avatar;
    private int creatorId;
    private int commentCount;
    private List<ShowCommentDTO> firstShowCommentDTOList;
    private List<ShowCommentDTO> secondShowCommentDTOList;
}
