package com.ankit.ezymanage.controller;

import com.ankit.ezymanage.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class RootController {
    private final UserService userService;

    @Autowired
    public RootController(UserService userService) {
        this.userService = userService;
    }

    public void makeChangesIfAuthenticated(Model model) {
        String username = userService.findLoggedInUsername();
        if (username != null) {
            model.addAttribute("username", username);
            model.addAttribute("loggedIn", true);
        } else {
            model.addAttribute("loggedIn", false);
        }
    }
}
