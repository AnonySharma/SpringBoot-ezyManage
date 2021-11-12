package com.ankit.ezymanage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ankit.ezymanage.model.Order;
import com.ankit.ezymanage.model.Product;
import com.ankit.ezymanage.service.OrderService;
import com.ankit.ezymanage.service.ProductService;
import com.ankit.ezymanage.service.ShopService;
import com.ankit.ezymanage.service.UserService;
import com.ankit.ezymanage.utils.Pair;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController extends BaseController {
    private OrderService orderService;
    private ShopService shopService;
    private ProductService productService;

    @Autowired
    public HomeController(UserService userService, OrderService orderService, ShopService shopService,
            ProductService productService) {
        super(userService);
        this.orderService = orderService;
        this.shopService = shopService;
        this.productService = productService;
    }

    @GetMapping({ "/", "" })
    public String home(Model model) {
        isLoggedIn();
        System.out.println("Welcome to Home!");
        isAuthorized(model, "ROLE_USER");
        return "home";
    }

    @GetMapping("/payments/")
    public String payments(Model model) {
        isAuthorized(model, "ROLE_USER");
        return "payments";
    }

    @GetMapping("/customers/")
    public String customers(Model model) {
        isAuthorized(model, "ROLE_USER");
        return "customers";
    }
}
