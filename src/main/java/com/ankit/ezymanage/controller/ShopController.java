package com.ankit.ezymanage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    @GetMapping("/myshops/grantpermission/")
    public String makeOwner(Model model, RedirectAttributes redirectAttributes) {
        if (!isLoggedIn()) {
            return "redirect:/login/";
            // return "redirect:/login?back=/myshops/grantpermission/";
        }
        String username = userService.findLoggedInUsername();
        // TODO: REQUEST ADMIN TO MAKE OWNER
        if (userService.addRoleToUser(username, "ROLE_OWNER")) {
            System.out.println("Congrats! You can open your own shop accounts now!");
            redirectAttributes.addFlashAttribute("successMsg", "Congrats! You can open your own shop-accounts now!");
        } else {
            System.out.println("Failed to grant permissions!");
            redirectAttributes.addFlashAttribute("errorMsg", "Failed to grant permissions!");
        }
        return "redirect:/myshops/";
    }

    @GetMapping("/myshops/")
    public String myShops(Model model, RedirectAttributes redirectAttributes) {
        String username = userService.findLoggedInUsername();
        System.out.println(isAuthorized(model, "ROLE_OWNER"));
        System.out.println(isLoggedIn());
        if (!isLoggedIn()) {
            return "redirect:/login/";
        }
        if (!isAuthorized(model, "ROLE_OWNER")) {
            model.addAttribute("infoMsg", "You are not authorized to add new shops!");
            return "my_shops_unauth";
        }
        System.out.println("user: " + user);
        List<Shop> shops = shopService.getAllShopsUnder(username);
        model.addAttribute("owner", username);
        model.addAttribute("shops", shops);
        return "my_shops";
    }

    @GetMapping("/newshop/")
    public String newShop(Model model) {
        if (!isAuthorized(model, "ROLE_OWNER"))
            return FORBIDDEN_ERROR_PAGE;
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
        if (!isAuthorized(model, "ROLE_OWNER"))
            return FORBIDDEN_ERROR_PAGE;
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
        if (!isAuthorized(model, "ROLE_OWNER"))
            return FORBIDDEN_ERROR_PAGE;
        System.out.println("Opened shop!");
        model.addAttribute("shop", shopService.getShopById(shopId));
        model.addAttribute("shopId", shopId);
        return "dashboard";
    }

    @GetMapping("/shops/{shopId}/products/")
    public String productsPerShop(@PathVariable("shopId") int shopId, Model model) {
        if (!isAuthorized(model, "ROLE_OWNER"))
            return FORBIDDEN_ERROR_PAGE;
        System.out.println("Opened products under shop!");
        model.addAttribute("shop", shopService.getShopById(shopId));
        model.addAttribute("shopId", shopId);
        model.addAttribute("products", shopService.getProductsByShop(shopId));
        return "products_per_shop";
    }

    @GetMapping("/shops/{shopId}/products/add/")
    public String showAllProducts(@PathVariable("shopId") int shopId, Model model) {
        if (!isAuthorized(model, "ROLE_OWNER"))
            return FORBIDDEN_ERROR_PAGE;
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
        if (!isAuthorized(model, "ROLE_OWNER"))
            return FORBIDDEN_ERROR_PAGE;
        System.out.println("Listing a product!");
        shopService.addProductToShop(shopId, productId);
        return "redirect:/shops/" + shopId + "/products/add/";
    }

    @GetMapping("/shops/{shopId}/products/remove/{productId}/")
    public String unlistProduct(@PathVariable("shopId") int shopId, @PathVariable("productId") int productId,
            Model model) {
        if (!isAuthorized(model, "ROLE_OWNER"))
            return FORBIDDEN_ERROR_PAGE;
        System.out.println("Unlisting a product!");
        shopService.removeProductFromShop(shopId, productId);
        return "redirect:/shops/" + shopId + "/products/";
    }

    // PAST ORDERS
    @GetMapping("/shops/{shopId}/orders/")
    public String pastOrders(@PathVariable("shopId") int shopId, Model model) {
        if (!isAuthorized(model, "ROLE_OWNER"))
            return FORBIDDEN_ERROR_PAGE;
        model.addAttribute("shopId", shopId);
        List<Order> orders = orderService.getOrdersByShopId(shopId);
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

        Map<Integer, String> customerMap = new HashMap<>();
        for (Pair<Order, String> order : myOrderList) {
            customerMap.put(order.getFirst().getCustomerId(),
                    userService.getUserById(order.getFirst().getCustomerId()).getUsername());
        }
        model.addAttribute("customerMap", customerMap);
        model.addAttribute("orders", myOrderList);
        return "orders";
    }

    @GetMapping("/shops/{shopId}/orders/{orderId}/")
    public String pastOrderItem(@PathVariable("shopId") int shopId, @PathVariable("orderId") int orderId, Model model) {
        if (!isAuthorized(model, "ROLE_OWNER"))
            return FORBIDDEN_ERROR_PAGE;
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
        model.addAttribute("backLink", "/shops/" + shopId + "/orders/");
        return "order_page";
    }
}
