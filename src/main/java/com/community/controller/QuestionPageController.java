package com.community.controller;

import com.community.domain.Question;
import com.community.mapper.QuestionMapper;

import com.community.service.CommentService;
import com.community.service.QuestionService;
import com.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class QuestionPageController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String toQuestion(@PathVariable int id,Model model){

        Question question = questionMapper.findQuestionById(id);
        questionService.addView(id);
        model.addAttribute("question",question);
        model.addAttribute("userService",userService);
        model.addAttribute("comments",commentService.findQuestionComment(id));
        model.addAttribute("commentService",commentService);
        return "question";
    }

}
