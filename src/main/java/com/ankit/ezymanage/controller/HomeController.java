package com.ankit.ezymanage.controller;

import com.ankit.ezymanage.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController extends BaseController {
    @Autowired
    public HomeController(UserService userService) {
        super(userService);
    }

    @GetMapping({ "/", "" })
    public String home(Model model) {
        isLoggedIn();
        System.out.println("Welcome to Home!");
        isAuthorized(model, "ROLE_USER");
        return "home";
    }

    @GetMapping("/payments/")
    public String payments(Model model) {
        isAuthorized(model, "ROLE_USER");
        return "payments";
    }

    @GetMapping("/customers/")
    public String customers(Model model) {
        isAuthorized(model, "ROLE_USER");
        return "customers";
    }
}
