package com.community.controller;

import com.community.mapper.QuestionMapper;
import com.community.service.TagService;
import com.community.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TagController {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private TagService tagService;

    @GetMapping("/tag/{tag}")
    public String page(Model model,
                       @RequestParam(value = "pageNum",defaultValue = "1") int pageNum,
                       @PathVariable(value = "tag") String tag){
        PageHelper.startPage(pageNum,10);
        PageInfo pageInfo = new PageInfo(tagService.getTagQuestion(tag));
        model.addAttribute("tagString",tagService.tagToChinese(tag));
        model.addAttribute("tag",tag);
        model.addAttribute("userService",userService);
        model.addAttribute("pageInfo",pageInfo);
        return "tag";
    }
}
