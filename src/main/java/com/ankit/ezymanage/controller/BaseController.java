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

    // Pages constants
    protected static final String FORBIDDEN_ERROR_PAGE = "error/403";
    protected static final String NOT_FOUND_ERROR_PAGE = "error/404";
    protected static final String UNAUTHORIZED_ERROR_PAGE = "error/401";

    // Allowed Role Constants
    protected static final String SHOP_CUSTOMER = "ROLE_USER ROLE_STAFF ROLE_OWNER ROLE_ADMIN";
    protected static final String SHOP_STAFF = "ROLE_STAFF ROLE_OWNER ROLE_ADMIN";
    protected static final String SHOP_OWNER = "ROLE_OWNER ROLE_ADMIN";
    protected static final String ONLY_STAFF = "ROLE_STAFF";

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
            user = userService.getUserByUsername(username);
            model.addAttribute("username", username);
            model.addAttribute("role", user.getRole());
            model.addAttribute("loggedIn", true);

            List<String> permittedRolesList = Arrays.asList(permittedRoles.split(" "));
            List<String> userRolesList = Arrays.asList(user.getRole().split(" "));
            // System.out.println("permittedRolesList: " + permittedRolesList);
            // System.out.println("userRolesList: " + userRolesList);
            for (String string : userRolesList) {
                if (permittedRolesList.contains(string)) {
                    return true;
                }
            }
            return false;
        }

        return false;
    }
}
