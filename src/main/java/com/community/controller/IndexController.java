package com.community.controller;


import com.community.domain.Notification;
import com.community.domain.Praise;
import com.community.domain.Question;
import com.community.domain.User;
import com.community.dto.PraiseUserDTO;
import com.community.mapper.QuestionMapper;
import com.community.mapper.UserMapper;
import com.community.service.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    private PraiseService praiseService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private IndexService indexService;

    @GetMapping({"/","/index"})
    public String toIndex(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                          HttpServletRequest request, HttpServletResponse response,
                          HttpSession session, Model model){

        //登录
        User user = userService.findUserByRequest(request);
        if(user != null){
            request.getSession().setAttribute("user",user);
        }
        //分页

        PageInfo pageInfo = indexService.sortByLatestTime(pageNum);
        model.addAttribute("userService",userService);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("info","latest");
        model.addAttribute("praiseService",praiseService);
        return "index";
    }

    @GetMapping("/popular")
    public String newQuestoin(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                              Model model){
        PageInfo pageInfo = indexService.sortByPopular(pageNum);
        model.addAttribute("userService",userService);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("info","popular");
        model.addAttribute("praiseService",praiseService);
        return "index";
    }

    @GetMapping("/follow")
    public String follow(@RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                         HttpServletRequest request,Model model){

        User user = (User) request.getSession().getAttribute("user");
        PageInfo pageInfo = indexService.follow(pageNum,user.getId());
        model.addAttribute("userService",userService);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("info","follow");
        model.addAttribute("praiseService",praiseService);
        return "index";
    }

    @ResponseBody
    @RequestMapping("/praise")
    public Map<String, Object> addPraise(@RequestBody PraiseUserDTO praiseUserDTO,
                                         Model model){
        Praise praise = new Praise();
        int userId = praiseUserDTO.getUser();
        int questionId = praiseUserDTO.getQuestion();
        praise.setUser(userId);
        praise.setQuestion(questionId);
        praise.setCreateTime(System.currentTimeMillis());
        praise.setUpdateTime(System.currentTimeMillis());
        praiseService.addLikeByRedis(praiseUserDTO);
        model.addAttribute("alreadyPraise",praiseService.alreadyPraiseInRedis(praise.getUser(), praise.getQuestion()));
        Map<String,Object> result = new HashMap<>();
        int count = questionService.praiseCountById(questionId);
        result.put("count",count);
        return result;
    }

    @ResponseBody
    @PostMapping("/praiseStatus")
    public String praiseStatus(@RequestBody PraiseUserDTO praiseUserDTO,
                               Model model){
        model.addAttribute("status",praiseService.alreadyPraise(praiseUserDTO.getUser(), praiseUserDTO.getQuestion()));
        return null;
    }

}
