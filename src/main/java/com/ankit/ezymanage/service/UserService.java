package com.ankit.ezymanage.service;

import java.util.List;

import com.ankit.ezymanage.model.User;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
	void saveUser(User user);

	void deleteUser(String username);

	void addRoleToUser(String username, String roleName);

	User getUser(String username);

	User getUserById(int id);

	List<User> getAllUsers();

	UserDetails loadUserByUsername(String username);

	String findLoggedInUsername();

	boolean isLoggedIn();
}
