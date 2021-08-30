package com.community.service;

import com.community.CommunityApplication;
import com.community.domain.Question;
import com.community.dto.QuestionProfileDTO;
import com.community.mapper.QuestionMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class IndexService {

    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserService userService;

    @Autowired
    private QuestionConvert questionConvert;

    public PageInfo sortByLatestTime(int pageNum) {
        PageHelper.startPage(pageNum,5);
        List<Question> questionList = questionMapper.sortByLatestTime();
        PageInfo pageInfo = questionConvert.getPageInfo(questionList,pageNum);
        return pageInfo;
    }

    public PageInfo sortByPopular(int pageNum) {
        PageHelper.startPage(pageNum,5);
        List<Question> questionList = questionMapper.sortByCommentCount();
        PageInfo pageInfo = questionConvert.getPageInfo(questionList,pageNum);
        return pageInfo;
    }

    public PageInfo follow(int pageNum,int user) {
        PageHelper.startPage(pageNum,5);
        PageInfo pageInfo = questionConvert.getPageInfo(questionMapper.findQuestionByFollow(user),pageNum);
        return pageInfo;
    }

}
