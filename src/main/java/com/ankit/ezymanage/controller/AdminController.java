package com.ankit.ezymanage.controller;

import java.util.Arrays;
import java.util.List;

import com.ankit.ezymanage.model.Product;
import com.ankit.ezymanage.model.Shop;
import com.ankit.ezymanage.model.User;
import com.ankit.ezymanage.service.ProductService;
import com.ankit.ezymanage.service.ShopService;
import com.ankit.ezymanage.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController extends BaseController {
    private final UserService userService;
    private final ShopService shopService;
    private final ProductService productService;

    @Autowired
    public AdminController(UserService userService, ShopService shopService, ProductService productService) {
        super(userService);
        this.userService = userService;
        this.shopService = shopService;
        this.productService = productService;
    }

    @GetMapping("/admin/")
    public String adminHome(Model model) {
        isAuthorized(model, "ROLE_USER");
        List<User> userList = userService.getAllUsers();
        List<Shop> shopList = shopService.getAllShops();
        List<Product> productList = productService.getAllProducts();

        System.out.println(shopList.toString());
        System.out.println("Opened Admin panel!");
        model.addAttribute("userList", userList);
        model.addAttribute("shopList", shopList);
        model.addAttribute("productList", productList);
        return "admin";
    }

    @GetMapping("/admin/users/delete/{username}/")
    public String deleteUser(@PathVariable(value = "username") String username, Model model,
            RedirectAttributes redirectAttributes) {
        isAuthorized(model, "ROLE_USER");
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

    @GetMapping("/admin/shops/new/")
    public String addShop(Model model) {
        isAuthorized(model, "ROLE_USER");
        model.addAttribute("shop", new Shop());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("postLink", "/admin/shops/new/");
        return "new_shop";
    }

    @PostMapping("/admin/shops/new/")
    public String addShop(@ModelAttribute("shop") Shop shop, Model model, RedirectAttributes redirectAttributes) {
        isAuthorized(model, "ROLE_USER");
        System.out.println("Adding new shop!");
        if (shopService.createShop(shop)) {
            System.out.println("Added new shop!");
            redirectAttributes.addFlashAttribute("successMsg", "Added new shop!");
            return "redirect:/admin/";
        } else {
            System.out.println("Failed to add new shop!");
            redirectAttributes.addFlashAttribute("errorMsg", "Failed to add new shop!");
            return "redirect:/admin/shops/new/";
        }
    }

    @GetMapping("/admin/products/new/")
    public String addProduct(Model model) {
        isAuthorized(model, "ROLE_USER");
        model.addAttribute("product", new Product());
        model.addAttribute("shops", shopService.getAllShops());
        model.addAttribute("postLink", "/admin/products/new/");
        return "new_product";
    }

    @PostMapping("/admin/products/new/")
    public String addProduct(@ModelAttribute("product") Product product, Model model,
            RedirectAttributes redirectAttributes) {
        isAuthorized(model, "ROLE_USER");
        System.out.println("Adding new product!");
        if (productService.addProduct(product)) {
            System.out.println("Added new product!");
            redirectAttributes.addFlashAttribute("successMsg", "Added new product!");
            return "redirect:/admin/";
        } else {
            System.out.println("Failed to add new product!");
            redirectAttributes.addFlashAttribute("errorMsg", "Failed to add new product!");
            return "redirect:/admin/products/new/";
        }
    }

    @GetMapping("/admin/shops/delete/{id}/")
    public String deleteShop(@PathVariable(value = "id") int id, Model model, RedirectAttributes redirectAttributes) {
        isAuthorized(model, "ROLE_USER");
        System.out.println(userService.findLoggedInUsername() + " deleting " + id);

        System.out.println("Deleting shop!");
        shopService.deleteShop(id);
        System.out.println("Deleted shop!");

        return "redirect:/admin/";
    }

    @GetMapping("/admin/products/delete/{id}/")
    public String deleteProduct(@PathVariable(value = "id") int id, Model model,
            RedirectAttributes redirectAttributes) {
        isAuthorized(model, "ROLE_USER");
        System.out.println(userService.findLoggedInUsername() + " deleting " + id);

        System.out.println("Deleting product!");
        productService.deleteProduct(id);
        System.out.println("Deleted product!");

        return "redirect:/admin/";
    }

    @GetMapping("/admin/users/edit/{username}/")
    public String editUserRoles(@PathVariable("username") String username, Model model) {
        isAuthorized(model, "ROLE_USER");
        User user = userService.getUserByUsername(username);
        List<String> allRoles = Arrays.asList("ROLE_ADMIN", "ROLE_OWNER", "ROLE_STAFF", "ROLE_USER");
        List<String> currentRoles = userService.getUserRoles(username);
        model.addAttribute("user", user);
        model.addAttribute("currentRoles", currentRoles);
        model.addAttribute("allRoles", allRoles);
        return "edit_user_roles";
    }

    @GetMapping("/admin/users/edit/{username}/roles/add/{role}")
    public String addRole(@PathVariable("username") String username, @PathVariable("role") String role, Model model,
            RedirectAttributes redirectAttributes) {
        isAuthorized(model, "ROLE_USER");
        System.out.println("Adding role to user!");
        if (userService.addRoleToUser(username, role)) {
            System.out.println("Added role to user!");
            redirectAttributes.addFlashAttribute("successMsg", "Added role to user!");
        } else {
            System.out.println("Failed to add role to user!");
            redirectAttributes.addFlashAttribute("errorMsg", "Failed to add role to user!");
        }
        return "redirect:/admin/";
    }

    @GetMapping("/admin/users/edit/{username}/roles/remove/{role}")
    public String removeRole(@PathVariable("username") String username, @PathVariable("role") String role, Model model,
            RedirectAttributes redirectAttributes) {
        isAuthorized(model, "ROLE_USER");
        System.out.println("Removing role to user!");
        if (userService.removeRoleFromUser(username, role)) {
            System.out.println("Removed role from user!");
            redirectAttributes.addFlashAttribute("successMsg", "Removed role from user!");
        } else {
            System.out.println("Failed to remove role from user!");
            redirectAttributes.addFlashAttribute("errorMsg", "Failed to remove role from user!");
        }
        return "redirect:/admin/";
    }
}
