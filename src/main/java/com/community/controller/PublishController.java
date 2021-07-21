package com.community.controller;

import com.community.domain.Question;
import com.community.domain.User;
import com.community.mapper.QuestionMapper;
import com.community.mapper.UserMapper;
import com.community.service.QuestionService;
import com.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/publish")
public class PublishController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @GetMapping()
    public String getPublish(){

        return "publish";
    }

    @PostMapping()
    public String doPublish(HttpServletRequest request, HttpServletResponse httpServletResponse,
                          @RequestParam("title") String title,
                          @RequestParam("tag") String tag,
                          @RequestParam("detail") String detail,
                            Model model){
        User user = userService.findUserByRequest(request);
        if(user == null){
            model.addAttribute("msg","请登录！");
            return "publish";
        }
        questionService.PublishQuestion(user,title,detail,tag);
        return "redirect:index";
    }
}
