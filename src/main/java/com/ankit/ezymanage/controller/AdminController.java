package com.ankit.ezymanage.controller;

import java.util.List;

import com.ankit.ezymanage.model.User;
import com.ankit.ezymanage.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @RequestMapping("/admin/delete/{username}/")
    public String deleteUser(@PathVariable(value = "username") String username, Model model,
            RedirectAttributes redirectAttributes) {
        System.out.println(userService.findLoggedInUsername() + " deleting " + username);
        if (userService.findLoggedInUsername().equals(username)) {
            System.out.println("Cant delete yourself. Lol!");
            redirectAttributes.addFlashAttribute("errorMsg", "Can't delete yourself. Lol!");
            return "redirect:/admin/";
        }

        System.out.println("Deleting user!");
        userService.deleteUser(username);
        System.out.println("Deleted user!");

        return "redirect:/admin/";
    }
}
