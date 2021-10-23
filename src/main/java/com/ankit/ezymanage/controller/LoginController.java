package com.ankit.ezymanage.controller;

import javax.servlet.http.HttpSession;

import com.ankit.ezymanage.model.User;
import com.ankit.ezymanage.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login/")
    public ModelAndView loginGoto(Model model) {
        System.out.println("go and login!!!!!!");
        model.addAttribute("user", new User());
        return new ModelAndView("login");
    }

    @PostMapping("/login/")
    public String loginManager(@ModelAttribute("user") User user, Model model, HttpSession httpSession) {
        System.out.println(model.toString());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());

        User userToLogin = new User(user.getUsername(), user.getPassword());
        System.out.println(userToLogin.toString());
        // userService.login(userToLogin);

        System.out.println("just Logged in!!!!!!");
        return "redirect:/";
    }
}
