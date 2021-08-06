package com.community.controller;

import com.community.domain.User;
import com.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @GetMapping("/notificationStatus")
    public String notificationStatus(HttpServletRequest request, Model model){
        User user = (User)request.getSession().getAttribute("user");
        int count = 0;
        if(user!=null){
            count = notificationService.notificationCount(user.getId());
        }
        model.addAttribute("notificationCount",count);
        return null;
    }
}
