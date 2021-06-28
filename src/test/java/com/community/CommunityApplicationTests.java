package com.community;

import com.community.domain.User;
import com.community.mapper.UserMapper;
import com.community.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CommunityApplicationTests {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    void contextLoads() {
        User user = userServiceImpl.findById(12);
        System.out.println(user);

    }

}
