package com.ankit.ezymanage.controller;

import java.util.ArrayList;
import java.util.List;

import com.ankit.ezymanage.model.Cart;
import com.ankit.ezymanage.model.Product;
import com.ankit.ezymanage.service.CartService;
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
public class CartController extends BaseController {
    private final ShopService shopService;
    private final ProductService productService;
    private final CartService cartService;
    private final OrderService orderService;

    @Autowired
    public CartController(UserService userService, ShopService shopService, CartService cartService,
            ProductService productService, OrderService orderService) {
        super(userService);
        this.shopService = shopService;
        this.productService = productService;
        this.cartService = cartService;
        this.orderService = orderService;
    }

    @GetMapping("/shops/{shopId}/cart/")
    public String cart(@PathVariable("shopId") int shopId, Model model) {
        isAuthorized(model, "ROLE_USER");
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
        if (cartService.cartExistsForUserId(customerId, shopId)) {
            redirectAttributes.addFlashAttribute("infoMsg", "User already has an opened cart!");
        } else {
            cartService.addNewCart(cart);
        }

        return "redirect:/shops/" + shopId + "/cart/" + customerId + "/";
    }

    @GetMapping("/shops/{shopId}/cart/{customerId}/")
    public String cart(@PathVariable("shopId") int shopId, @PathVariable("customerId") int customerId, Model model) {
        isAuthorized(model, "ROLE_USER");
        Cart cart = cartService.getCartByUserIdAndShopId(customerId, shopId);
        model.addAttribute("cart", cart);
        model.addAttribute("customerName", userService.getUserById(customerId).getUsername());
        model.addAttribute("shopId", shopId);
        model.addAttribute("customerId", customerId);

        System.out.println("Opened cart!");

        cart = cartService.getCartByUserIdAndShopId(customerId, shopId);
        List<Integer> cartItemsIds = new ArrayList<>();
        List<Pair<Product, Integer>> cartItems = new ArrayList<>();

        for (Pair<Integer, Integer> product : cart.getProducts()) {
            Product prod = productService.getProduct(product.getFirst().intValue());
            cartItemsIds.add(prod.getId());
            cartItems.add(new Pair<Product, Integer>(prod, product.getSecond()));
        }

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cartItemsIds", cartItemsIds);
        model.addAttribute("productList", shopService.getProductsByShop(shopId));
        return "new_cart";
    }

    @GetMapping("/shops/{shopId}/cart/{customerId}/add/{productId}/")
    public String addProductToCart(@PathVariable("shopId") int shopId, @PathVariable("customerId") int customerId,
            @PathVariable("productId") int productId, Model model, RedirectAttributes redirectAttributes) {
        isAuthorized(model, "ROLE_USER");
        Cart cart = cartService.getCartByUserIdAndShopId(customerId, shopId);
        cartService.updateProductInCart(cart.getId(), productId, 1);

        redirectAttributes.addFlashAttribute("infoMsg", "Product added to cart!");
        System.out.println("Updated products in the cart!");
        return "redirect:/shops/" + shopId + "/cart/" + customerId + "/";
    }

    @GetMapping("/shops/{shopId}/cart/{customerId}/remove/{productId}/")
    public String removeProductFromCart(@PathVariable("shopId") int shopId, @PathVariable("customerId") int customerId,
            @PathVariable("productId") int productId, Model model, RedirectAttributes redirectAttributes) {
        isAuthorized(model, "ROLE_USER");
        System.out.println("Removing product from cart!");

        Cart cart = cartService.getCartByUserIdAndShopId(customerId, shopId);
        cartService.updateProductInCart(cart.getId(), productId, 0);

        redirectAttributes.addFlashAttribute("infoMsg", "Product removed from cart!");
        return "redirect:/shops/" + shopId + "/cart/" + customerId + "/";
    }

    @GetMapping("/shops/{shopId}/cart/{customerId}/checkout/")
    public String checkout(@PathVariable("shopId") int shopId, @PathVariable("customerId") int customerId, Model model,
            RedirectAttributes redirectAttributes) {
        isAuthorized(model, "ROLE_USER");
        System.out.println("Checking out!");

        Cart cart = cartService.getCartByUserIdAndShopId(customerId, shopId);
        model.addAttribute("cart", cart);
        List<Pair<Product, Integer>> cartItems = new ArrayList<>();

        for (Pair<Integer, Integer> product : cart.getProducts()) {
            Product prod = productService.getProduct(product.getFirst().intValue());
            cartItems.add(new Pair<Product, Integer>(prod, product.getSecond()));
        }

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("shopId", shopId);
        model.addAttribute("customerId", customerId);

        redirectAttributes.addFlashAttribute("infoMsg", "Cart checked out!");
        return "checkout";
    }

    @GetMapping("/shops/{shopId}/cart/{customerId}/checkout/done/")
    public String checkoutComplete(@PathVariable("shopId") int shopId, @PathVariable("customerId") int customerId,
            Model model, RedirectAttributes redirectAttributes) {
        isAuthorized(model, "ROLE_USER");
        Cart cart = cartService.getCartByUserIdAndShopId(customerId, shopId);
        int staffId = userService.getUser(userService.findLoggedInUsername()).getId();
        if (orderService.checkoutCart(cart, staffId)) {
            redirectAttributes.addFlashAttribute("successMsg", "Order placed successfully!");
            cartService.deleteCartByCartId(cart.getId());
        } else {
            redirectAttributes.addFlashAttribute("errrorMsg", "Cart checkout failed!");
        }
        System.out.println("Checked out!");

        model.addAttribute("cart", cart);
        model.addAttribute("shopId", shopId);
        model.addAttribute("customerId", customerId);

        return "checkout_success";
    }

    @GetMapping("/shops/{shopId}/cart/{customerId}/clear/")
    public String clearCart(@PathVariable("shopId") int shopId, @PathVariable("customerId") int customerId, Model model,
            RedirectAttributes redirectAttributes) {
        isAuthorized(model, "ROLE_USER");
        Cart cart = cartService.getCartByUserIdAndShopId(customerId, shopId);
        cartService.clearCart(cart.getId());
        redirectAttributes.addFlashAttribute("infoMsg", "Cart cleared!");
        return "redirect:/shops/" + shopId + "/cart/" + customerId + "/";
    }

    @GetMapping("/shops/{shopId}/cart/{customerId}/increment/{productId}/")
    public String incrementProductFromCart(@PathVariable("shopId") int shopId,
            @PathVariable("customerId") int customerId, @PathVariable("productId") int productId, Model model,
            RedirectAttributes redirectAttributes) {
        isAuthorized(model, "ROLE_USER");
        Cart cart = cartService.getCartByUserIdAndShopId(customerId, shopId);
        cartService.incrementProductInCart(cart.getId(), productId);
        return "redirect:/shops/" + shopId + "/cart/" + customerId + "/";
    }

    @GetMapping("/shops/{shopId}/cart/{customerId}/decrement/{productId}/")
    public String decrementProductFromCart(@PathVariable("shopId") int shopId,
            @PathVariable("customerId") int customerId, @PathVariable("productId") int productId, Model model,
            RedirectAttributes redirectAttributes) {
        isAuthorized(model, "ROLE_USER");
        Cart cart = cartService.getCartByUserIdAndShopId(customerId, shopId);
        cartService.decrementProductInCart(cart.getId(), productId);
        return "redirect:/shops/" + shopId + "/cart/" + customerId + "/";
    }

}
