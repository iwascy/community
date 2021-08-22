package com.community.controller;

import com.community.domain.Question;
import com.community.domain.User;
import com.community.dto.CommentDTO;
import com.community.dto.QuestionDTO;
import com.community.mapper.QuestionMapper;

import com.community.service.CommentService;
import com.community.service.QuestionService;
import com.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class QuestionController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String toQuestion(@PathVariable int id,Model model){

        Question question = questionMapper.findQuestionById(id);
        questionService.addView(id);
        model.addAttribute("comments",commentService.findQuestionComment(id));
        model.addAttribute("commentService",commentService);

        model.addAttribute("questionDTO",questionService.showQuestion(id));
        return "question";
    }

    @GetMapping("/question/{id}/delete")
    public String deleteQuestion(@PathVariable("id") int questionId){
        questionService.delete(questionId);
        return "redirect:/index";
    }

    @PostMapping("/question/{id}/comment")
    public String addComment(@RequestBody CommentDTO commentDTO,
                             @PathVariable("id") int id){
        commentService.addComment(commentDTO);
        return "redirect:/question/"+id;
    }

    @PostMapping("/question/{id}/like")
    public void like(@PathVariable("id") int id, HttpSession session){
        User user = (User)session.getAttribute("user");

        commentService.addLike(id);
    }

}
