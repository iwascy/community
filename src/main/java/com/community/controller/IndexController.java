package com.community.controller;


import com.community.domain.User;
import com.community.mapper.UserMapper;
import com.community.service.impl.UserServiceImpl;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping({"/","/index"})
    public String toIndex(HttpServletRequest request, HttpServletResponse response, HttpSession session){
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
        User user = userService.findByToken(token);
        if(user != null){
            request.getSession().setAttribute("user",user);
        }
        return "index";
    }

    @GetMapping("/test")
    public String test(){

        return "test";
    }
}
