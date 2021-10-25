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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController extends RootController {
	private final UserService userService;
	private final ProfileService profileService;

	@Autowired
	public UserController(UserService userService, ProfileService profileService) {
		super(userService);
		this.userService = userService;
		this.profileService = profileService;
	}

	@RequestMapping("/profile/")
	public String profileManager(Model model) {
		makeChangesIfAuthenticated(model);
		System.out.println("Profile page !!!!!!");
		String username = userService.findLoggedInUsername();

		if (username == null)
			return "redirect:/login/";

		Profile profile = profileService.getProfile(username);
		model.addAttribute("profile", profile);
		System.out.println(profile.toString());

		System.out.println(model.toString());
		return "profile";
	}

	@GetMapping("/profile/edit/")
	public String profileEditManager(Model model) {
		makeChangesIfAuthenticated(model);
		System.out.println("Profile edit page !!!!!!");
		String username = userService.findLoggedInUsername();

		if (username == null)
			return "redirect:/login/";

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
		makeChangesIfAuthenticated(model);
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
}
