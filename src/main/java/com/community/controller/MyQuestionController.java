package com.community.controller;


import com.community.domain.User;
import com.community.mapper.QuestionMapper;
import com.community.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class MyQuestionController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/myquestion")
    public String toMyQuestion(HttpServletRequest request, HttpServletResponse response,
                               HttpSession session, Model model,
                               @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                               @RequestParam(value = "pageSize",defaultValue = "5") int pageSize){
        String token = "";
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            for (Cookie cookie : request.getCookies()) {
                if(cookie.getName().equals("token")){
                    token = cookie.getValue();
                    break;
                }
            }
        }
        User user = userMapper.findByToken(token);
        int creator = user.getId();
        PageHelper.startPage(pageNum,pageSize);
        PageInfo pageInfo = new PageInfo(questionMapper.findQuestionsByCreator(creator));
        model.addAttribute("pageInfo",pageInfo);
        return "myquestion";
    }
}
