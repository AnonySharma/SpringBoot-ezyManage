package com.ankit.ezymanage.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ankit.ezymanage.model.Profile;
import com.ankit.ezymanage.service.ProfileService;
import com.ankit.ezymanage.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController extends BaseController {
	private final UserService userService;
	private final ProfileService profileService;

	@Autowired
	public UserController(UserService userService, ProfileService profileService) {
		super(userService);
		this.userService = userService;
		this.profileService = profileService;
	}

	@GetMapping("/profile/")
	public String profileManager(Model model) {
		if (!isLoggedIn())
			return "redirect:/login/";

		isAuthorized(model, "ROLE_USER");
		System.out.println("Profile page !!!!!!");
		String username = userService.findLoggedInUsername();
		Profile profile = profileService.getProfile(username);
		model.addAttribute("profile", profile);
		System.out.println(profile.toString());

		System.out.println(model.toString());
		return "profile";
	}

	@GetMapping("/profile/edit/")
	public String profileEditManager(Model model) {
		if (!isLoggedIn())
			return "redirect:/login/";

		isAuthorized(model, "ROLE_USER");
		System.out.println("Profile edit page !!!!!!");
		String username = userService.findLoggedInUsername();
		Profile profile = profileService.getProfile(username);
		model.addAttribute("profile", profile);
		model.addAttribute("edit_form", true);
		System.out.println(profile.toString());

		System.out.println(model.toString());
		return "profile";
	}

	@PostMapping("/profile/edit/")
	public String profileEditRequestManager(@ModelAttribute("profile") Profile profile, Model model)
			throws ParseException {
		isAuthorized(model, "ROLE_USER");
		System.out.println("Profile edit request page !!!!!!");
		String username = userService.findLoggedInUsername();

		if (username == null)
			return "redirect:/login/";

		System.out.println(username + " is logged in!!");
		System.out.println("Edited successfully!");
		System.out.println("Check 1!");
		System.out.println(profile);
		System.out.println(profile.getDateOfBirth().toString());

		System.out.println("Check 2!");
		if (profile.getDateOfBirth() != null) {
			DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss z yyyy");
			System.out.println("Check 3!");
			Date date = (Date) formatter.parse(profile.getDateOfBirth().toString());
			profile.setDateOfBirth(date);
			System.out.println(date.toString());
		}

		System.out.println("Check 4!");
		profileService.updateProfile(profile);

		System.out.println(model.toString());
		return "redirect:/profile/";
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
}
