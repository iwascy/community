package com.community.controller;


import com.community.domain.User;
import com.community.dto.AccessTokenDTO;
import com.community.dto.GithubUserDTO;
import com.community.mapper.UserMapper;
import com.community.provider.GihubProvider;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Controller
public class LoginByGithubController {

    @Autowired
    private GihubProvider gihubProvider;

    @Autowired
    UserMapper userMapper;

    @Value("${gihub.client.secret}")
    private String client_secret;

    @Value("${github.redirect.rui}")
    private String redirect_uri;

    @Value("${github.client.id}")
    private String client_id;


    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        try {
            AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
            accessTokenDTO.setCode(code);
            accessTokenDTO.setState(state);
            accessTokenDTO.setRedirect_uri(redirect_uri);
            accessTokenDTO.setClient_id(client_id);
            accessTokenDTO.setClient_secret(client_secret);
            String accessToken = gihubProvider.getAccessToken(accessTokenDTO);
            //GihubProvider gihubProvider = new GihubProvider();
            GithubUserDTO githubUserDTO = gihubProvider.getUser(accessToken);
            if(githubUserDTO != null){
                //success
                //将用户信息存储到数据库中git
                User user = new User();
                //唯一用户标识
                String token = UUID.randomUUID().toString();
                //判断用户表是否有该用户
                System.out.println(githubUserDTO.getId());
                if(userMapper.getAccountIdCount(githubUserDTO.getId()) > 0){
                    //修改token
                    userMapper.updateTokenByAccountId(token, githubUserDTO.getId());
                }else{
                    //增加用户
                    if(githubUserDTO.getName()==null){
                        githubUserDTO.setName(githubUserDTO.getLogin());
                    }
                    user.setName(githubUserDTO.getName());
                    user.setToken(token);
                    user.setAccountId(githubUserDTO.getId());
                    user.setAvatar(githubUserDTO.getAvatarUrl());
                    user.setCreateTime(System.currentTimeMillis());
                    user.setUpdateTime(user.getCreateTime());
                    userMapper.insert(user);
                }
                //登录成功，写入cookie
                response.addCookie(new Cookie("token",token));
                return "redirect:/";
            }else{
                request.setAttribute("error","登录失败，请重新登录。");
                return "redirect:/";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return "redirect:/index";
    }
}
