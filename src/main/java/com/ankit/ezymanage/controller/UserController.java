package com.ankit.ezymanage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ankit.ezymanage.model.Order;
import com.ankit.ezymanage.model.Product;
import com.ankit.ezymanage.model.Profile;
import com.ankit.ezymanage.service.OrderService;
import com.ankit.ezymanage.service.ProductService;
import com.ankit.ezymanage.service.ProfileService;
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
public class UserController extends BaseController {
	private final UserService userService;
	private final ProfileService profileService;
	private final ShopService shopService;
	private final ProductService productService;
	private final OrderService orderService;

	@Autowired
	public UserController(UserService userService, ProfileService profileService, ShopService shopService,
			ProductService productService, OrderService orderService) {
		super(userService);
		this.userService = userService;
		this.profileService = profileService;
		this.shopService = shopService;
		this.productService = productService;
		this.orderService = orderService;
	}

	@GetMapping("/profile/")
	public String profileManager(Model model) {
		if (!isLoggedIn())
			return "redirect:/login/";

		System.out.println("Profile page !!!!!!");
		String username = userService.findLoggedInUsername();
		Profile profile = profileService.getProfile(username);
		model.addAttribute("profile", profile);
		System.out.println(profile.toString());

		System.out.println(model.toString());
		if (isAuthorized(model, ROLE_ABOVE_ADMIN))
			model.addAttribute("isAdmin", true);
		return "profile";
	}

	@GetMapping("/profile/edit/")
	public String profileEditManager(Model model) {
		if (!isLoggedIn())
			return "redirect:/login/";

		System.out.println("Profile edit page !!!!!!");
		String username = userService.findLoggedInUsername();
		Profile profile = profileService.getProfile(username);
		model.addAttribute("profile", profile);
		model.addAttribute("edit_form", true);
		System.out.println(profile.toString());

		System.out.println(model.toString());
		if (isAuthorized(model, ROLE_ABOVE_ADMIN))
			model.addAttribute("isAdmin", true);
		return "profile";
	}

	@PostMapping("/profile/edit/")
	public String profileEditRequestManager(@ModelAttribute("profile") Profile profile, Model model) {
		if (!isLoggedIn())
			return "redirect:/login/";

		System.out.println("Profile edit request page !!!!!!");
		String username = userService.findLoggedInUsername();
		System.out.println(username + " is logged in!!");
		System.out.println("Edited successfully!");
		System.out.println(profile);
		profileService.updateProfile(profile);

		System.out.println(model.toString());
		if (isAuthorized(model, ROLE_ABOVE_ADMIN))
			model.addAttribute("isAdmin", true);
		return "redirect:/profile/";
	}

	// My orders
	@GetMapping("/myorders/")
	public String myOrders(Model model) {
		if (!isLoggedIn())
			return "redirect:/login/";

		System.out.println("Welcome to My Orders!");
		int userId = userService.getUserByUsername(userService.findLoggedInUsername()).getId();
		List<Order> orders = orderService.getAllOrdersByUserId(userId);
		System.out.println("Hey!");
		List<Pair<Order, String>> myOrderList = new ArrayList<>();
		for (Order order : orders) {
			String products = "";
			for (Pair<Integer, Integer> product : order.getItems()) {
				products += productService.getProduct(product.getFirst()).getName() + "(x" + product.getSecond()
						+ "), ";
			}
			if (products.length() > 2)
				products = products.substring(0, products.length() - 2);
			myOrderList.add(new Pair<>(order, products));
		}
		Map<Integer, String> shopMap = new HashMap<>();
		for (Pair<Order, String> order : myOrderList) {
			shopMap.put(order.getFirst().getShopId(), shopService.getShopById(order.getFirst().getShopId()).getName());
		}
		model.addAttribute("shopMap", shopMap);
		model.addAttribute("orders", myOrderList);
		if (isAuthorized(model, ROLE_ABOVE_ADMIN))
			model.addAttribute("isAdmin", true);
		return "my_orders";
	}

	@GetMapping("/myorders/{orderId}/")
	public String pastOrderItem(@PathVariable("orderId") int orderId, Model model) {
		if (!isLoggedIn())
			return "redirect:/login/";

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
		if (isAuthorized(model, ROLE_ABOVE_ADMIN))
			model.addAttribute("isAdmin", true);
		return "order_page";
	}

}
