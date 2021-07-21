package com.community.domain;

import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName comment
 */
@Data
public class Comment implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private Long parentId;

    /**
     * 
     */
    private Integer type;

    /**
     * 
     */
    private Integer commentator;

    /**
     * 
     */
    private Long createTime;

    /**
     * 
     */
    private Long updateTime;

    /**
     * 
     */
    private Integer likeCount;

    /**
     * 评论内容
     */
    private String content;

    private static final long serialVersionUID = 1L;

    private Integer questionId;
}