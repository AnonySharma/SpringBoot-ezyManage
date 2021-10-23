package com.ankit.ezymanage.service;

import java.util.List;

import com.ankit.ezymanage.model.User;

public interface UserService {
	void saveUser(User user);

	void addRoleToUser(String username, String roleName);

	User getUser(String username);

	List<User> getUsers();
}
