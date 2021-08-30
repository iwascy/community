package com.community.service;

import com.community.domain.Question;
import com.community.dto.QuestionProfileDTO;
import com.community.mapper.QuestionMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionConvert{

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserService userService;

    public PageInfo getPageInfo(List<Question> questionList, int pageNum){
        List<QuestionProfileDTO> questionProfileDTOList = new ArrayList<>();
        PageInfo pageInfo = new PageInfo(questionList);
        for (Question question : questionList) {
            QuestionProfileDTO questionProfileDTO = new QuestionProfileDTO();
            questionProfileDTO.setId(question.getId());
            questionProfileDTO.setName(userService.findUserNameById(question.getCreator()));
            questionProfileDTO.setCommentCount(question.getCommentCount());
            questionProfileDTO.setPraiseCount(question.getPraiseCount());
            questionProfileDTO.setTitle(question.getTitle());
            int len = question.getDetail().length();
            if (len > 100) {
                questionProfileDTO.setDetail(question.getDetail().substring(1, 100) + "......");
            } else {
                questionProfileDTO.setDetail(question.getDetail());
            }

            questionProfileDTO.setTag(questionProfileDTO.getTag());
            questionProfileDTOList.add(questionProfileDTO);
        }
        pageInfo.setList(questionProfileDTOList);
        return pageInfo;

    }
}
