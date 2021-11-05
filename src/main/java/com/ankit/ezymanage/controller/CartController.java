package com.ankit.ezymanage.controller;

import com.ankit.ezymanage.model.Cart;
import com.ankit.ezymanage.service.CartService;
import com.ankit.ezymanage.service.ShopService;
import com.ankit.ezymanage.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CartController extends RootController {
    private final ShopService shopService;
    private final CartService cartService;

    @Autowired
    public CartController(UserService userService, ShopService shopService, CartService cartService) {
        super(userService);
        this.shopService = shopService;
        this.cartService = cartService;
    }

    @GetMapping("/shops/{shopId}/cart/")
    public String cart(@PathVariable("shopId") int shopId, Model model) {
        makeChangesIfAuthenticated(model);
        Cart newCart = new Cart();
        newCart.setShopId(shopId);
        model.addAttribute("cart", newCart);
        model.addAttribute("shopId", shopId);
        model.addAttribute("customers", userService.getAllUsers());
        return "new_cart";
    }

    @PostMapping("/shops/{shopId}/cart/new/")
    public String afterUsername(@PathVariable("shopId") int shopId, @ModelAttribute("cart") Cart cart, Model model,
            RedirectAttributes redirectAttributes) {
        int customerId = cart.getCustomerId();
        if (cartService.cartExistsForUserId(shopId, customerId)) {
            redirectAttributes.addFlashAttribute("infoMsg", "User already has an opened cart!");
        } else {
            cartService.addNewCart(cart);
        }

        return "redirect:/shops/" + shopId + "/cart/" + customerId + "/";
    }

    @GetMapping("/shops/{shopId}/cart/{customerId}/")
    public String cart(@PathVariable("shopId") int shopId, @PathVariable("customerId") int customerId, Model model) {
        makeChangesIfAuthenticated(model);
        Cart cart = cartService.getCartByUserId(customerId);
        model.addAttribute("cart", cart);
        model.addAttribute("customerName", userService.getUserById(customerId).getUsername());
        model.addAttribute("shopId", shopId);
        model.addAttribute("productList", shopService.getProductsByShop(shopId));
        System.out.println("Opened cart!");
        return "new_cart";
    }
}
