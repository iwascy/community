package com.community.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionDTO {
    private String title;
    private String author;
    private Long time;
    private String detail;
    private Integer questionId;
    private String avatar;
    private Integer creatorId;
    private Integer commentCount;
    private Integer viewCount;
    private List<ShowCommentDTO> firstShowCommentDTOList;
    private List<ShowCommentDTO> secondShowCommentDTOList;
}
