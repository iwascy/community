package com.community;

import com.community.domain.User;
import com.community.mapper.UserMapper;
import com.community.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommunityApplicationTests {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    void contextLoads() {
        User user = new User(3,"jack","32","2323",1234,13213);
        int result = userServiceImpl.insert(user);
        System.out.println(result);

    }

}
