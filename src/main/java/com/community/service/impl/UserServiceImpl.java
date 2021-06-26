package com.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.community.domain.User;
import com.community.service.UserService;
import com.community.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
implements UserService{

}




