package com.ankit.ezymanage.controller;

import com.ankit.ezymanage.service.ProductService;
import com.ankit.ezymanage.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController extends BaseController {
    private final ProductService productService;

    @Autowired
    public ProductController(UserService userService, ProductService productService) {
        super(userService);
        this.productService = productService;
    }

    @GetMapping("/products/")
    public String getProducts(Model model) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_ADMIN))
            return FORBIDDEN_ERROR_PAGE;
        model.addAttribute("products", productService.getAllProducts());
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "products";
    }
}
