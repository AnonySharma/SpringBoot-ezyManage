package com.ankit.ezymanage.controller;

import com.ankit.ezymanage.model.User;
import com.ankit.ezymanage.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class RootController {
    protected final UserService userService;
    protected User user;

    @Autowired
    public RootController(UserService userService) {
        this.userService = userService;
    }

    public void makeChangesIfAuthenticated(Model model) {
        String username = userService.findLoggedInUsername();
        if (username != null) {
            user = userService.getUser(username);
            model.addAttribute("username", username);
            model.addAttribute("role", user.getRole());
            model.addAttribute("loggedIn", true);
        } else {
            model.addAttribute("loggedIn", false);
        }
    }
}
