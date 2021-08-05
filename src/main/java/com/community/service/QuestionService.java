package com.community.service;

import com.community.domain.Question;
import com.community.domain.User;
import com.community.dto.IndexQuestionDTO;
import com.community.mapper.QuestionMapper;
import com.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;


    public void PublishQuestion(User user,String title,String detail,String tag){
        Question question = new Question();
        question.setCreator(user.getId());
        question.setTitle(title);
        question.setDetail(detail);
        question.setTag(tag);
        question.setCreateTime(System.currentTimeMillis());
        question.setUpdateTime(question.getCreateTime());
        questionMapper.insert(question);
    }

    public List<IndexQuestionDTO> questionIndex(){

        List<IndexQuestionDTO> indexQuestionDTOList = new ArrayList<>();

        return indexQuestionDTOList;
    }

    public void addView(int id) {
        questionMapper.addViewCount(id);
    }

    public List<Question> sortByLatestTime(){
        return questionMapper.sortByLatestTime();
    }

    public List<Question> sortByPopular(){
        return questionMapper.sortByCommentCount();
    }

    public List<Question> findQuestionByUser(int id){
        return questionMapper.findQuestionsByCreator(id);
    }


    public void addPraiseCount(int questionId){
        questionMapper.addPraiseCount(questionId);
    }

    public void reducePraiseCount(int questionId){
        questionMapper.reducePraiseCount(questionId);
    }

    public void delete(int questionId) {
        questionMapper.deleteById(questionId);
    }

    public List<Question> findQuestionByFollow(int user){
        return questionMapper.findQuestionByFollow(user);
    }

    public int praiseCountById(int id){
        return questionMapper.findPraiseCount(id);
    };
}
