package com.ankit.ezymanage.controller;

import java.util.Arrays;
import java.util.List;

import com.ankit.ezymanage.model.User;
// import com.ankit.ezymanage.utils.Constants.*;
import com.ankit.ezymanage.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class BaseController {
    protected final UserService userService;
    protected User user;

    @Autowired
    public BaseController(UserService userService) {
        this.userService = userService;
    }

    public boolean isLoggedIn() {
        return userService.findLoggedInUsername() != null;
    }

    public boolean isAuthorized(Model model, String permittedRoles) {
        String username = userService.findLoggedInUsername();
        if (username != null) {
            user = userService.getUser(username);
            model.addAttribute("username", username);
            model.addAttribute("role", user.getRole());
            model.addAttribute("loggedIn", true);

            List<String> permittedRolesList = Arrays.asList(permittedRoles.split(" "));
            return permittedRolesList.contains(user.getRole());
        }

        return false;
    }
}
