package com.community.service;

import com.community.domain.User;
import com.community.mapper.UserMapper;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    int insert(User user);

    int deleteById(int id);

}
