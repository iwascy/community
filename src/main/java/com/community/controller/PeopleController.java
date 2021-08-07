package com.community.controller;

import com.community.domain.Question;
import com.community.domain.User;
import com.community.dto.NotificationDTO;
import com.community.dto.PeopleInfoDTO;
import com.community.service.FollowService;
import com.community.service.NotificationService;
import com.community.service.QuestionService;
import com.community.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PeopleController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/people/{accountId}")
    public String people(@PathVariable("accountId") int id,
                         Model model){
        PageHelper.startPage(1,50);
        PageInfo pageInfo = new PageInfo(questionService.findQuestionByUser(id));
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("userService",userService);
        model.addAttribute("followService",followService);
        model.addAttribute("people",id);
        model.addAttribute("nav","people");
        return "people";
    }

    @ResponseBody
    @PostMapping("/addFollow")
    public String Follow(@RequestParam int user,
                         @RequestParam int userFollowed){
        followService.follow(user,userFollowed);
        return "";
    }

}
