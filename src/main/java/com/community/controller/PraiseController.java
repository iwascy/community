package com.community.controller;

import com.community.domain.Praise;
import com.community.dto.PraiseUserDTO;
import com.community.service.PraiseService;
import com.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PraiseController {

    @Autowired
    private PraiseService praiseService;

    @Autowired
    private QuestionService questionService;

    @ResponseBody
    @RequestMapping("/praise")
    public Map<String, Object> addPraise(@RequestBody PraiseUserDTO praiseUserDTO,
                                         Model model){
        Praise praise = new Praise();
        int userId = praiseUserDTO.getUser();
        int questionId = praiseUserDTO.getQuestion();
        praise.setUser(userId);
        praise.setQuestion(questionId);
        praise.setCreateTime(System.currentTimeMillis());
        praise.setUpdateTime(System.currentTimeMillis());
        if(praiseService.addPraise(praise)){
            questionService.addPraiseCount(questionId);
        }else{
            questionService.reducePraiseCount(questionId);
        }
        model.addAttribute("alreadyPraise",praiseService.alreadyPraise(praise.getUser(), praise.getQuestion()));
        Map<String,Object> result = new HashMap<>();
        int count = questionService.praiseCountById(questionId);
        result.put("count",count);
        return result;
    }

    @ResponseBody
    @PostMapping("/praiseStatus")
    public String praiseStatus(@RequestBody PraiseUserDTO praiseUserDTO,
                               Model model){
        model.addAttribute("status",praiseService.alreadyPraise(praiseUserDTO.getUser(), praiseUserDTO.getQuestion()));
        return null;
    }
}
