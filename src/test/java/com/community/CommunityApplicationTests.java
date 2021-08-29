package com.community;

import com.community.domain.Question;
import com.community.domain.User;
import com.community.dto.QuestionProfileDTO;
import com.community.mapper.QuestionMapper;
import com.community.mapper.UserMapper;
import com.community.service.CommentService;
import com.community.service.IndexService;
import com.community.service.QuestionService;
import com.community.service.SearchService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Stack;

@SpringBootTest
class CommunityApplicationTests {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    CommentService commentService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private SearchService searchService;

    @Autowired
    private IndexService indexService;

    @Test
    void redisTest(){
    }

}
