package com.ankit.ezymanage.controller;

import com.ankit.ezymanage.model.User;
import com.ankit.ezymanage.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController extends BaseController {
    public LoginController(UserService userService) {
        super(userService);
    }

    @GetMapping("/login/")
    public String loginGoto(Model model) {
        if (isLoggedIn()) {
            return "redirect:/";
        }

        System.out.println("go and login!!!!!!");
        isAuthorized(model, "ROLE_USER");
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/loggedin/")
    public String loginManager(Model model, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("successMsg", "Welcome!");
        return "redirect:/";
    }

    @GetMapping("/loggedout/")
    public String logoutManager(Model model, RedirectAttributes redirectAttributes) {
        System.out.println("Logged out!");
        redirectAttributes.addFlashAttribute("successMsg", "Logged out successfully!");
        return "redirect:/";
    }
}
