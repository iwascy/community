package com.community.service;

import com.community.domain.Question;
import com.community.dto.QuestionProfileDTO;
import com.community.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserService userService;

    public List<QuestionProfileDTO> search(String detail) {
        String condition = detail.replace(" ","|");
        List<Question> questionList = questionMapper.search(condition);
        List<QuestionProfileDTO> questionProfileDTOList = new ArrayList<>();
        for (Question question : questionList) {
            QuestionProfileDTO questionProfileDTO = new QuestionProfileDTO();
            questionProfileDTO.setId(question.getId());
            questionProfileDTO.setName(userService.findUserNameById(question.getCreator()));
            questionProfileDTO.setCommentCount(question.getCommentCount());
            questionProfileDTO.setPraiseCount(questionProfileDTO.getPraiseCount());
            questionProfileDTO.setTitle(question.getTitle());
            questionProfileDTO.setDetail(question.getDetail());
            questionProfileDTO.setTag(questionProfileDTO.getTag());
            questionProfileDTOList.add(questionProfileDTO);
        }
        return questionProfileDTOList;
    }
}
