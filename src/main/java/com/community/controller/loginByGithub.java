package com.community.controller;


import com.community.dto.AccessTokenDTO;
import com.community.dto.GithubUser;
import com.community.provider.GihubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.GitProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
public class loginByGithub {

    @Autowired
    private GihubProvider gihubProvider;

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
            GithubUser user = gihubProvider.getUser(accessToken);
            if(user != null){
                //success
                request.getSession().setAttribute("user",user);
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
