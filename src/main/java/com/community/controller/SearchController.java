package com.community.controller;


import com.community.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SearchController {
    @Autowired
    private SearchService searchService;


}
