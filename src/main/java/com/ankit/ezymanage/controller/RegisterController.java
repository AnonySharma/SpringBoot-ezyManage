package com.ankit.ezymanage.controller;

import javax.servlet.http.HttpSession;

import com.ankit.ezymanage.model.User;
import com.ankit.ezymanage.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RegisterController extends BaseController {
    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        super(userService);
        this.userService = userService;
    }

    @GetMapping("/register/")
    public String registerGoto(Model model) {
        if (isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/";
        }

        System.out.println("go and register!!!!!!");
        model.addAttribute("user", new User());
        isAuthorized(model, "ROLE_USER");
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "register";
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public String handleDuplicateKeyException(Model model, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMsg", "Username not available!");
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "redirect:/register/";
    }

    @PostMapping("/register/")
    public String registerManager(@ModelAttribute("user") User user, Model model, HttpSession httpSession) {
        if (isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/";
        }

        User userToSave = new User(user.getUsername(), user.getPassword());
        userService.saveUser(userToSave);

        isAuthorized(model, "ROLE_USER");
        System.out.println("just registered!!!!!!");
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "redirect:/";
    }
}
