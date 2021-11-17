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
        System.out.println(isLoggedIn());
        if (isLoggedIn()) {
            System.out.println("Welcome to Home page!");
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);

            return "home";
        }
        System.out.println("Welcome to Landing page!");
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "landing";
    }

    @GetMapping("/payments/")
    public String payments(Model model) {
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "payments";
    }

    @GetMapping("/terms/")
    public String terms(Model model) {
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "terms_and_conditions";
    }

    @GetMapping("/contact/")
    public String contact(Model model) {
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "contact_us";
    }

    @GetMapping("/about/")
    public String about(Model model) {
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "about_us";
    }
}
