package com.community.controller;

import com.community.domain.User;
import com.community.dto.NotificationDTO;
import com.community.service.FollowService;
import com.community.service.NotificationService;
import com.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;


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

    @GetMapping("/people/{id}/notification")
    public String notification(@PathVariable("id") int id,
                               Model model){
        List<NotificationDTO> notificationDTOList = notificationService.notificationContent(id);
        model.addAttribute("userService",userService);
        model.addAttribute("followService",followService);
        model.addAttribute("people",id);
        model.addAttribute("notificationDTOList",notificationDTOList);
        model.addAttribute("nav","notification");
        return "notification";
    }
    @ResponseBody
    @GetMapping("/notification/{id}")
    public String readNotification(@PathVariable("id") int id,
                                   @RequestParam("status") boolean status){
        if(status == true){
            notificationService.readNotification(id);
            return "true";
        }
        return "false";
    }
}
