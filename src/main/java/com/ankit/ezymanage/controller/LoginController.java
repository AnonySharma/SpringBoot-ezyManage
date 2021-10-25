package com.ankit.ezymanage.controller;

import com.ankit.ezymanage.model.User;
import com.ankit.ezymanage.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController extends RootController {
    public LoginController(UserService userService) {
        super(userService);
    }

    @GetMapping("/login/")
    public ModelAndView loginGoto(Model model) {
        System.out.println("go and login!!!!!!");
        makeChangesIfAuthenticated(model);
        model.addAttribute("user", new User());
        return new ModelAndView("login");
    }
}
