package com.community.dto;

import com.community.mapper.QuestionMapper;
import com.community.mapper.UserMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
public class IndexQuestionDTO {
    private String author;
    private String title;
    private String detail;
    private int viewCount;
}
