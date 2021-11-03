package com.ankit.ezymanage.controller;

import com.ankit.ezymanage.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController extends RootController {
    @Autowired
    public HomeController(UserService userService) {
        super(userService);
    }

    @RequestMapping({ "/", "", "/home" })
    public String home(Model model) {
        System.out.println("Hiiiii");
        makeChangesIfAuthenticated(model);
        return "home";
    }

    @RequestMapping("/error/")
    public String errorManager(Model model) {
        makeChangesIfAuthenticated(model);
        System.out.println("ERRRORRR !!!!!!");
        return "error";
    }
}
