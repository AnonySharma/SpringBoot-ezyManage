package com.ankit.ezymanage.controller;

import com.ankit.ezymanage.model.User;
import com.ankit.ezymanage.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController extends BaseController {
    public LoginController(UserService userService) {
        super(userService);
    }

    @GetMapping("/login/")
    public String loginGoto(Model model) {
        if (isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/";
        }
        System.out.println("go and login!!!!!!");
        isAuthorized(model, "ROLE_USER");
        model.addAttribute("user", new User());
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "login";
    }

    @GetMapping("/verify")
    public String verifyEmail(@RequestParam("token") String token, RedirectAttributes redirectAttributes) {
        String username = userService.getUsernameByVerificationToken(token);
        System.out.println(token + " " + username);
        if (username != null) {
            userService.updateUserVerificationStatus(username, true);
            redirectAttributes.addFlashAttribute("successMsg", "Your email has been verified, you can now login.");
            return "redirect:/login/";
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "Invalid token");
            return "redirect:/";
        }
    }

    @GetMapping("/loggedin/")
    public String loginManager(Model model, RedirectAttributes redirectAttributes) {
        if (!isLoggedIn())
            return PAGE_NOT_FOUND_ERROR_PAGE;
        System.out.println(userService.findLoggedInUsername());
        System.out.println(userService.getUserByUsername(userService.findLoggedInUsername()));

        if (!userService.getUserByUsername(userService.findLoggedInUsername()).isVerified()) {
            redirectAttributes.addFlashAttribute("warningMsg",
                    "Please check your email, and verify your account first!");
            return "redirect:/logout/";
        }
        redirectAttributes.addFlashAttribute("successMsg", "Welcome!");
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "redirect:/";
    }

    @GetMapping("/loggedout/")
    public String logoutManager(Model model, RedirectAttributes redirectAttributes) {
        if (isLoggedIn())
            return PAGE_NOT_FOUND_ERROR_PAGE;
        System.out.println("Logged out!");
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "redirect:/";
    }
}
