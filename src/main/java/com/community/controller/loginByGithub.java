package com.community.controller;


import com.community.domain.User;
import com.community.dto.AccessTokenDTO;
import com.community.dto.GithubUser;
import com.community.provider.GihubProvider;
import com.community.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.GitProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Controller
public class loginByGithub {

    @Autowired
    private GihubProvider gihubProvider;

    @Autowired
    UserServiceImpl userService;

    @Value("${gihub.client.secret}")
    private String client_secret;

    @Value("${github.redirect.rui}")
    private String redirect_uri;

    @Value("${github.client.id}")
    private String client_id;


    @GetMapping("/callback")
    public String callback(@RequestParam("code") String code,
                           @RequestParam("state") String state,
                           HttpServletRequest request){
        try {
            AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
            accessTokenDTO.setCode(code);
            accessTokenDTO.setState(state);
            accessTokenDTO.setRedirect_uri(redirect_uri);
            accessTokenDTO.setClient_id(client_id);
            accessTokenDTO.setClient_secret(client_secret);
            String accessToken = gihubProvider.getAccessToken(accessTokenDTO);
            GihubProvider gihubProvider = new GihubProvider();
            GithubUser githubUser = gihubProvider.getUser(accessToken);
            if(githubUser != null){
                //success
                //将用户信息存储到数据库中git
                User user = new User();
                user.setName(githubUser.getName());
                //唯一用户标识
                user.setToken(UUID.randomUUID().toString());
                user.setAccountId(String.valueOf(githubUser.getId()));
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                userService.insert(user);
                request.getSession().setAttribute("user",githubUser);
                return "redirect:index";
            }else{
                request.setAttribute("error","登录失败，请重新登录。");
                return "redirect:index";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "index";
    }
}
