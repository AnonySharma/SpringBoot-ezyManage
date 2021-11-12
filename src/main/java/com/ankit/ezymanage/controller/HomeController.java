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

    // My orders
    @GetMapping("/myorders/")
    public String myOrders(Model model) {
        isLoggedIn();
        System.out.println("Welcome to My Orders!");
        isAuthorized(model, "ROLE_USER");
        List<Order> orders = orderService.getAllOrdersByUserId(user.getId());
        List<Pair<Order, String>> myOrderList = new ArrayList<>();
        for (Order order : orders) {
            String products = "";
            for (Pair<Integer, Integer> product : order.getItems()) {
                products += productService.getProduct(product.getFirst()).getName() + "(x" + product.getSecond()
                        + "), ";
            }
            products = products.substring(0, products.length() - 2);
            myOrderList.add(new Pair<>(order, products));
        }
        Map<Integer, String> shopMap = new HashMap<>();
        for (Pair<Order, String> order : myOrderList) {
            shopMap.put(order.getFirst().getShopId(), shopService.getShopById(order.getFirst().getShopId()).getName());
        }
        model.addAttribute("shopMap", shopMap);
        model.addAttribute("orders", myOrderList);
        return "my_orders";
    }

    @GetMapping("/myorders/{orderId}/")
    public String pastOrderItem(@PathVariable("orderId") int orderId, Model model) {
        Order order = orderService.getOrderByOrderId(orderId);
        model.addAttribute("orderId", orderId);
        model.addAttribute("shopName",
                shopService.getShopById(orderService.getOrderByOrderId(orderId).getShopId()).getName());
        model.addAttribute("customerName", userService.getUserById(order.getCustomerId()).getUsername());
        model.addAttribute("staffName", userService.getUserById(order.getStaffId()).getUsername());
        model.addAttribute("order", order);

        List<Pair<Product, Integer>> orderedItems = new ArrayList<>();

        for (Pair<Integer, Integer> product : order.getItems()) {
            orderedItems.add(new Pair<Product, Integer>(productService.getProduct(product.getFirst().intValue()),
                    product.getSecond()));
        }
        model.addAttribute("orderedItems", orderedItems);
        model.addAttribute("backLink", "/myorders/");
        return "order_page";
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
