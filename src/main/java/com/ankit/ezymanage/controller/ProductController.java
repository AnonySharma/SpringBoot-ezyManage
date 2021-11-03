package com.ankit.ezymanage.controller;

import com.ankit.ezymanage.service.ProductService;
import com.ankit.ezymanage.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController extends RootController {
    private final ProductService productService;

    @Autowired
    public ProductController(UserService userService, ProductService productService) {
        super(userService);
        this.productService = productService;
    }

    @GetMapping("/products/")
    public String getProducts(Model model) {
        makeChangesIfAuthenticated(model);
        model.addAttribute("products", productService.getAllProducts());
        return "products";
    }
}
