package com.ankit.ezymanage.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CustomErrorController implements ErrorController {
	// @RequestMapping({ "/error/", "/error" })
	// public String errorManager(Model model) {
	// isAuthorized(model, "ROLE_USER");
	// System.out.println("ERRRORRR !!!!!!");
	// return "error/404";
	// }

	// @RequestMapping("${server.error.path}")
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request) {
		Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		System.out.println("status: " + status);
		if (status != null) {
			Integer statusCode = Integer.valueOf(status.toString());
			if (statusCode == HttpStatus.NOT_FOUND.value()) {
				return "error/404";
			} else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
				return "error/401";
			} else if (statusCode == HttpStatus.FORBIDDEN.value()) {
				return "error/403";
			}
		}
		return "error/error";
	}

	@GetMapping("/login-error/")
	public String loginError(HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		HttpSession session = request.getSession(false);
		String errorMessage = null;
		System.out.println(session.getAttributeNames().nextElement());
		if (session != null) {
			AuthenticationException ex = (AuthenticationException) session
					.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
			if (ex != null) {
				System.out.println(ex);
				errorMessage = ex.getMessage();
			}
		}
		redirectAttributes.addFlashAttribute("errorMsg", errorMessage);
		return "redirect:/login/";
	}

	@GetMapping("/401/")
	public String unauthorizedManager(Model model) {
		System.out.println("UNAUTHORIZED");
		return "error/401";
	}

	@GetMapping("/403/")
	public String forbiddenManager(Model model) {
		System.out.println("FORBIDDEN");
		return "error/403";
	}

	@GetMapping("/404/")
	public String pageNotFoundManager(Model model) {
		System.out.println("Page Not Found");
		return "error/404";
	}
}
