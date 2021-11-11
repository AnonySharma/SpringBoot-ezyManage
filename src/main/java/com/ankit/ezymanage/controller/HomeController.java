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
        isAuthorized(model, "ROLE_USER");
        System.out.println("Hiiiii");
        return "home";
    }

    @GetMapping("/error/")
    public String errorManager(Model model) {
        isAuthorized(model, "ROLE_USER");
        System.out.println("ERRRORRR !!!!!!");
        return "errors/404";
    }

    @GetMapping("/401/")
    public String unauthorizedManager(Model model) {
        System.out.println("UNAUTHORIZED");
        return "errors/401";
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
