package com.ankit.ezymanage.model;

public class User {
	private int id;
	private String username;
	private String password;
	private String role;
	private boolean isAdmin;

	public User() {
	}

	public User(int id, String username, String password, String role, boolean isAdmin) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.role = role;
		this.isAdmin = isAdmin;
	}

	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.role = "ROLE_USER";
		this.isAdmin = false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", isAdmin=" + isAdmin + ", password=" + password + ", role=" + role + ", username="
				+ username + "]";
	}
}
