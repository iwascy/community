package com.community.controller;


import com.community.domain.Question;
import com.community.mapper.QuestionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EditQuestionController {

    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/question/{id}/edit")
    public String toEdit(@PathVariable int id,Model model){
        Question question = questionMapper.findQuestionById(id);
        model.addAttribute("question",question);
        return "edit";
    }

    @PostMapping("/question/{id}/edit")
    public String doEdit(@PathVariable("id") int id,
                         @RequestParam("title") String newTitle,
                         @RequestParam("detail") String newDetail,
                         @RequestParam("tag") String newTag, Model model){
        questionMapper.updateQuestionById(newTitle,newTag,newDetail,System.currentTimeMillis(),id);
        return "redirect:/question/"+id;
    }
}
