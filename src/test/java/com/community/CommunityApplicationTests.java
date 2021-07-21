package com.community;

import com.community.domain.Question;
import com.community.domain.User;
import com.community.mapper.QuestionMapper;
import com.community.mapper.UserMapper;
import com.community.service.CommentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Stack;

@SpringBootTest
class CommunityApplicationTests {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    CommentService commentService;

    @Test
    void findTime(){
        int a = sumNums(3);
        System.out.println(a);
    }

    int res = 0;
    public int sumNums(int n) {
        boolean x = (n < 0) || (sumNums(n - 1) > 0);
        res += n;
        return res;
    }
}
