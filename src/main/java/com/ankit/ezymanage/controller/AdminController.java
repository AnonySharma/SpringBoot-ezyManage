package com.ankit.ezymanage.controller;

import java.util.List;

import com.ankit.ezymanage.model.User;
import com.ankit.ezymanage.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AdminController extends RootController {
    private UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        super(userService);
        this.userService = userService;
    }

    @RequestMapping("/admin/")
    public ModelAndView adminHome(Model model) {
        List<User> userList = userService.getUsers();
        System.out.println(userList);
        makeChangesIfAuthenticated(model);
        model.addAttribute("userList", userList);
        return new ModelAndView("admin");
    }
}
