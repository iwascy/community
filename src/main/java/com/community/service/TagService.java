package com.community.service;

import com.community.domain.Question;
import com.community.dto.QuestionProfileDTO;
import com.community.enums.TagEnum;
import com.community.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserService userService;

    public String tagToChinese(String tag){
        return TagEnum.getTagString(tag);
    }

    public List<QuestionProfileDTO> getTagQuestion(String tag){
        List<Question> questionList = questionMapper.questionOfTag(tag);
        List<QuestionProfileDTO> questionProfileDTOList = new ArrayList<>();
        for (Question question : questionList) {
            QuestionProfileDTO questionProfileDTO = new QuestionProfileDTO();
            questionProfileDTO.setTag(tag);
            int detailLength = question.getDetail().length();
            if(detailLength > 40){
                questionProfileDTO.setDetail(question.getDetail().substring(0,40));
            }else{
                questionProfileDTO.setDetail(question.getDetail());
            }
            questionProfileDTO.setId(question.getId());
            questionProfileDTO.setName(userService.findUserNameById(question.getCreator()));
            questionProfileDTO.setPraiseCount(question.getPraiseCount());
            questionProfileDTO.setCommentCount(question.getCommentCount());
            questionProfileDTO.setViewCount(questionProfileDTO.getViewCount());
            questionProfileDTOList.add(questionProfileDTO);
        }
        return questionProfileDTOList;
    }
}
