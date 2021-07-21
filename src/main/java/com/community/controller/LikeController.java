package com.community.controller;

import com.community.domain.Like;
import com.community.service.LikeService;
import com.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LikeController {

    @Autowired
    private LikeService likeService;

    @Autowired
    private QuestionService questionService;

    @PostMapping("/like")
    public String addLike(@RequestParam("user") int userId,
                          @RequestParam("question") int questionId){
        Like like = new Like();
        like.setId(userId);
        like.setQuestion(questionId);
        like.setCreateTime(System.currentTimeMillis());
        like.setUpdateTime(System.currentTimeMillis());
        if(likeService.addLike(like)){
            questionService.addLikeCount(questionId);
        }else{
            questionService.reduceLikeCount(questionId);
        }
        return null;
    }
}
