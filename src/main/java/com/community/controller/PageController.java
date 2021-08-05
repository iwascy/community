package com.community.controller;


import com.community.domain.User;
import com.community.mapper.QuestionMapper;
import com.community.mapper.UserMapper;
import com.community.service.PraiseService;
import com.community.service.QuestionService;
import com.community.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class PageController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private PraiseService praiseService;

    @GetMapping("/index/page={pageNum}")
    public String newPage(HttpServletRequest request, HttpServletResponse response,
                          HttpSession session, Model model,
                          @PathVariable(value = "pageNum",required = false) int pageNum){

        //分页
        PageHelper.startPage(pageNum,5);
        PageInfo pageInfo = new PageInfo(questionService.sortByLatestTime());
        model.addAttribute("userService",userService);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("info","latest");
        model.addAttribute("praiseService",praiseService);
        return "index";
    }

    @GetMapping("/popular/page={pageNum}")
    public String popularPage(HttpServletRequest request, HttpServletResponse response,
                          HttpSession session, Model model,
                          @PathVariable(value = "pageNum",required = false) int pageNum){

        //分页
        PageHelper.startPage(pageNum,5);
        PageInfo pageInfo = new PageInfo(questionService.sortByPopular());
        model.addAttribute("userService",userService);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("info","popular");
        model.addAttribute("praiseService",praiseService);
        return "/index";
    }

    @GetMapping("/follow/page={pageNum}")
    public String followPage(HttpServletRequest request, HttpServletResponse response,
                              HttpSession session, Model model,
                              @PathVariable(value = "pageNum",required = false) int pageNum){

        //分页
        PageHelper.startPage(pageNum,5);
        User user = (User) request.getSession().getAttribute("user");
        PageInfo pageInfo = new PageInfo(questionService.findQuestionByFollow(user.getId()));
        model.addAttribute("userService",userService);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("info","follow");
        model.addAttribute("praiseService",praiseService);
        return "/index";
    }
}
