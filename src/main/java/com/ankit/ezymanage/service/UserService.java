package com.ankit.ezymanage.service;

import java.util.List;

import com.ankit.ezymanage.model.User;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
	void saveUser(User user);

	void deleteUser(String username);

	List<String> getUserRoles(String username);

	boolean addRoleToUser(String username, String roleName);

	boolean removeRoleFromUser(String username, String role);

	User getUserByUsername(String username);

	User getUserById(int id);

	List<User> getAllUsers();

	UserDetails loadUserByUsername(String username);

	String findLoggedInUsername();

}
