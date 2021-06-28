package com.community.controller;

import com.community.domain.Question;
import com.community.domain.User;
import com.community.service.impl.QuestionServiceImpl;
import com.community.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/publish")
public class PublishController {

    @Autowired
    private QuestionServiceImpl questionService;

    @Autowired
    private UserServiceImpl userService;

    @GetMapping()
    public String getPublish(){

        return "publish";
    }

    @PostMapping()
    public String doPublish(HttpServletRequest request, HttpServletResponse httpServletResponse,
                          @RequestParam("title") String title,
                          @RequestParam("tag") String tag,
                          @RequestParam("detail") String detail,
                            Model model){
        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        String token= "";
        if(cookies != null){
            for (Cookie cookie : request.getCookies()) {
                if(cookie.getName().equals("token")){
                    token = cookie.getValue();
                    break;
                }
            }
        }
        User user = userService.findByToken(token);
        if(user == null){
            model.addAttribute("msg","请登录！");
            return "publish";
        }
        Question question = new Question();
        question.setCreator(user.getId());
        question.setTitle(title);
        question.setDetail(detail);
        question.setTag(tag);
        question.setCreateTime(System.currentTimeMillis());
        question.setUpdateTime(question.getCreateTime());
        questionService.insert(question);
        return "redirect:index";
    }
}
