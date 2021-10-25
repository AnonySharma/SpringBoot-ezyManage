package com.ankit.ezymanage.controller;

import com.ankit.ezymanage.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController extends RootController {
    @Autowired
    public HomeController(UserService userService) {
        super(userService);
    }

    @RequestMapping({ "/", "", "home" })
    public ModelAndView home(Model model) {
        System.out.println("Hiiiii");
        makeChangesIfAuthenticated(model);
        return new ModelAndView("home");
    }

    @RequestMapping("/error/")
    public String errorManager(Model model) {
        makeChangesIfAuthenticated(model);
        System.out.println("ERRRORRR !!!!!!");
        // System.out.println(model.toString());
        return "redirect:/error";
    }
}
