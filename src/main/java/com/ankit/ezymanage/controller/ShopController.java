package com.ankit.ezymanage.controller;

import java.util.ArrayList;
import java.util.List;

import com.ankit.ezymanage.model.Product;
import com.ankit.ezymanage.model.Order;
import com.ankit.ezymanage.model.Shop;
import com.ankit.ezymanage.service.OrderService;
import com.ankit.ezymanage.service.ProductService;
import com.ankit.ezymanage.service.ShopService;
import com.ankit.ezymanage.service.UserService;
import com.ankit.ezymanage.utils.Pair;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ShopController extends BaseController {
    private final UserService userService;
    private final ShopService shopService;
    private final ProductService productService;
    private final OrderService orderService;

    @Autowired
    public ShopController(UserService userService, ShopService shopService, ProductService productService,
            OrderService orderService) {
        super(userService);
        this.shopService = shopService;
        this.userService = userService;
        this.productService = productService;
        this.orderService = orderService;
    }

    @GetMapping("/myshops/")
    public String myShops(Model model) {
        isAuthorized(model, "ROLE_USER");
        String owner = userService.findLoggedInUsername();
        System.out.println("user: " + user);
        List<Shop> shops = shopService.getAllShopsUnder(owner);
        model.addAttribute("owner", owner);
        model.addAttribute("shops", shops);
        return "my_shops";
    }

    @GetMapping("/newshop/")
    public String newShop(Model model) {
        isAuthorized(model, "ROLE_USER");
        String owner = userService.findLoggedInUsername();
        Shop shop = new Shop();
        shop.setOwner(owner);
        System.out.println("Initially " + shop.toString());
        model.addAttribute("shop", shop);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("postLink", "/newshop/");
        return "new_shop";
    }

    @PostMapping("/newshop/")
    public String newShopPost(@ModelAttribute("shop") Shop shop, Model model) {
        isAuthorized(model, "ROLE_USER");
        System.out.println("Creating shop!");
        System.out.println(shop.toString());

        // TODO: no need to set again
        String owner = userService.findLoggedInUsername();
        shop.setOwner(owner);
        shopService.createShop(shop);
        return "redirect:/myshops/";
    }

    @GetMapping("/shops/{shopId}/")
    public String dashboard(@PathVariable("shopId") int shopId, Model model) {
        isAuthorized(model, "ROLE_USER");
        System.out.println("Opened shop!");
        model.addAttribute("shop", shopService.getShopById(shopId));
        model.addAttribute("shopId", shopId);
        return "dashboard";
    }

    @GetMapping("/shops/{shopId}/products/")
    public String productsPerShop(@PathVariable("shopId") int shopId, Model model) {
        isAuthorized(model, "ROLE_USER");
        System.out.println("Opened products under shop!");
        model.addAttribute("shop", shopService.getShopById(shopId));
        model.addAttribute("shopId", shopId);
        model.addAttribute("products", shopService.getProductsByShop(shopId));
        return "products_per_shop";
    }

    @GetMapping("/shops/{shopId}/products/add/")
    public String showAllProducts(@PathVariable("shopId") int shopId, Model model) {
        isAuthorized(model, "ROLE_USER");
        System.out.println("Listing all product!");

        List<Integer> addedProducts = new ArrayList<>();
        for (Product product : shopService.getProductsByShop(shopId)) {
            addedProducts.add(product.getId());
        }

        model.addAttribute("productList", productService.getAllProducts());
        model.addAttribute("addedProducts", addedProducts);
        model.addAttribute("shopId", shopId);
        return "allProducts";
    }

    @GetMapping("/shops/{shopId}/products/add/{productId}/")
    public String addProduct(@PathVariable("shopId") int shopId, @PathVariable("productId") int productId,
            Model model) {
        isAuthorized(model, "ROLE_USER");
        System.out.println("Listing a product!");
        shopService.addProductToShop(shopId, productId);
        return "redirect:/shops/" + shopId + "/products/add/";
    }

    @GetMapping("/shops/{shopId}/products/remove/{productId}/")
    public String unlistProduct(@PathVariable("shopId") int shopId, @PathVariable("productId") int productId,
            Model model) {
        isAuthorized(model, "ROLE_USER");
        System.out.println("Unlisting a product!");
        shopService.removeProductFromShop(shopId, productId);
        return "redirect:/shops/" + shopId + "/products/";
    }

    // PAST ORDERS
    @GetMapping("/shops/{shopId}/orders/")
    public String pastOrders(@PathVariable("shopId") int shopId, Model model) {
        isAuthorized(model, "ROLE_USER");
        model.addAttribute("shopId", shopId);
        model.addAttribute("orders", orderService.getOrdersByShopId(shopId));
        return "orders";
    }

    @GetMapping("/shops/{shopId}/orders/{orderId}/")
    public String pastOrderItem(@PathVariable("shopId") int shopId, @PathVariable("orderId") int orderId, Model model) {
        isAuthorized(model, "ROLE_USER");
        Order order = orderService.getOrderByOrderId(orderId);
        model.addAttribute("shopId", shopId);
        model.addAttribute("orderId", orderId);
        model.addAttribute("shopName", shopService.getShopById(shopId).getName());
        model.addAttribute("customerName", userService.getUserById(order.getCustomerId()).getUsername());
        model.addAttribute("staffName", userService.getUserById(order.getStaffId()).getUsername());
        model.addAttribute("order", order);

        List<Pair<Product, Integer>> orderedItems = new ArrayList<>();

        for (Pair<Integer, Integer> product : order.getItems()) {
            orderedItems.add(new Pair<Product, Integer>(productService.getProduct(product.getFirst().intValue()),
                    product.getSecond()));
        }
        model.addAttribute("orderedItems", orderedItems);
        return "order_page";
    }
}
