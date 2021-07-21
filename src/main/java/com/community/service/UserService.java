package com.community.service;

import com.community.domain.User;
import com.community.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User findUserByRequest(HttpServletRequest request){
        String token = "";
        if(request.getCookies() != null){
            for (Cookie cookie : request.getCookies()) {
                if(cookie.getName().equals("token")){
                    token = cookie.getValue();
                    break;
                }
            }
        }
        User user = userMapper.findByToken(token);
        return user;
    }

    public String findUserNameById(int id){


        return userMapper.findUserNameById(id);
    }

    public String findAvatarById(int id){
        return userMapper.findAvatarById(id);
    }
}
