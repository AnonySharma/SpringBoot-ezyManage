package com.ankit.ezymanage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ankit.ezymanage.model.Product;
import com.ankit.ezymanage.model.Order;
import com.ankit.ezymanage.model.OwnerRequest;
import com.ankit.ezymanage.model.Shop;
import com.ankit.ezymanage.model.Staff;
import com.ankit.ezymanage.model.User;
import com.ankit.ezymanage.service.EmailService;
import com.ankit.ezymanage.service.OrderService;
import com.ankit.ezymanage.service.ProductService;
import com.ankit.ezymanage.service.ProfileService;
import com.ankit.ezymanage.service.ShopService;
import com.ankit.ezymanage.service.UserService;
import com.ankit.ezymanage.utils.MyCalander;
import com.ankit.ezymanage.utils.Pair;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ShopController extends BaseController {
    private final UserService userService;
    private final ShopService shopService;
    private final ProductService productService;
    private final OrderService orderService;
    private final EmailService emailService;
    private final ProfileService profileService;

    @Autowired
    public ShopController(UserService userService, ShopService shopService, ProductService productService,
            OrderService orderService, EmailService emailService, ProfileService profileService) {
        super(userService);
        this.shopService = shopService;
        this.userService = userService;
        this.productService = productService;
        this.orderService = orderService;
        this.emailService = emailService;
        this.profileService = profileService;
    }

    @GetMapping("/myshops/grantpermission/")
    public String requestOwner(Model model, RedirectAttributes redirectAttributes) {
        if (!isLoggedIn())
            return "redirect:/login/";
        if (isAuthorized(model, ROLE_ABOVE_OWNER))
            return "redirect:/myshops/";
        String username = userService.findLoggedInUsername();
        int userId = userService.getUserByUsername(username).getId();
        model.addAttribute("user", user);
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            redirectAttributes.addFlashAttribute("isAdmin", true);

        System.out.println("Requesting for owner permission...");
        if (userService.checkIfAlreadyRequestedToBeOwner(userId)) {
            model.addAttribute("infoMsg", "You have already requested once! Contact admin if reason is not known!");
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "my_shops_unauth";
        }
        OwnerRequest ownerRequest = new OwnerRequest();
        ownerRequest.setUserId(userId);
        ownerRequest.setStatus("pending");
        ownerRequest.setDate(MyCalander.now());
        userService.createOwnerRequest(ownerRequest);
        System.out.println("Request sent!");
        model.addAttribute("infoMsg", "Request sent! Please wait for admin's approval!");
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "my_shops_unauth";
    }

    @GetMapping("/myshops/")
    public String myShops(Model model, RedirectAttributes redirectAttributes) {
        String username = userService.findLoggedInUsername();
        System.out.println(isAuthorized(model, ROLE_ABOVE_STAFF));
        System.out.println(isLoggedIn());
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_STAFF)) {
            model.addAttribute("infoMsg", "You are not authorized to add new shops!");
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "my_shops_unauth";
        }
        int userId = userService.getUserByUsername(username).getId();
        OwnerRequest ownerRequest = userService.getOwnerRequestByUserId(userId);
        if ((ownerRequest != null) && (ownerRequest.getStatus().equals("allowed"))) {
            model.addAttribute("successMsg", "You can now add shops under your account!");
            userService.deleteOwnerRequest(userId);
        }
        System.out.println("user: " + user);
        List<Shop> shops = shopService.getAllShopsUnder(username);
        model.addAttribute("owner", username);
        model.addAttribute("shops", shops);
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "my_shops";
    }

    @GetMapping("/newshop/")
    public String newShop(Model model) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_OWNER))
            return FORBIDDEN_ERROR_PAGE;
        String owner = userService.findLoggedInUsername();
        Shop shop = new Shop();
        shop.setOwner(owner);
        System.out.println("Initially " + shop.toString());
        model.addAttribute("shop", shop);
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("postLink", "/newshop/");
        model.addAttribute("backLink", "/myshops/");
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "new_shop";
    }

    @PostMapping("/newshop/")
    public String newShopPost(@ModelAttribute("shop") Shop shop, Model model) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_OWNER))
            return FORBIDDEN_ERROR_PAGE;
        System.out.println("Creating shop!");
        System.out.println(shop.toString());
        String owner = userService.findLoggedInUsername();
        shop.setOwner(owner);
        shopService.createShop(shop);
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "redirect:/myshops/";
    }

    @GetMapping("/shops/{shopId}/")
    public String dashboard(@PathVariable("shopId") int shopId, Model model) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_STAFF))
            return FORBIDDEN_ERROR_PAGE;
        System.out.println("Opened shop!");
        model.addAttribute("shop", shopService.getShopById(shopId));
        model.addAttribute("shopId", shopId);
        model.addAttribute("isOwner", user.getUsername() == shopService.getShopById(shopId).getOwner());
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "dashboard";
    }

    @GetMapping("/shops/{shopId}/products/")
    public String productsPerShop(@PathVariable("shopId") int shopId, Model model) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_STAFF))
            return FORBIDDEN_ERROR_PAGE;
        System.out.println("Opened products under shop!");
        model.addAttribute("shop", shopService.getShopById(shopId));
        model.addAttribute("shopId", shopId);
        model.addAttribute("products", shopService.getProductsByShop(shopId));
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "products_per_shop";
    }

    @GetMapping("/shops/{shopId}/products/add/")
    public String showAllProducts(@PathVariable("shopId") int shopId, Model model,
            RedirectAttributes redirectAttributes) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_OWNER)) {
            // return FORBIDDEN_ERROR_PAGE;
            redirectAttributes.addFlashAttribute("errorMsg", "You are not authorized to add products!");
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/shops/" + shopId + "/products/";
        }
        System.out.println("Listing all product!");

        List<Integer> addedProducts = new ArrayList<>();
        for (Product product : shopService.getProductsByShop(shopId)) {
            addedProducts.add(product.getId());
        }

        model.addAttribute("productList", productService.getAllProducts());
        model.addAttribute("addedProducts", addedProducts);
        model.addAttribute("shopId", shopId);
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "allProducts";
    }

    @GetMapping("/shops/{shopId}/products/add/{productId}/")
    public String addProduct(@PathVariable("shopId") int shopId, @PathVariable("productId") int productId,
            Model model) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_OWNER))
            return FORBIDDEN_ERROR_PAGE;
        System.out.println("Listing a product!");
        shopService.addProductToShop(shopId, productId);
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "redirect:/shops/" + shopId + "/products/add/";
    }

    @GetMapping("/shops/{shopId}/products/remove/{productId}/")
    public String unlistProduct(@PathVariable("shopId") int shopId, @PathVariable("productId") int productId,
            Model model) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_OWNER))
            return FORBIDDEN_ERROR_PAGE;
        System.out.println("Unlisting a product!");
        shopService.removeProductFromShop(shopId, productId);
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "redirect:/shops/" + shopId + "/products/";
    }

    // PAST ORDERS
    @GetMapping("/shops/{shopId}/orders/")
    public String pastOrders(@PathVariable("shopId") int shopId, Model model) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_STAFF))
            return FORBIDDEN_ERROR_PAGE;
        model.addAttribute("shopId", shopId);
        List<Order> orders = orderService.getOrdersByShopId(shopId);
        List<Pair<Order, String>> orderList = new ArrayList<>();
        for (Order order : orders) {
            String products = "";
            for (Pair<Integer, Integer> product : order.getItems()) {
                products += productService.getProduct(product.getFirst()).getName() + "(x" + product.getSecond()
                        + "), ";
            }
            if (products.length() > 2)
                products = products.substring(0, products.length() - 2);
            orderList.add(new Pair<>(order, products));
        }

        Map<Integer, String> customerMap = new HashMap<>();
        for (Pair<Order, String> order : orderList) {
            customerMap.put(order.getFirst().getCustomerId(),
                    userService.getUserById(order.getFirst().getCustomerId()).getUsername());
        }
        model.addAttribute("customerMap", customerMap);
        model.addAttribute("orders", orderList);
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "orders";
    }

    @GetMapping("/shops/{shopId}/orders/{orderId}/")
    public String pastOrderItem(@PathVariable("shopId") int shopId, @PathVariable("orderId") int orderId, Model model) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_STAFF))
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
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "order_page";
    }

    @GetMapping("/shops/{shopId}/staffs/")
    public String manageStaffs(@PathVariable("shopId") int shopId, Model model) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_OWNER))
            return FORBIDDEN_ERROR_PAGE;
        System.out.println("Opened staffs page!");
        model.addAttribute("staffs", shopService.getStaffsByShop(shopId));
        model.addAttribute("shopId", shopId);
        model.addAttribute("shopName", shopService.getShopById(shopId).getName());
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "manage_staffs";
    }

    @GetMapping("/shops/{shopId}/staffs/add/")
    public String addStaff(@PathVariable("shopId") int shopId, Model model) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_OWNER))
            return FORBIDDEN_ERROR_PAGE;
        System.out.println("Adding staff!");

        List<Pair<String, Integer>> users = new ArrayList<>();
        for (User user : userService.getAllUsers()) {
            users.add(new Pair<String, Integer>(user.getUsername(), user.getId()));
        }
        model.addAttribute("staff", new Staff());
        model.addAttribute("shopId", shopId);
        model.addAttribute("users", users);
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "new_staff";
    }

    @PostMapping("/shops/{shopId}/staffs/add/")
    public String addStaff(@PathVariable("shopId") int shopId, @ModelAttribute("staff") Staff staff, Model model,
            RedirectAttributes redirectAttributes) {
        System.out.println("Adding staff!" + staff);
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_OWNER))
            return FORBIDDEN_ERROR_PAGE;

        System.out.println("Adding staff!" + staff);
        if (staff.getStaffId() == -1) {
            redirectAttributes.addFlashAttribute("errorMsg", "Choose a staff!");
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/shops/" + shopId + "/staffs/add/";
        }
        if (shopService.checkStaffByShop(shopId, staff.getStaffId())) {
            redirectAttributes.addFlashAttribute("warningMsg", "Staff already exists!");
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/shops/" + shopId + "/staffs/add/";
        }
        staff.setShopId(shopId);
        staff.setDateOfJoining(MyCalander.now());
        staff.setName(userService.getUserById(staff.getStaffId()).getUsername());
        shopService.addStaff(staff);
        redirectAttributes.addFlashAttribute("successMsg", "Staff added successfully!");
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "redirect:/shops/" + shopId + "/staffs/";
    }

    @GetMapping("/shops/{shopId}/staffs/remove/{staffId}/")
    public String removeStaff(@PathVariable("shopId") int shopId, @PathVariable("staffId") int staffId, Model model,
            RedirectAttributes redirectAttributes) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_OWNER))
            return FORBIDDEN_ERROR_PAGE;
        System.out.println("Removing staff!");
        shopService.removeStaff(shopId, staffId);
        redirectAttributes.addFlashAttribute("successMsg", "Staff removed successfully!");
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "redirect:/shops/" + shopId + "/staffs/";
    }

    @GetMapping("/shops/{shopId}/staffs/{staffId}/orders/")
    public String manageStaffOrders(@PathVariable("shopId") int shopId, @PathVariable("staffId") int staffId,
            Model model) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_OWNER))
            return FORBIDDEN_ERROR_PAGE;
        System.out.println("Opened staff orders page!");
        model.addAttribute("staffId", staffId);
        model.addAttribute("staffName", userService.getUserById(staffId).getUsername());
        List<Order> orders = orderService.getOrdersByStaff(shopId, staffId);
        List<Pair<Order, String>> orderList = new ArrayList<>();
        for (Order order : orders) {
            String products = "";
            for (Pair<Integer, Integer> product : order.getItems()) {
                products += productService.getProduct(product.getFirst()).getName() + "(x" + product.getSecond()
                        + "), ";
            }
            if (products.length() > 2)
                products = products.substring(0, products.length() - 2);
            orderList.add(new Pair<>(order, products));
        }

        Map<Integer, String> customerMap = new HashMap<>();
        for (Pair<Order, String> order : orderList) {
            customerMap.put(order.getFirst().getCustomerId(),
                    userService.getUserById(order.getFirst().getCustomerId()).getUsername());
        }
        model.addAttribute("customerMap", customerMap);
        model.addAttribute("orders", orderList);
        model.addAttribute("shopId", shopId);
        model.addAttribute("shopName", shopService.getShopById(shopId).getName());
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "manage_staff_orders";
    }

    @GetMapping("/shops/{shopId}/customers/")
    public String manageCustomers(@PathVariable("shopId") int shopId, Model model) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_STAFF))
            return FORBIDDEN_ERROR_PAGE;
        System.out.println("Opened customers page!");
        List<Integer> customerIds = shopService.getCustomersByShop(shopId);
        Map<Integer, String> customerMap = new HashMap<>();
        for (int customerId : customerIds) {
            customerMap.put(customerId, userService.getUserById(customerId).getUsername());
        }

        model.addAttribute("customerIds", customerIds);
        model.addAttribute("customerMap", customerMap);
        model.addAttribute("shopId", shopId);
        model.addAttribute("shopName", shopService.getShopById(shopId).getName());
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "manage_customers";
    }

    @GetMapping("/shops/{shopId}/customers/{customerId}/orders/")
    public String manageCustomerOrders(@PathVariable("shopId") int shopId, @PathVariable("customerId") int customerId,
            Model model) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_STAFF))
            return FORBIDDEN_ERROR_PAGE;
        System.out.println("Opened customer orders page!");
        model.addAttribute("customerId", customerId);
        model.addAttribute("customerName", userService.getUserById(customerId).getUsername());
        model.addAttribute("orders", orderService.getOrdersByCustomer(shopId, customerId));
        model.addAttribute("shopId", shopId);
        model.addAttribute("shopName", shopService.getShopById(shopId).getName());
        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "manage_customer_orders";
    }

    @GetMapping("/shops/{shopId}/mailing/")
    public String mailToCustomer(@PathVariable("shopId") int shopId, Model model) {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_STAFF))
            return FORBIDDEN_ERROR_PAGE;
        List<Integer> customerIds = shopService.getCustomersByShop(shopId);
        Map<Integer, String> customerMap = new HashMap<>();
        for (int customerId : customerIds) {
            customerMap.put(customerId, userService.getUserById(customerId).getUsername());
        }

        model.addAttribute("customerIds", customerIds);
        model.addAttribute("customerMap", customerMap);
        return "mailing";
    }

    @PostMapping("/shops/{shopId}/mailing/all/")
    public String mailToAllCustomers(@PathVariable("shopId") int shopId, @RequestParam("mail_subject") String subject,
            @RequestParam("mail_body") String body, Model model, RedirectAttributes redirectAttributes) {
        List<Integer> customerIds = shopService.getCustomersByShop(shopId);
        String to = "";
        for (Integer customerId : customerIds) {
            String username = userService.getUserById(customerId).getUsername();
            to += profileService.getProfile(username).getEmail() + " ";
        }

        if ((to.length() > 1) && (to.charAt(to.length() - 1) == ' '))
            to = to.substring(0, to.length() - 1);
        System.out.println("Sending mail to: " + to + "#");
        emailService.sendHTMLEmailByShop(shopId, to, subject, body);
        redirectAttributes.addAttribute("successMsg", "Mailed successfully to all customers");
        return "redirect:/shops/" + shopId + "/";
    }

    @PostMapping("/shops/{shopId}/mailing/{customerId}/")
    public String mailToACustomer(@PathVariable("shopId") int shopId, @PathVariable("customerId") int customerId,
            @RequestParam("mail_subject") String subject, @RequestParam("mail_body") String body, Model model,
            RedirectAttributes redirectAttributes) {
        String username = userService.getUserById(customerId).getUsername();
        String to = profileService.getProfile(username).getEmail();
        System.out.println("Sending mail to: " + to + "#");
        emailService.sendHTMLEmailByShop(shopId, to, subject, body);
        redirectAttributes.addAttribute("successMsg", "Mailed successfully to " + username);
        return "redirect:/shops/" + shopId + "/";
    }
}
