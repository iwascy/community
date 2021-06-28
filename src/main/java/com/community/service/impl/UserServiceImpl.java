package com.community.service.impl;

import com.community.domain.User;
import com.community.mapper.UserMapper;
import com.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public int deleteById(int id) {
        return userMapper.deleteById(id);
    }

    @Override
    public User findByToken(String token){
        return userMapper.findByToken(token);
    }

    @Override
    public User findById(int id) {
        return userMapper.findById(id);
    }
}
