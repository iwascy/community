package com.community.controller;


import com.community.domain.User;
import com.community.service.SearchService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;

@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping("/search")
    public String search(@RequestParam("detail") String detail,
                         @RequestParam(value = "pageNum",required = false,defaultValue = "1") int pageNum,
                         Model model){
        PageHelper.startPage(pageNum,10);
        PageInfo pageInfo = new PageInfo(searchService.search(detail));
        model.addAttribute("info","没找到："+detail);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("detail",detail);
        return "/search";
    }


}
