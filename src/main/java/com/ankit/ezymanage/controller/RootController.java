package com.ankit.ezymanage.controller;

import com.ankit.ezymanage.service.SecurityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class RootController {
    private final SecurityService securityService;

    @Autowired
    public RootController(SecurityService securityService) {
        this.securityService = securityService;
    }

    public void makeChangesIfAuthenticated(Model model) {
        String username = securityService.findLoggedInUsername();
        if (username != null) {
            model.addAttribute("username", username);
            model.addAttribute("loggedIn", true);
        } else {
            model.addAttribute("loggedIn", false);
        }

    }
}
