package com.community.controller;

import com.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class InfoController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/info")
    public String toInfo(){

        return "info";
    }

    @PostMapping("/info")
    public String update(HttpServletRequest request, HttpServletResponse response,
                         @RequestParam("username") String username){
        String token = (String) request.getSession().getAttribute("token");
        return "index";
    }
}
