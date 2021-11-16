package com.ankit.ezymanage.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ankit.ezymanage.model.OwnerRequest;
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
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_ADMIN))
            return FORBIDDEN_ERROR_PAGE;
        List<User> userList = userService.getAllUsers();
        List<Shop> shopList = shopService.getAllShops();
        List<Product> productList = productService.getAllProducts();

        System.out.println(shopList.toString());
        System.out.println("Opened Admin panel!");
        model.addAttribute("userList", userList);
        model.addAttribute("shopList", shopList);
        model.addAttribute("productList", productList);
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "admin";
    }

    @GetMapping("/admin/users/delete/{username}/")
    public String deleteUser(@PathVariable(value = "username") String username, Model model,
            RedirectAttributes redirectAttributes) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }

        if (!isAuthorized(model, ROLE_ABOVE_ADMIN))
            return FORBIDDEN_ERROR_PAGE;
        System.out.println(userService.findLoggedInUsername() + " deleting " + username);
        if (userService.findLoggedInUsername().equals(username)) {
            System.out.println("Cant delete yourself. Lol!");
            redirectAttributes.addFlashAttribute("errorMsg", "Can't delete yourself. Lol!");
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/admin/";
        }

        System.out.println("Deleting user!");
        userService.deleteUser(username);
        System.out.println("Deleted user!");

        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "redirect:/admin/";
    }

    @GetMapping("/admin/users/edit/{username}/")
    public String editUserRoles(@PathVariable("username") String username, Model model) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_ADMIN))
            return FORBIDDEN_ERROR_PAGE;
        User user = userService.getUserByUsername(username);
        List<String> allRoles = Arrays.asList("ROLE_ADMIN", "ROLE_OWNER", "ROLE_STAFF", "ROLE_USER");
        List<String> currentRoles = userService.getUserRoles(username);
        model.addAttribute("user", user);
        model.addAttribute("currentRoles", currentRoles);
        model.addAttribute("allRoles", allRoles);
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "edit_user_roles";
    }

    @GetMapping("/admin/users/edit/{username}/roles/add/{role}")
    public String addRole(@PathVariable("username") String username, @PathVariable("role") String role, Model model,
            RedirectAttributes redirectAttributes) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_ADMIN))
            return FORBIDDEN_ERROR_PAGE;
        System.out.println("Adding role to user!");
        if (userService.addRoleToUser(username, role)) {
            System.out.println("Added role to user!");
            redirectAttributes.addFlashAttribute("successMsg", "Added role to user!");
        } else {
            System.out.println("Failed to add role to user!");
            redirectAttributes.addFlashAttribute("errorMsg", "Failed to add role to user!");
        }
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "redirect:/admin/";
    }

    @GetMapping("/admin/users/edit/{username}/roles/remove/{role}")
    public String removeRole(@PathVariable("username") String username, @PathVariable("role") String role, Model model,
            RedirectAttributes redirectAttributes) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_ADMIN))
            return FORBIDDEN_ERROR_PAGE;
        System.out.println("Removing role to user!");
        if (userService.removeRoleFromUser(username, role)) {
            System.out.println("Removed role from user!");
            redirectAttributes.addFlashAttribute("successMsg", "Removed role from user!");
        } else {
            System.out.println("Failed to remove role from user!");
            redirectAttributes.addFlashAttribute("errorMsg", "Failed to remove role from user!");
        }
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "redirect:/admin/";
    }

    @GetMapping("/admin/products/new/")
    public String addProduct(Model model) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_ADMIN))
            return FORBIDDEN_ERROR_PAGE;
        model.addAttribute("product", new Product());
        model.addAttribute("shops", shopService.getAllShops());
        model.addAttribute("postLink", "/admin/products/new/");
        model.addAttribute("backLink", "/admin/");
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "new_product";
    }

    @PostMapping("/admin/products/new/")
    public String addProduct(@ModelAttribute("product") Product product, Model model,
            RedirectAttributes redirectAttributes) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_ADMIN))
            return FORBIDDEN_ERROR_PAGE;
        System.out.println("Adding new product!");
        if (productService.addProduct(product)) {
            System.out.println("Added new product!");
            redirectAttributes.addFlashAttribute("successMsg", "Added new product!");
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/admin/";
        } else {
            System.out.println("Failed to add new product!");
            redirectAttributes.addFlashAttribute("errorMsg", "Failed to add new product!");
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/admin/products/new/";
        }
    }

    @GetMapping("/admin/products/delete/{id}/")
    public String deleteProduct(@PathVariable(value = "id") int id, Model model,
            RedirectAttributes redirectAttributes) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_ADMIN))
            return FORBIDDEN_ERROR_PAGE;
        System.out.println(userService.findLoggedInUsername() + " deleting " + id);

        System.out.println("Deleting product!");
        productService.deleteProduct(id);
        redirectAttributes.addFlashAttribute("successMsg", "Deleted product successfully!");
        System.out.println("Deleted product!");

        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "redirect:/admin/";
    }

    @GetMapping("/admin/shops/new/")
    public String addShop(Model model) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_ADMIN))
            return FORBIDDEN_ERROR_PAGE;
        model.addAttribute("shop", new Shop());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("postLink", "/admin/shops/new/");
        model.addAttribute("backLink", "/admin/");
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "new_shop";
    }

    @PostMapping("/admin/shops/new/")
    public String addShop(@ModelAttribute("shop") Shop shop, Model model, RedirectAttributes redirectAttributes) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_ADMIN))
            return FORBIDDEN_ERROR_PAGE;
        System.out.println("Adding new shop!");
        if (shopService.createShop(shop)) {
            System.out.println("Added new shop!");
            redirectAttributes.addFlashAttribute("successMsg", "Added new shop!");
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/admin/";
        } else {
            System.out.println("Failed to add new shop!");
            redirectAttributes.addFlashAttribute("errorMsg", "Failed to add new shop!");
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/admin/shops/new/";
        }
    }

    @GetMapping("/admin/shops/delete/{id}/")
    public String deleteShop(@PathVariable(value = "id") int id, Model model, RedirectAttributes redirectAttributes) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_ADMIN))
            return FORBIDDEN_ERROR_PAGE;
        System.out.println(userService.findLoggedInUsername() + " deleting " + id);

        System.out.println("Deleting shop!");
        shopService.deleteShop(id);
        System.out.println("Deleted shop!");

        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "redirect:/admin/";
    }

    @GetMapping("/admin/shops/requests/")
    public String getShopRequests(Model model) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_ADMIN))
            return FORBIDDEN_ERROR_PAGE;

        List<OwnerRequest> allRequests = userService.getAllActiveOwnerRequests();
        Map<Integer, String> userMap = new HashMap<>();
        for (OwnerRequest request : allRequests) {
            userMap.put(request.getUserId(), userService.getUserById(request.getUserId()).getUsername());
        }
        model.addAttribute("allRequests", allRequests);
        model.addAttribute("userMap", userMap);
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "shop_requests";
    }

    @GetMapping("/admin/shops/requests/allow/{id}/")
    public String allowShopRequest(@PathVariable(value = "id") int id, Model model,
            RedirectAttributes redirectAttributes) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_ADMIN))
            return FORBIDDEN_ERROR_PAGE;
        System.out.println(userService.findLoggedInUsername() + " allowing " + id);

        System.out.println("Allowing shop request!");
        String username = userService.getUserById(id).getUsername();
        userService.addRoleToUser(username, ONLY_OWNER);
        userService.updateStatusOwnerRequest(id, "allowed");
        System.out.println("Allowed shop request!");
        model.addAttribute("infoMsg", "Allowed shop request!");

        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "redirect:/admin/shops/requests/";
    }

    @GetMapping("/admin/shops/requests/deny/{id}/")
    public String denyShopRequest(@PathVariable(value = "id") int id, Model model,
            RedirectAttributes redirectAttributes) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_ADMIN))
            return FORBIDDEN_ERROR_PAGE;
        System.out.println(userService.findLoggedInUsername() + " denying " + id);

        System.out.println("Denying shop request!");
        userService.updateStatusOwnerRequest(id, "denied");
        System.out.println("Denied shop request!");
        model.addAttribute("infoMsg", "Denied shop request!");

        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "redirect:/admin/shops/requests/";
    }
}
