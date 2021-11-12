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
            return "redirect:/";
        }

        System.out.println("go and register!!!!!!");
        model.addAttribute("user", new User());
        isAuthorized(model, "ROLE_USER");
        return "register";
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public String handleDuplicateKeyException(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("errorMsg", "Username not available!");
        return "redirect:/register/";
    }

    @PostMapping("/register/")
    public String registerManager(@ModelAttribute("user") User user, Model model, HttpSession httpSession) {
        if (isLoggedIn()) {
            return "redirect:/";
        }

        System.out.println(model.toString());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());

        User userToSave = new User(user.getUsername(), user.getPassword());
        System.out.println(userToSave.toString());
        userService.saveUser(userToSave);

        isAuthorized(model, "ROLE_USER");
        System.out.println("just registered!!!!!!");
        return "redirect:/";
    }
}
