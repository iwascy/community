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
public class SearchService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserService userService;

    public PageInfo search(int pageNum,String detail) {
        String condition = detail.replace(" ","|");
        PageHelper.startPage(pageNum,5);
        List<Question> questionList = questionMapper.search(condition);
        PageInfo page = new PageInfo(questionList);

        List<QuestionProfileDTO> questionProfileDTOList = new ArrayList<>();
        for (Question question : (List<Question>) page.getList()) {
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
        page.setList(questionProfileDTOList);
        return page;
    }
}
