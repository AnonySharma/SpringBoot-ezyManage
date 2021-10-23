package com.ankit.ezymanage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    @RequestMapping("/")
    public ModelAndView home() {
        System.out.println("Hiiiii");
        return new ModelAndView("home");
    }

    @RequestMapping("/error/")
    public String errorManager() {
        System.out.println("ERRRORRR !!!!!!");
        // System.out.println(model.toString());
        return "redirect:/error";
    }
}
