package com.community.controller;

import com.community.domain.Comment;
import com.community.domain.User;
import com.community.dto.CommentDTO;
import com.community.service.CommentService;
import com.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;

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
