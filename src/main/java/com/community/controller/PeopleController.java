package com.community.controller;

import com.community.domain.Question;
import com.community.domain.User;
import com.community.service.QuestionService;
import com.community.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PeopleController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @GetMapping("/people/{accountId}")
    public String people(@PathVariable("accountId") int id,
                         Model model){
        PageHelper.startPage(1,50);
        PageInfo pageInfo = new PageInfo(questionService.findQuestionByUser(id));
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("userService",userService);
        return "people";
    }
}
