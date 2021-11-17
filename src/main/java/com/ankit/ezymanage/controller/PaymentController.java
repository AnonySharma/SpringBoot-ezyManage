package com.ankit.ezymanage.controller;

import java.text.ParseException;
import java.util.UUID;

import com.ankit.ezymanage.model.Cart;
import com.ankit.ezymanage.service.CartService;
import com.ankit.ezymanage.service.OrderService;
import com.ankit.ezymanage.service.ProfileService;
import com.ankit.ezymanage.service.UserService;
import com.ankit.ezymanage.utils.MyCalander;
import com.instamojo.wrapper.api.ApiContext;
import com.instamojo.wrapper.api.Instamojo;
import com.instamojo.wrapper.api.InstamojoImpl;
import com.instamojo.wrapper.exception.ConnectionException;
import com.instamojo.wrapper.exception.HTTPException;
import com.instamojo.wrapper.model.PaymentOrder;
import com.instamojo.wrapper.model.PaymentOrderResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// id
// test_Kb8X2tAcFiz35aUsv6d3aBXejk4JVBtl6OF
// secret
// test_U3cGu0T94arRxit0zG9c8rqG1hdu0ab65vncZCGUOv2uB7RpiQg2MdqFoMdtoOc0aCYNSzVVkOEvA2DRsbbC3FC3WicXeYz11rNkch8EeqbfrSnXolR0wpnKnDk

@Controller
public class PaymentController extends BaseController {
    private Instamojo api;
    private String clientId = "test_Kb8X2tAcFiz35aUsv6d3aBXejk4JVBtl6OF";
    private String clientSecret = "test_U3cGu0T94arRxit0zG9c8rqG1hdu0ab65vncZCGUOv2uB7RpiQg2MdqFoMdtoOc0aCYNSzVVkOEvA2DRsbbC3FC3WicXeYz11rNkch8EeqbfrSnXolR0wpnKnDk";
    private UserService userService;
    private ProfileService profileService;
    private OrderService orderService;
    private CartService cartService;

    @Autowired
    public PaymentController(UserService userService, ProfileService profileService, OrderService orderService,
            CartService cartService) {
        super(userService);
        this.userService = userService;
        this.profileService = profileService;
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @GetMapping("/shops/{shopId}/cart/{customerId}/checkout/process/")
    public String processPayment(@PathVariable("shopId") int shopId, @PathVariable("customerId") int customerId,
            Model model, RedirectAttributes redirectAttributes) {
        if (!isLoggedIn())
            return "redirect:/login";
        if (!isAuthorized(model, ROLE_ABOVE_STAFF))
            return FORBIDDEN_ERROR_PAGE;

        String username = userService.getUserById(customerId).getUsername();
        Cart cart = cartService.getCartByUserIdAndShopId(customerId, shopId);

        ApiContext context = ApiContext.create(clientId, clientSecret, ApiContext.Mode.TEST);
        api = new InstamojoImpl(context);
        float fees = cart.getTotal();
        PaymentOrder order = new PaymentOrder();
        order.setName(userService.getUserById(customerId).getUsername());
        order.setCurrency("INR");
        order.setAmount((double) fees);
        order.setCreatedAt(MyCalander.now().toString());
        order.setDescription(String.valueOf(shopId));

        order.setRedirectUrl("http://localhost:8080/shops/" + shopId + "/cart/" + customerId + "/checkout/success/");
        // order.setWebhookUrl("http://localhost:8080/shops/" + shopId + "/cart/" +
        // customerId + "/checkout/webhook/");
        String token = UUID.randomUUID().toString();
        order.setTransactionId(token);

        String email = profileService.getProfile(username).getEmail();
        String phone = profileService.getProfile(username).getPhoneNumber();

        // if (email == null)
        // email = "cgankitsharma@gmail.com";
        // if (phone == null)
        // phone = "7979939400";

        order.setEmail(email);
        order.setPhone(phone);

        System.out.println(order);

        try {

            PaymentOrderResponse paymentOrderResponse = api.createPaymentOrder(order);
            // String paymentOrderId = paymentOrderResponse.getPaymentOrder().getId();
            // System.out.println(paymentOrderId);

            // Transaction transaction = new Transaction();
            // transaction.setAmount(20);
            // transaction.setVerified(false);
            // transaction.setTransactionDate(new Date());
            // transaction.setTransactionTime(new Date());
            // transaction.setMode("online");
            // transaction.setToken(token);
            // transactionDAO.save(transaction);

            return "redirect:" + paymentOrderResponse.getPaymentOptions().getPaymentUrl();

        } catch (HTTPException e) {
            System.out.println("HTTPException: " + e);
            System.out.println(e.getStatusCode());
            System.out.println(e.getMessage());
            System.out.println(e.getJsonPayload());
            redirectAttributes.addFlashAttribute("infoMsg", "Either phone or email is not set for the customer.");
        } catch (ConnectionException e) {
            System.out.println("ConnectionException: " + e);
        }

        return "redirect:/shops/" + shopId + "/cart/" + customerId + "/checkout/failed/";
    }

    @GetMapping("/shops/{shopId}/cart/{customerId}/checkout/success/")
    public String paidSuccess(@PathVariable("shopId") int shopId, @PathVariable("customerId") int customerId,
            @RequestParam("transaction_id") String transactionId, Model model, RedirectAttributes redirectAttributes)
            throws ParseException {
        if (!isLoggedIn()) {
            if (isAuthorized(model, ROLE_ABOVE_ADMIN))
                model.addAttribute("isAdmin", true);
            return "redirect:/login/";
        }
        if (!isAuthorized(model, ROLE_ABOVE_STAFF))
            return FORBIDDEN_ERROR_PAGE;

        try {
            PaymentOrder paymentOrder = api.getPaymentOrderByTransactionId(transactionId);
            System.out.println(paymentOrder);
            // Cart
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }

        if (cartService.cartExistsForUserId(customerId, shopId)) {
            Cart cart = cartService.getCartByUserIdAndShopId(customerId, shopId);
            System.out.println(cart);
            int staffId = userService.getUserByUsername(userService.findLoggedInUsername()).getId();
            if (orderService.checkoutCart(cart, staffId, "Payment Gateway")) {
                redirectAttributes.addFlashAttribute("successMsg", "Order placed successfully!");
                cartService.deleteCartByCartId(cart.getId());
            } else {
                redirectAttributes.addFlashAttribute("errorMsg", "Cart checkout failed!");
            }
        } else {
            redirectAttributes.addFlashAttribute("warningMsg", "No cart found!");
        }
        System.out.println("Checked out!");

        model.addAttribute("shopId", shopId);
        model.addAttribute("customerId", customerId);

        if (isAuthorized(model, ROLE_ABOVE_ADMIN))
            model.addAttribute("isAdmin", true);
        return "checkout_success";
    }
}
