package com.community.controller;


import com.community.domain.Question;
import com.community.domain.User;
import com.community.mapper.QuestionMapper;
import com.community.mapper.UserMapper;
import com.community.service.PraiseService;
import com.community.service.QuestionService;
import com.community.service.UserService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    private PraiseService praiseService;

    @GetMapping({"/","/index"})
    public String toIndex(HttpServletRequest request, HttpServletResponse response,
                          HttpSession session, Model model){

        //登录
        User user = userService.findUserByRequest(request);
        if(user != null){
            request.getSession().setAttribute("user",user);
        }
        //分页
        PageHelper.startPage(1,5);
        PageInfo pageInfo = new PageInfo(questionService.sortByLatestTime());
        model.addAttribute("userService",userService);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("info","latest");
        model.addAttribute("praiseService",praiseService);
        return "index";
    }

    @GetMapping("/popular")
    public String newQuestoin(Model model){
        PageHelper.startPage(1,5);
        PageInfo pageInfo = new PageInfo(questionService.sortByPopular());
        model.addAttribute("userService",userService);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("info","popular");
        model.addAttribute("praiseService",praiseService);
        return "index";
    }

    @GetMapping("/follow")
    public String follow(HttpServletRequest request,Model model){
        PageHelper.startPage(1,5);
        User user = (User) request.getSession().getAttribute("user");
        PageInfo pageInfo = new PageInfo(questionService.findQuestionByFollow(user.getId()));
        model.addAttribute("userService",userService);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("info","follow");
        model.addAttribute("praiseService",praiseService);
        return "index";
    }
}
