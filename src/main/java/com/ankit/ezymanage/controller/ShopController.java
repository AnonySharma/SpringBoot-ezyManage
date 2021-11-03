package com.ankit.ezymanage.controller;

import java.util.List;

import com.ankit.ezymanage.model.Shop;
import com.ankit.ezymanage.service.ShopService;
import com.ankit.ezymanage.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ShopController extends RootController {
    private final ShopService shopService;
    private final UserService userService;

    @Autowired
    public ShopController(UserService userService, ShopService shopService) {
        super(userService);
        this.shopService = shopService;
        this.userService = userService;
    }

    @RequestMapping("/myshops/")
    public String myShops(Model model) {
        makeChangesIfAuthenticated(model);
        String owner = userService.findLoggedInUsername();
        List<Shop> shops = shopService.getAllShopsUnder(owner);
        model.addAttribute("shops", shops);
        return "my_shops";
    }

    @GetMapping("/newshop/")
    public String newShop(Model model) {
        makeChangesIfAuthenticated(model);
        String owner = userService.findLoggedInUsername();
        Shop shop = new Shop();
        shop.setOwner(owner);
        System.out.println("Initially " + shop.toString());
        model.addAttribute("shop", shop);
        model.addAttribute("postLink", "/newshop/");
        return "new_shop";
    }

    @PostMapping("/newshop/")
    public String newShopPost(@ModelAttribute("shop") Shop shop, Model model) {
        makeChangesIfAuthenticated(model);
        System.out.println("Creating shop!");
        System.out.println(shop.toString());
        shopService.createShop(shop);
        return "redirect:/myshops/";
    }

    @GetMapping("/shops/{id}/")
    public String currentShop(@PathVariable("id") int id, Model model) {
        makeChangesIfAuthenticated(model);
        System.out.println("Opened shop!");
        model.addAttribute("shop", shopService.getShopById(id));
        model.addAttribute("products", shopService.getProductsByShop(id));
        return "shop";
    }

    @GetMapping("/shops/{shopId}/remove/{productId}")
    public String unlistProduct(@PathVariable("shopId") int shopId, @PathVariable("productId") int productId,
            Model model) {
        makeChangesIfAuthenticated(model);
        System.out.println("Unlisting a product!");
        shopService.removeProductFromShop(shopId, productId);
        return "shop";
    }
}
