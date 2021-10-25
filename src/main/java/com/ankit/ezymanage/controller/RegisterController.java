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
public class RegisterController extends RootController {
    private final UserService userService;

    @Autowired
    public RegisterController(UserService userService) {
        super(userService);
        this.userService = userService;
    }

    @RequestMapping("/register/")
    public ModelAndView registerGoto(Model model) {
        System.out.println("go and register!!!!!!");
        model.addAttribute("user", new User());
        makeChangesIfAuthenticated(model);
        return new ModelAndView("register");
    }

    @PostMapping("/register/")
    public String registerManager(@ModelAttribute("user") User user, Model model, HttpSession httpSession) {
        System.out.println(model.toString());
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());

        User userToSave = new User(user.getUsername(), user.getPassword());
        System.out.println(userToSave.toString());
        userService.saveUser(userToSave);

        makeChangesIfAuthenticated(model);
        System.out.println("just registered!!!!!!");
        return "redirect:/";
    }
}
